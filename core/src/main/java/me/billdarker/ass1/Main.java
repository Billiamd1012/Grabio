package me.billdarker.ass1;

import com.badlogic.gdx.Game;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public static GameScreen gameScreen;
    public static MenuScreen menuScreen;
    public static AboutScreen aboutScreen;
    public static GameOverScreen gameOverScreen;
    public static VictoryScreen victoryScreen;
    public static SnakeScreen snakeScreen;


    @Override
    public void create() {
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        aboutScreen = new AboutScreen(this);
        gameOverScreen = new GameOverScreen(this);
        victoryScreen = new VictoryScreen(this);
        snakeScreen = new SnakeScreen(this);
        setScreen(menuScreen);
    }

    public void setToGameScreen(){
        setScreen(gameScreen);
    }

    public void setToGameOverScreen() {
        setScreen(gameOverScreen);
    }

    public void setToVictoryScreen() {
        setScreen(victoryScreen);
    }

    public void setToSnakeScreen(){
        setScreen(snakeScreen);
    }
}
