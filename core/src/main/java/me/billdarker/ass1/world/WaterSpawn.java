package me.billdarker.ass1.world;

import java.util.ArrayList;
import java.util.Random;

public class WaterSpawn {

        private Map map;
        private ArrayList<Water> water = new ArrayList<Water>();

        private final Random random = new Random();

        public WaterSpawn(int numberOfBots, Map _map){
            map = _map;
            for (int x = 0; x < numberOfBots; x ++){
                int randomX = random.nextInt(map.getWidth());
                int randomY = random.nextInt(map.getHeight());
                if (map.getTile(randomX,randomY).owner.getType() == playerType.WILD){
                    //TODO: add random bot names
                    Water nextWater = new Water(_map,playerType.WATER,"WATER");
                    map.setOwner(randomX,randomY,nextWater);
                    water.add(nextWater);
                }
            }
        }
}
