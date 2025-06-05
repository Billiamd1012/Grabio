package me.billdarker.ass1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import me.billdarker.ass1.overlay.Camera;
import me.billdarker.ass1.overlay.InputDetector;
import me.billdarker.ass1.overlay.TouchHandler;
import me.billdarker.ass1.world.Map;
import me.billdarker.ass1.world.Player;
import me.billdarker.ass1.world.playerType;



public class GameScreen implements Screen {
    /**
     * The main game screen responsible for rendering and updating all game components.
     * This screen handles the core gameplay loop, including:
     * - Rendering the game world and all its visual elements
     * - Updating game logic and entity states
     * - Processing player input and game events
     * - Managing game objects and their interactions
     */
    Main game;
    private SpriteBatch batch;
    private Stage stage;
    private Map map;
    private Player player;

    private float viewportWidth = 100f;
    private float viewportHeight = 100f;
    private Camera camera;
    private float lastUpdateTime = 0f;

    Texture add = new Texture("Grabio_Plus_Button.png");
    Texture minus = new Texture("Grabio_Minus_Button.png");
    TextureRegionDrawable drawable_add = new TextureRegionDrawable(new TextureRegion(add));
    TextureRegionDrawable drawable_minus= new TextureRegionDrawable(new TextureRegion(minus));
    Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    BitmapFont font = new BitmapFont(Gdx.files.internal("arial_high_quality.fnt"));
    Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.valueOf("44D49B"));

    BitmapFont font2 = new BitmapFont(Gdx.files.internal("arial_high_quality_Smaller.fnt"));
    Label.LabelStyle labelStyle2 = new Label.LabelStyle(font, Color.valueOf("44D49B"));

    Label percentageTitleLabel = new Label("Troop Attack: % 0" , labelStyle2);
    Label TroopCountTitleLabel = new Label("Troop Count: 0", labelStyle2);
    Label GrowthRateTitleLabel = new Label("Growth Rate: 0", labelStyle2);




    public GameScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {

        batch = new SpriteBatch();
        stage = new Stage();
        map = new Map(20, 20); // Create a 20x20 tile map
        player = new Player(map, playerType.PLAYER, "Player"); // Create a neutral player for unowned tiles


        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        camera = new Camera();

        GestureDetector.GestureListener touchHandler = new TouchHandler(camera, map);
        GestureDetector InputDetector = new InputDetector(touchHandler);
        Gdx.input.setInputProcessor(InputDetector);
        // Set viewport to match map size (20 tiles * 32 pixels)

        //set start tile
        player.setStart(0,0);


        Table overlay = new Table();
        overlay.setFillParent(true);
        ImageButton Add_Button = new ImageButton(drawable_add);
        ImageButton Minus_button = new ImageButton(drawable_minus);





        percentageTitleLabel.setWrap(false);

        overlay.right();
        overlay.add(TroopCountTitleLabel).colspan(4).center().pad(2);
        overlay.row();
        overlay.add(GrowthRateTitleLabel).colspan(4).center().pad(2);
        overlay.row();
        overlay.add(percentageTitleLabel).colspan(4).center().pad(2);
        overlay.row();
        overlay.add(Add_Button).size(200,200).pad(10);
        overlay.add(Minus_button).size(200,200).pad(10);
        overlay.row();

        stage.addActor(overlay);

        Gdx.input.setInputProcessor(stage);
        Add_Button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {

            }
        });
        Minus_button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {

            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update game state every 100ms (10 times per second)
        lastUpdateTime += delta;
        if (lastUpdateTime >= 0.1f) {
            update();
            lastUpdateTime = 0f;
        }

        // Update camera position if needed
        camera.update(delta,batch);

        batch.begin();
        map.draw(batch);
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(width,height);
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
    }

//    gamestate update runs 10 times per second
    public void update(){
        player.update();
        percentageTitleLabel.setText("Troop Attack: %" + number);
        TroopCountTitleLabel.setText("Troop Count: "+ number);
        GrowthRateTitleLabel.setText("Growth Rate: " + number);
    }

}
