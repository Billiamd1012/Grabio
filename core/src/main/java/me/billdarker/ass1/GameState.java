package me.billdarker.ass1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;

import me.billdarker.ass1.overlay.Camera;
import me.billdarker.ass1.overlay.InputDetector;
import me.billdarker.ass1.overlay.Overlay;
import me.billdarker.ass1.overlay.TouchHandler;
import me.billdarker.ass1.world.BotManager;
import me.billdarker.ass1.world.Map;
import me.billdarker.ass1.world.Player;
import me.billdarker.ass1.world.WaterSpawn;
import me.billdarker.ass1.world.playerType;

public class GameState implements Screen {
    Main game;
    protected SpriteBatch batch;
    protected Stage stage;
    protected Map map;
    protected Player player;
    protected BotManager botManager;
    protected WaterSpawn waterSpawn;
    protected Camera camera;
    protected float lastUpdateTime = 0f;
    //how often game updates 0.5f = twice a second
    protected final float updateSpeed = 0.2f;

    public GameState(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        map = new Map(20, 20); // Create a 20x20 tile map
        player = new Player(map, playerType.PLAYER, "Player"); // Create a neutral player for unowned tiles
        botManager = new BotManager(5,map);
        waterSpawn = new WaterSpawn(5, map);
        camera = new Camera(map.getWidth(), map.getHeight());

        // Set viewport to match map size (20 tiles * 32 pixels)

        //set start tile
        player.setStart(0,0);

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

    public void update(){

    }
}
