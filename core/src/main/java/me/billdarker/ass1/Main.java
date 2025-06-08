package me.billdarker.ass1;

import com.badlogic.gdx.Game;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public static GameScreen gameScreen;
    public static MenuScreen menuScreen;
    public static AboutScreen aboutScreen;
    public static GameOverScreen gameOverScreen;

    @Override
    public void create() {
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        aboutScreen = new AboutScreen(this);
        gameOverScreen = new GameOverScreen(this);
        setScreen(menuScreen);
    }

    public void setToGameScreen(){
        setScreen(gameScreen);
    }
}
