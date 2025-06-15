package me.billdarker.ass1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.input.GestureDetector;

import me.billdarker.ass1.overlay.InputDetector;
import me.billdarker.ass1.overlay.TouchHandler;
import me.billdarker.ass1.overlay.Overlay;


public class GameScreen extends GameState implements Screen {
    /**
     * The main game screen responsible for rendering and updating all game components.
     * This screen handles the core gameplay loop, including:
     * - Rendering the game world and all its visual elements
     * - Updating game logic and entity states
     * - Processing player input and game events
     * - Managing game objects and their interactions
     */

    private Overlay overlay;
    public GameScreen(Main game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        overlay = new Overlay(stage);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        GestureDetector.GestureListener touchHandler = new TouchHandler(camera, map, player);
        GestureDetector inputDetector = new InputDetector(touchHandler);

        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(inputDetector);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    //    gamestate update runs 10 times per second
    public void update(){
        player.update();
        map.update();
        botManager.update();

        // Update overlay with player statistics
        overlay.updatePopulation(player.getPopulation(), player.getMaxPopulation());
        overlay.updateGrowthRate(player.getTerritory().getTroopsAddedThisUpdate());

        // Update territory attack percentage from overlay
        player.getTerritory().setAttackPercentage(overlay.getAttackPercentage());

        if (player.getTiles().isEmpty()){
            game.setToSnakeScreen();
        }

        if (map.checkPlayerWin()){
            game.setToVictoryScreen();
        }
    }

    @Override
    public void dispose() {
        overlay.dispose();
    }
}
