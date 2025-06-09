package me.billdarker.ass1.world;


import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import com.badlogic.gdx.Gdx;

public class Water extends Player {
    //this bot has a simple function of clicking one of it's neighbours at random whenever it's population is full
    private ArrayList<Tile> adjacent = new ArrayList<Tile>();
    private final Random random = new Random();


    public Water(Map _map, playerType _type, String _name) {
        super(_map, _type, _name);
    }
}
