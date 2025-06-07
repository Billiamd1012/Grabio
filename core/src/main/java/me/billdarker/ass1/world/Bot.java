package me.billdarker.ass1.world;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class Bot extends Player {
    //this bot has a simple function of clicking one of it's neighbours at random whenever it's population is full
    private ArrayList<Tile> adjacent = new ArrayList<Tile>();
    private final Random random = new Random();


    public Bot(Map _map, playerType _type, String _name) {
        super(_map, _type, _name);
    }

    @Override
    public void update() {
        super.update();
        if (getPopulation() == getMaxPopulation()){
            //attack a neighbour
            getAdjacent();
            if (!adjacent.isEmpty()) {
                Tile defendingTile = adjacent.get(random.nextInt(adjacent.size()));
                attack(defendingTile);
            }
        }
    }

    private void getAdjacent() {
        adjacent.clear(); // Clear previous adjacent tiles
        List<Tile> botTiles = getTiles();
        if (botTiles == null) return;
        
        for (Tile tile : botTiles) {
            if (tile == null) continue;
            
            //for each tile check adjacent
            //above
            int above_x = tile.getX();
            int above_y = tile.getY() + 1;
            if (above_y < map.getHeight()) {
                Tile aboveTile = map.getTile(above_x, above_y);
                if (aboveTile != null && aboveTile.owner != this){
                    adjacent.add(aboveTile);
                }
            }
            //below
            int below_x = tile.getX();
            int below_y = tile.getY() - 1;
            if (below_y >= 0) {
                Tile belowTile = map.getTile(below_x, below_y);
                if (belowTile != null && belowTile.owner != this){
                    adjacent.add(belowTile);
                }
            }

            //left
            int left_x = tile.getX() - 1;
            int left_y = tile.getY();
            if (left_x >= 0) {
                Tile leftTile = map.getTile(left_x, left_y);
                if (leftTile != null && leftTile.owner != this){
                    adjacent.add(leftTile);
                }
            }

            //right
            int right_x = tile.getX() + 1;
            int right_y = tile.getY();
            if (right_x < map.getWidth()) {
                Tile rightTile = map.getTile(right_x, right_y);
                if (rightTile != null && rightTile.owner != this){
                    adjacent.add(rightTile);
                }
            }
        }
    }
}
