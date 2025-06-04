package me.billdarker.ass1.world;

import com.badlogic.gdx.Gdx;
import java.util.List;
import java.util.ArrayList;

public class Territory {
    /*
    * Territory's are owned by players and are responsible for storing a population and tiles in it contains
     */

    private final Player owner;
    private final List<Tile> tiles;
    private float population;
    private float attackPercentage = 0.5f;
    private float maxPopulation;
    private final float maxPopulationMultiplier = 10f;
    private final Map map;
    private static final float MIN_POPULATION = 1f;
    private static final float BASE_GROWTH_RATE = 0.1f;
    private static final float TERRITORY_INFLUENCE = 0.3f; // How much territory population influences tile growth

    private List<Attack> attacks = new ArrayList<>();

    public Territory(Player owner, Map map) {
        this.owner = owner;
        this.map = map;
        this.tiles = new ArrayList<>();
        this.population = MIN_POPULATION;
        updateMaxPopulation();
    }

    public void addTile(Tile tile) {
        if (!tiles.contains(tile)) {
            tiles.add(tile);
            updateMaxPopulation();
        }
    }

    public void removeTile(Tile tile) {
        if (tiles.remove(tile)) {
            updateMaxPopulation();
        }
    }

    private void updateMaxPopulation() {
        maxPopulation = Math.max(MIN_POPULATION, tiles.size() * maxPopulationMultiplier);
    }

    public void update() {
        if (tiles.isEmpty()) {
            population = MIN_POPULATION;
            return;
        }

        // Calculate territory's current population ratio (0 to 1)
        float territoryPopulationRatio = population / maxPopulation;

        // Update each tile independently
        for (Tile tile : tiles) {
            // Calculate tile's growth based on its own growth rate and current population
            float tileGrowthRate = tile.getGrowthRate() * BASE_GROWTH_RATE * owner.getGrowthMultiplier();
            float tilePopulationRatio = tile.getPopulation() / (maxPopulation / tiles.size());

            // Calculate logistic growth factor for the tile
            float tileLogisticFactor = 4 * tilePopulationRatio * (1 - tilePopulationRatio);

            // Calculate territory influence factor
            float territoryFactor = 1 + (TERRITORY_INFLUENCE * (1 - territoryPopulationRatio));

            // Calculate final growth for this tile
            float tileGrowth = tile.getPopulation() * tileGrowthRate * tileLogisticFactor * territoryFactor;

            // Apply growth to tile
            tile.addPopulation(tileGrowth);
        }

        // Update territory's total population
        population = 0;
        for (Tile tile : tiles) {
            population += tile.getPopulation();
        }

        // Ensure territory population stays within bounds
        if (population > maxPopulation) {
            float excess = population - maxPopulation;
            // Distribute excess reduction proportionally to tile populations
            for (Tile tile : tiles) {
                float reduction = (tile.getPopulation() / population) * excess;
                tile.removePopulation(reduction);
            }
            population = maxPopulation;
        }

        Gdx.app.log("Territory", "Current population: " + population +
            " maxPopulation: " + maxPopulation +
            " territoryRatio: " + territoryPopulationRatio);

        // Update attacks and remove completed ones
        if (!attacks.isEmpty()) {
            List<Attack> completedAttacks = new ArrayList<>();
            for (Attack attack : attacks) {
                attack.update();
                if (attack.isComplete()) {
                    completedAttacks.add(attack);
                }
            }
            attacks.removeAll(completedAttacks);
        }
    }

    private float getAverageGrowthRate() {
        if (tiles.isEmpty()) return 0;

        float growthRateSum = 0;
        for (Tile tile : tiles) {
            growthRateSum += tile.getGrowthRate();
        }
        return growthRateSum / tiles.size();
    }

    private float calculateGrowthRate(float avgGrowthRate, float maxPopulation, float currentPopulation) {
        if (maxPopulation <= MIN_POPULATION || currentPopulation <= 0) {
            return 0;
        }

        // Ensure current population is at least MIN_POPULATION
        currentPopulation = Math.max(MIN_POPULATION, currentPopulation);

        // Calculate the logistic growth factor
        // This creates a bell curve that peaks at maxPopulation/2
        float populationRatio = currentPopulation / maxPopulation;
        float logisticFactor = 4 * populationRatio * (1 - populationRatio);

        // Apply the growth rate with a base multiplier
        return currentPopulation * avgGrowthRate * BASE_GROWTH_RATE * logisticFactor;
    }

    public float getPopulation() {
        return population;
    }

    public float getMaxPopulation() {
        return maxPopulation;
    }

    public List<Tile> getTiles() {
        return new ArrayList<>(tiles);
    }

    public Player getOwner() {
        return owner;
    }

    public void attack(Tile tile) {
        if (map.isAdjacent(owner, tile)) {
            Gdx.app.log("Territory", "Territory is adjacent to this one");
            float attackingTroops = population * attackPercentage;

            // Distribute attacking troops proportionally to tile populations
            for (Tile attackingTile : tiles) {
                float tileTroops = (attackingTile.getPopulation() / population) * attackingTroops;
                attackingTile.removePopulation(tileTroops);
            }

            Attack newAttack = new Attack(this.owner, tile.owner, attackingTroops);
            population -= attackingTroops;
            attacks.add(newAttack);
        }
    }

    public float defend(Player attacker, float attackingTroops) {
        List<Tile> defendingTiles = new ArrayList<>();
        for (Tile tile : tiles) {
            if (map.isBordering(attacker, tile)) {
                defendingTiles.add(tile);
            }
        }

        if (defendingTiles.isEmpty()) {
            return attackingTroops;
        }

        // attacking per tile = attackingTroops / num(defendingTiles)
        float attackingPerTile = attackingTroops / defendingTiles.size();
        float leftOverTroops = 0;

        for (Tile tile : defendingTiles) {
            float defenseStrength = tile.getPopulation() * tile.getDefenseRate();
            float leftOverTroopsOnTile = attackingPerTile - defenseStrength;

            if (leftOverTroopsOnTile > 0) {
                // Tile is captured
                Gdx.app.log("Defending", "Captured tile at " + tile.getX() + "," + tile.getY());
                // Transfer ownership and set population to attacking troops
                tile.setOwner(attacker);
                tile.setPopulation(leftOverTroopsOnTile);
            } else {
                // Tile successfully defended
                float casualties = attackingPerTile / tile.getDefenseRate();
                tile.removePopulation(casualties);
            }

            leftOverTroops += Math.max(0, leftOverTroopsOnTile);
        }

        Gdx.app.log("Defending", "Troops left after defending: " + leftOverTroops + " Attakcing troops: "+attackingTroops);
        return leftOverTroops;
    }
}
