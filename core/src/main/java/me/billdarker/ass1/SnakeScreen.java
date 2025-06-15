package me.billdarker.ass1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import me.billdarker.ass1.overlay.InputDetector;
import me.billdarker.ass1.overlay.TouchHandler;
import me.billdarker.ass1.snake.Button;
import me.billdarker.ass1.snake.Direction;
import me.billdarker.ass1.snake.SnakeController;
import me.billdarker.ass1.world.Bot;
import me.billdarker.ass1.world.Map;
import me.billdarker.ass1.world.Player;

public class SnakeScreen extends GameState implements Screen {
    //takes in death conditions
    //makes new map based on death conditions
    private SnakeController snakeController;
    private Player bot;
    private ImageButton upButton, downButton, leftButton, rightButton;
    private Texture arrowTexture;
    private Skin skin;
    private Texture backgroundTexture;
    private Texture gamePadBackground;
    private Texture gamePadUp;
    private ImageButton gamePadUpButton;
    private Texture gamePadDown;
    private ImageButton gamePadDownButton;
    private Texture gamePadLeft;
    private ImageButton gamePadLeftButton;
    private Texture gamePadRight;
    private ImageButton gamePadRightButton;
    private final float gamepadScaling = 3f;


    protected final float updateSpeed = 1f;

    public SnakeScreen(Main main) {
        super(main);
    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();

        // Copy game state from GameScreen
        this.map = Main.gameScreen.map;
        this.player = Main.gameScreen.player;
        this.botManager = Main.gameScreen.botManager;
        this.waterSpawn = Main.gameScreen.waterSpawn;
        this.camera = Main.gameScreen.camera;
        Gdx.app.log("Snake","Snake show called");
        super.render(0);

        map.setMaxPopulation();
        //set starting square to snake start
        //get bot that killed player
        for (Bot _bot: botManager.getBots()){
            if (_bot.killedPlayer()){
                bot = _bot;
            }
        }
        snakeController = new SnakeController(map,bot);

        gamePadBackground = new Texture("pixel-art-space-shooter-gui/PNG/Ingame_Interface/ingame_0035_gamepad.png");
        gamePadUp = new Texture("pixel-art-space-shooter-gui/PNG/Ingame_Interface/ingame_0034_arrow.png");
        gamePadDown = new Texture("pixel-art-space-shooter-gui/PNG/Ingame_Interface/ingame_0033_arrow-copy.png");
        gamePadLeft = new Texture("pixel-art-space-shooter-gui/PNG/Ingame_Interface/ingame_0031_arrow-copy-2.png");
        gamePadRight = new Texture("pixel-art-space-shooter-gui/PNG/Ingame_Interface/ingame_0032_arrow-copy-3.png");

        //position gamepad buttons

        Image gamePadBackgroundImage = new Image(gamePadBackground);
        gamePadBackgroundImage.setScale(gamepadScaling);
        gamePadBackgroundImage.setPosition((0.85f*Gdx.graphics.getWidth()),(0.10f*Gdx.graphics.getHeight()));
        stage.addActor(gamePadBackgroundImage);

        float GPU_x = Gdx.graphics.getWidth() * 0.89f;
        float GPU_y = Gdx.graphics.getHeight() * 0.28f;
        float GPU_w = gamePadUp.getWidth() * gamepadScaling;
        float GPU_h = gamePadUp.getHeight() * gamepadScaling;
        gamePadUpButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(gamePadUp)));
        gamePadUpButton.setPosition(GPU_x, GPU_y);
        gamePadUpButton.setSize(GPU_w, GPU_h);
        gamePadUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                snakeController.setDirection(Direction.UP);
            }
        });

        float GPD_x = Gdx.graphics.getWidth() * 0.89f;
        float GPD_y = Gdx.graphics.getHeight() * 0.13f;
        float GPD_w = gamePadDown.getWidth() * gamepadScaling;
        float GPD_h = gamePadDown.getHeight() * gamepadScaling;
        gamePadDownButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(gamePadDown)));
        gamePadDownButton.setPosition(GPD_x, GPD_y);
        gamePadDownButton.setSize(GPD_w, GPD_h);
        gamePadDownButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                snakeController.setDirection(Direction.DOWN);
            }
        });

        float GPL_x = Gdx.graphics.getWidth()*0.86f;
        float GPL_y = Gdx.graphics.getHeight()*0.19f;
        float GPL_w = gamePadLeft.getWidth()*gamepadScaling;
        float GPL_h = gamePadLeft.getHeight()*gamepadScaling;
        gamePadLeftButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(gamePadLeft)));
        gamePadLeftButton.setPosition(GPL_x, GPL_y);
        gamePadLeftButton.setSize(GPL_w, GPL_h);
        gamePadLeftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                snakeController.setDirection(Direction.LEFT);
            }
        });

        float GPR_x = Gdx.graphics.getWidth()*0.935f;
        float GPR_y = Gdx.graphics.getHeight()*0.19f;
        float GPR_w = gamePadRight.getWidth()*gamepadScaling;
        float GPR_h = gamePadRight.getHeight()*gamepadScaling;
        gamePadRightButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(gamePadRight)));
        gamePadRightButton.setPosition(GPR_x, GPR_y);
        gamePadRightButton.setSize(GPR_w, GPR_h);
        gamePadRightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                snakeController.setDirection(Direction.RIGHT);
            }
        });

        // Add buttons to stage
        stage.addActor(gamePadUpButton);
        stage.addActor(gamePadDownButton);
        stage.addActor(gamePadLeftButton);
        stage.addActor(gamePadRightButton);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        GestureDetector.GestureListener touchHandler = new TouchHandler(camera, map, player);
        GestureDetector inputDetector = new InputDetector(touchHandler);

        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(inputDetector);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }
    public void update(){
        snakeController.move();
        if (snakeController.hasGoal()){
            game.setToGameScreen();
        }
        if (snakeController.hasDied()){
            game.setToGameOverScreen();
        }
        snakeController.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update game state every 100ms (10 times per second)
        lastUpdateTime += delta;
        if (lastUpdateTime >= updateSpeed) {
            update();
            lastUpdateTime = 0f;
        }

        // Update camera position if needed
        camera.update(delta, batch);

        // Draw game elements
        batch.begin();
        map.draw(batch);
        batch.end();

        // Draw UI on top
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundTexture.dispose();
        gamePadBackground.dispose();
        gamePadUp.dispose();
        gamePadDown.dispose();
        gamePadLeft.dispose();
        gamePadRight.dispose();

    }
}
