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

public class DeathScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Stage stage;
    private BitmapFont font;
    private float scaleFactor = 5f;

    public DeathScreen(Main game) {
        this.game = game;
        font = new BitmapFont();
        font.setColor(Color.RED); // Set the color to red for death screen
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();

        // Create death message
        Label label = new Label("You Died!", new Label.LabelStyle(font, Color.RED));
        label.setFontScale(scaleFactor);
        label.setPosition(Gdx.graphics.getWidth() / 2f - label.getWidth() * scaleFactor / 2f,
                         Gdx.graphics.getHeight() / 2f - label.getHeight() * scaleFactor / 2f);
        stage.addActor(label);

        // Create menu button
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;
        TextButton menuButton = new TextButton("Return to Menu", textButtonStyle);
        menuButton.getLabel().setFontScale(scaleFactor * 0.8f);

        // Position the button below the death message
        menuButton.setPosition(
            Gdx.graphics.getWidth() / 2f - menuButton.getWidth() * scaleFactor * 0.8f / 2f,
            Gdx.graphics.getHeight() / 2f - menuButton.getHeight() * scaleFactor * 0.8f / 2f - 100
        );

        // Make it interactive
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(Main.menuScreen);
            }
        });

        stage.addActor(menuButton);
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
        batch.dispose();
        stage.dispose();
        font.dispose();
    }
}
