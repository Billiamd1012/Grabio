package me.billdarker.ass1;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {
    /*
    Menu screen implements the ui for the game start screen including options for closing the application accessing the pause menu and setting intial game conditions.
     */

    Main game;

    private SpriteBatch batch;
    private Stage stage;
    private BitmapFont font;
    private float scaleFactor = 5f;

    public MenuScreen(Main game) {
        this.game = game;
        font = new BitmapFont();
        font.setColor(Color.WHITE); // Set the color of the text
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();

        Label label = new Label("Grabio", new Label.LabelStyle(font, Color.WHITE));
        label.setFontScale(scaleFactor);
        label.setPosition(Gdx.graphics.getWidth() / 2f - label.getWidth() * scaleFactor / 2f,
                         Gdx.graphics.getHeight() / 2f - label.getHeight() * scaleFactor / 2f);
        stage.addActor(label);

        // Create a TextButton for better interactivity
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;
        TextButton playButton = new TextButton("Play", textButtonStyle);
        playButton.getLabel().setFontScale(scaleFactor * 0.8f);

        // Position the button below the title
        playButton.setPosition(
            Gdx.graphics.getWidth() / 2f - playButton.getWidth() * scaleFactor * 0.8f / 2f,
            Gdx.graphics.getHeight() / 2f - playButton.getHeight() * scaleFactor * 0.8f / 2f - 100
        );

        // Make it interactive
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(Main.gameScreen);
            }
        });

        stage.addActor(playButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
