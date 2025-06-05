package me.billdarker.ass1;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreen implements Screen {
    /**
     * Handles all main menu user interface elements and interactions.
     * This screen is responsible for displaying the game title, menu options,
     * and processing user input for menu navigation. It provides options for
     * starting the game, accessing settings, and other main menu functionality.
     */

    Main game;

    private SpriteBatch batch;
    private Stage stage;
    private BitmapFont font;
    private float scaleFactor = 5f;
    Texture play = new Texture("Grabio_Play.png");
    Texture name = new Texture("Grabio_lable.png");
    Texture exit = new Texture("Grabio_Exit.png");
    Texture about = new Texture("Grabio_About.png");
    Texture mutemusic = new Texture("Grabio_Music_Mute.png");
    Texture playmusic = new Texture("Grabio_Music_Play.png");
    TextureRegionDrawable drawable_play = new TextureRegionDrawable(new TextureRegion(play));
    TextureRegionDrawable drawable_exit = new TextureRegionDrawable(new TextureRegion(exit));
    TextureRegionDrawable drawable_about = new TextureRegionDrawable(new TextureRegion(about));
    TextureRegionDrawable drawable_playmusic = new TextureRegionDrawable(new TextureRegion(playmusic));
    TextureRegionDrawable drawable_mutemusic = new TextureRegionDrawable(new TextureRegion(mutemusic));
    private boolean Music_Button_flipped = false;

    Music background_music = Gdx.audio.newMusic(Gdx.files.internal("game-music-7408.mp3"));

    public MenuScreen(Main game) {
        background_music.setLooping(true);
        background_music.setVolume(0.5f);
        background_music.play();
        this.game = game;
        font = new BitmapFont();
        font.setColor(Color.WHITE); // Set the color of the text
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();


        Table table = new Table();

        table.setFillParent(true); // Makes the table fill the stage
        stage.addActor(table);

        Image nameImage = new Image(name);
        ImageButton Play_Button = new ImageButton(drawable_play);
        ImageButton About_Button = new ImageButton(drawable_about);
        ImageButton Exit_Button = new ImageButton(drawable_exit);
        ImageButton Music_button = new ImageButton(drawable_playmusic);


        table.add(nameImage).size(1200, 400).colspan(4).center().pad(10);
        table.row();
        table.add(Play_Button).size(400, 200).pad(10);
        table.add(About_Button).size(400, 200).pad(10);
        table.add(Exit_Button).size(400, 200).pad(10);
        table.add(Music_button).size(200).pad(10);






        Play_Button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(Main.gameScreen);
            }
        });
        Exit_Button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        About_Button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(Main.aboutScreen);
            }
        });

        Music_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Music_Button_flipped) {
                    Music_button.getStyle().imageUp = drawable_mutemusic;
                    Music_Button_flipped = true;
                    background_music.pause();
                } else {
                    Music_button.getStyle().imageUp = drawable_playmusic;
                    Music_Button_flipped = false;
                    background_music.play();
                }
            }
        });

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
