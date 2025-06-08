package me.billdarker.ass1.world;

import com.badlogic.gdx.Gdx;
import java.util.List;
import java.util.ArrayList;

public class Attack {
    //This class is owned by territory and is created each time a territory attacks and object
    private final Player attacker;
    private final Player defender;
    private float attackingTroops;
    private boolean isComplete = false;
    private Tile currentTarget;
    private final Map map;

    public Attack(Player _attacker, Player _defender, float _attackingTroops, Tile initialTarget, Map _map) {
        attacker = _attacker;
        defender = _defender;
        attackingTroops = _attackingTroops;
        currentTarget = initialTarget;
        map = _map;

        Gdx.app.log("Attack", "Attack class created with " + attackingTroops + " troops");
    }

    public void update() {
        if (isComplete || currentTarget == null) {
            return;
        }

        // Defend against current target
        float remainingTroops = defender.defend(attacker, attackingTroops);
        
        // If we have remaining troops, try to attack adjacent tiles of the same owner
        if (remainingTroops > 0) {
            List<Tile> adjacentTiles = getAdjacentTilesOfSameOwner();
            if (!adjacentTiles.isEmpty()) {
                // Attack the first adjacent tile
                currentTarget = adjacentTiles.get(0);
                attackingTroops = remainingTroops;
                Gdx.app.log("Attack", "Continuing attack to adjacent tile with " + remainingTroops + " troops");
            } else {
                isComplete = true;
                Gdx.app.log("Attack", "Attack completed - no more adjacent tiles to attack");
            }
        } else {
            isComplete = true;
            Gdx.app.log("Attack", "Attack completed - no troops remaining");
        }
    }

    private List<Tile> getAdjacentTilesOfSameOwner() {
        List<Tile> adjacentTiles = new ArrayList<>();
        
        for (Tile tile : defender.getTiles()) {
            if (tile != currentTarget && map.isBordering(attacker, tile)) {
                adjacentTiles.add(tile);
            }
        }
        
        return adjacentTiles;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public float getAttackingTroops() {
        return attackingTroops;
    }
}
