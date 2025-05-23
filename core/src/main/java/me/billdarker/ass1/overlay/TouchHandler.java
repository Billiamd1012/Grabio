package me.billdarker.ass1.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import me.billdarker.ass1.world.Map;

public class TouchHandler implements GestureDetector.GestureListener {
    Camera camera;
    Map map;

    public TouchHandler(Camera _camera, Map _map) {
        camera = _camera;
        map = _map;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        // Convert screen coordinates to tile coordinates
        int[] tileCoords = map.screenToTileCoordinates(x, y, camera.camera);
        int tileX = tileCoords[0];
        int tileY = tileCoords[1];
        
        // Log the tapped tile coordinates
        Gdx.app.log("TouchHandler", "Tapped tile at: " + tileX + ", " + tileY);
        
        // You can also change the tile color to show it was tapped
        
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        //slide camera
        camera.slidePosition(deltaX,deltaY);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        camera.stopMovement();
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
