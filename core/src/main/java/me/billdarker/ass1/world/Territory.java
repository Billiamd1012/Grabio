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
    private float attackPercentage = 0.2f;
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
            float tileGrowthRate = tile.getGrowthRate() * BASE_GROWTH_RATE;
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

        if (!attacks.isEmpty()) {
            for (Attack attack : attacks) {
                attack.update();
            }
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
    //This method is called by the player when they click on a tile
    public void attack(Tile tile){
        //TODO: Ask map if the tile at x,y is owned by a territory that is adjacent to this territory
        if (map.isAdjacent(owner, tile)){
            Gdx.app.log("Territory", "Territory is adjacent to this one");
            //TODO: if can attack territory then attack with current settings
            float attackingTroops = getPopulation()*attackPercentage;
            Attack newAttack = new Attack(owner, tile.owner,attackingTroops);
            population -= attackingTroops;

            attacks.add(newAttack);
        }

    }

    //This method is called by by an attacking territory
    public float defend(Player attacker, float attackingTroops){
        // TODO: Implement defense logic
        //attackingTiles = map.all adjacent tiles
        List<Tile> defendingTiles = new ArrayList<>();
        for (Tile tile : tiles){
            if (map.isBordering(attacker, tile)){
                defendingTiles.add(tile);
            }
        }

        // attacking per tile = attackingTroops / num(attackingTiles)
        //new attacking troops = attackingTroops - (defenders on a tile*defense bonus of tile)


        // if newAttackingTroops > 0
        // attackingTiles change owner to attacker
        // attacking tiles are filled with attacking troops
        // excesss troops keep attacking



        //Territory ask if can attack /
        //territory calls attack /
        // Attack object? that has a troop count and a target /
        // Belongs to territory /
        // On territory update call defend on attack object /
        // Attack object calls defend on territory /
        // Defend takes away a certain number of troops /
        // If not 0 then take some territory from defender
        // If 0 after defend destroy attack object

        return attackingTroops;
    }
}
