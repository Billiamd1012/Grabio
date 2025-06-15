package me.billdarker.ass1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import me.billdarker.ass1.world.Map;
import me.billdarker.ass1.world.Player;

public class SnakeScreen extends GameState implements Screen {
    //takes in death conditions
    //makes new map based on death conditions


    public SnakeScreen(Main main) {
        super(main);
    }
    public void update(){
        Gdx.app.log("Snake","Snake update called");
    }
}
