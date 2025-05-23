package me.billdarker.ass1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;

import me.billdarker.ass1.overlay.Camera;
import me.billdarker.ass1.overlay.InputDetector;
import me.billdarker.ass1.overlay.TouchHandler;
import me.billdarker.ass1.world.Map;

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

    private float viewportWidth = 100f;
    private float viewportHeight = 100f;
    private Camera camera;

    public GameScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        map = new Map(20, 20); // Create a 20x20 tile map

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        camera = new Camera();

        GestureDetector.GestureListener touchHandler = new TouchHandler(camera, map);
        GestureDetector InputDetector = new InputDetector(touchHandler);
        Gdx.input.setInputProcessor(InputDetector);
        // Set viewport to match map size (20 tiles * 32 pixels)

        // Initialize tiles in a checkerboard pattern
        for(int x = 0; x < map.getWidth(); x++) {
            for(int y = 0; y < map.getHeight(); y++) {
                // If x+y is even, set to green (0), if odd set to gray (1)
                map.setTile(x, y, (x + y) % 2 == 0 ? 0 : 1);
            }
        }


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

    }

}
