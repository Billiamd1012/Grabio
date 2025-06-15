package me.billdarker.ass1.world;

import java.util.ArrayList;
import java.util.Random;

public class BotManager {
    //spawns a set number of bots on the map at random places
    private Map map;
    private ArrayList<Bot> bots = new ArrayList<Bot>();

    private final Random random = new Random();

    public BotManager(int numberOfBots, Map _map){
        map = _map;
        for (int x = 0; x < numberOfBots; x ++){
            int randomX = random.nextInt(map.getWidth());
            int randomY = random.nextInt(map.getHeight());
            if (map.getTile(randomX,randomY).owner.getType() == playerType.WILD){
                //TODO: add random bot names
                Bot nextBot = new Bot(_map,playerType.BOT,"BOT");
                map.setOwner(randomX,randomY,nextBot);
                bots.add(nextBot);
            }
        }
    }

    public void update(){
        for (Bot bot: bots){
            bot.update();
        }
    }

    public ArrayList<Bot> getBots(){
        return bots;
    }
}
