package me.billdarker.ass1.world;

import com.badlogic.gdx.Gdx;
import java.util.List;

public class Player {
    // maxTroops will be a product of the number of tiles
    private String name;
    private playerType type;
    private float maxTroops;
    private float maxTroopsMultiplier = 10;
    // troopCount will increase each turn (maxTroops/troopCount)*troopCount*avg(growthrate)
    private float troopCount = 50f;
    private List<Tile> playerTiles;
    private final Map map;

    //Create a new player with a reference to the map and a int to represent player type PLAYER:0, BOT:1, WILD:2
    public Player(Map _map, playerType _type, String _name){
        map = _map;
        name = _name;
        type = _type;
        //set start tile
    }

    public void setStart(int x, int y){
        map.setOwner(x,y,this);

    }
    public void update(){
        playerTiles = map.getTiles(this);
        maxTroops = playerTiles.size() * maxTroopsMultiplier;
        float avgGrowthRate = getAverageGrowthRate();
        float growthRate = calculateGrowthRate(avgGrowthRate, maxTroops, troopCount);
        troopCount += growthRate;
        Gdx.app.log("Player","Current growth rate: "+growthRate+" troopCount: "+troopCount+" maxTroops: "+maxTroops);
        // Ensure we don't exceed max troops
        troopCount = Math.min(troopCount, maxTroops);
    }

    private float getAverageGrowthRate() {
        if (playerTiles.isEmpty()) return 0;

        float growthRateSum = 0;
        for (Tile tile : playerTiles) {
            growthRateSum += tile.getGrowthRate();
        }
        return growthRateSum / playerTiles.size();
    }

    private float calculateGrowthRate(float avgGrowthRate, float maxTroops, float currentTroops) {
        if (maxTroops <= 0 || currentTroops <= 0) return 0;

        // Calculate the logistic growth factor
        // This creates a bell curve that peaks at maxTroops/2
        float logisticFactor = 0.1f * (currentTroops / maxTroops) * (1 - (currentTroops / maxTroops));

        // Apply the growth rate
        return currentTroops * avgGrowthRate * logisticFactor;
    }

    public playerType getType() {
        return type;
    }
}
