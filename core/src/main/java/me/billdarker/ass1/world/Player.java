package me.billdarker.ass1.world;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    public boolean hasSnaked = false;
    private playerType type;
    private final Territory territory;
    protected final Map map;
    private float growthMultiplier;

    private int startX;
    private int startY;

    protected List<Player> playerKilled = new ArrayList<Player>();
    private boolean killedPlayer = false;

    public Player(Map _map, playerType _type, String _name) {
        map = _map;
        name = _name;
        type = _type;
        territory = new Territory(this, map);
        switch (type){
            case PLAYER:
                growthMultiplier = 1f;
                break;
            case WILD:
                growthMultiplier = 0.1f;
                break;
            case BOT:
                growthMultiplier = 20f;
                break;
            case WATER:
                growthMultiplier = 0;
                break;
        }
    }

    public void setStart(int x, int y) {
        startX = x;
        startY = y;
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

    public float defend(Player attacker, float attackingTroops){
        return territory.defend(attacker, attackingTroops);
    }

    public float getGrowthMultiplier() {
        return growthMultiplier;
    }

    public Territory getTerritory() {
        return territory;
    }

    public Color getColor() {
        return null;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public void addKilled(Player killed){
        playerKilled.add(killed);
        if (killed.getType() == playerType.PLAYER){
            killedPlayer = true;
        }
    }

    public boolean killedPlayer(){
        return killedPlayer;
    }
}
