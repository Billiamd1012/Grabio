package me.billdarker.ass1;

import com.badlogic.gdx.Game;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public static GameScreen gameScreen;
    public static MenuScreen menuScreen;
    public static AboutScreen aboutScreen;
    public static GameOverScreen gameOverScreen;
    public static DeathScreen deathScreen;
    public static VictoryScreen victoryScreen;

    @Override
    public void create() {
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        aboutScreen = new AboutScreen(this);
        gameOverScreen = new GameOverScreen(this);
        deathScreen = new DeathScreen(this);
        victoryScreen = new VictoryScreen(this);
        setScreen(gameOverScreen);
    }

    public void setToGameScreen(){
        setScreen(gameScreen);
    }

    public void setToDeathScreen() {
        setScreen(deathScreen);
    }

    public void setToVictoryScreen() {
        setScreen(victoryScreen);
    }
}
