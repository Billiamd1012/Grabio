package me.billdarker.ass1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameOverScreen implements Screen {
    Main game;

    private SpriteBatch batch;
    private Stage stage;
    private BitmapFont font;

    Texture play = new Texture("Grabio_Play.png");
    Texture gameover = new Texture("Grabio_Game_Over.png");
    TextureRegionDrawable drawable_play_again = new TextureRegionDrawable(new TextureRegion(play));

    private boolean Music_Button_flipped = false;
    public GameOverScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();


        Table table = new Table();

        table.setFillParent(true); // Makes the table fill the stage
        stage.addActor(table);

        Image gamoverImage = new Image(gameover);
        ImageButton Play_Again_Button = new ImageButton(drawable_play_again);



        table.add(gamoverImage).size(1200, 400).center().pad(10);
        table.row();
        table.add(Play_Again_Button).size(400, 200).pad(10);

        Play_Again_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(Main.menuScreen);
            }
        });

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

