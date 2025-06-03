package me.billdarker.ass1.world;

import com.badlogic.gdx.Gdx;
import java.util.List;

public class Player {
    private String name;
    private playerType type;
    private final Territory territory;
    private final Map map;

    public Player(Map _map, playerType _type, String _name) {
        map = _map;
        name = _name;
        type = _type;
        territory = new Territory(this, map);
    }

    public void setStart(int x, int y) {
        map.setOwner(x, y, this);
        // Add the tile to the territory
        Tile startTile = map.getTile(x, y);
        if (startTile != null) {
            territory.addTile(startTile);
        }
    }

    public void update() {
        territory.update();
    }

    public void addTile(Tile tile) {
        territory.addTile(tile);
    }

    public void removeTile(Tile tile) {
        territory.removeTile(tile);
    }

    public float getPopulation() {
        return territory.getPopulation();
    }

    public float getMaxPopulation() {
        return territory.getMaxPopulation();
    }

    public List<Tile> getTiles() {
        return territory.getTiles();
    }

    public playerType getType() {
        return type;
    }

    public void attack(Tile tile){
        territory.attack(tile);
    }

    public float defend(float attackingTroops){
        return territory.defend(attackingTroops);
    }
}
