package me.billdarker.ass1;

import com.badlogic.gdx.Game;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public static GameScreen gameScreen;
    public static MenuScreen menuScreen;

    @Override
    public void create() {
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);

        setScreen(gameScreen);
    }

    public void setToGameScreen(){
        setScreen(gameScreen);
    }
}
