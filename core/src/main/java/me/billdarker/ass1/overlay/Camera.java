package me.billdarker.ass1.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Camera {

    public OrthographicCamera camera;

    private float viewportWidth = 640f;
    private float viewportHeight = 640f;
    private float xMoveScaling = 5f;
    private float yMoveScaling = 5f;

    private float nextX;
    private float nextY;
    
    // Map bounds
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;
    private static final int TILE_SIZE = 32; // Match the tile size from Map class

    public Camera(int mapWidth, int mapHeight) {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        float aspectRatio = width / height;
        if (aspectRatio > 1) {
            viewportWidth = viewportHeight * aspectRatio;
        } else {
            viewportHeight = viewportWidth / aspectRatio;
        }

        camera = new OrthographicCamera(viewportWidth, viewportHeight);
        
        // Calculate map bounds in world coordinates
        float mapWorldWidth = mapWidth * TILE_SIZE;
        float mapWorldHeight = mapHeight * TILE_SIZE;
        
        // Set bounds to keep map in view with some padding
        // Add extra padding to allow more movement
        minX = 0;
        maxX = mapWorldWidth;
        minY = 0;
        maxY = mapWorldHeight;
        
        // Set initial camera position to center of map
        camera.position.set(mapWorldWidth / 2, mapWorldHeight / 2, 0);
        
        // Log initial values for debugging
        Gdx.app.log("Camera", String.format("Map size: %dx%d, World size: %.0fx%.0f", 
            mapWidth, mapHeight, mapWorldWidth, mapWorldHeight));
        Gdx.app.log("Camera", String.format("Viewport: %.0fx%.0f", viewportWidth, viewportHeight));
        Gdx.app.log("Camera", String.format("Bounds: X[%.0f, %.0f] Y[%.0f, %.0f]", 
            minX, maxX, minY, maxY));
        Gdx.app.log("Camera", String.format("Initial position: %.0f, %.0f", 
            camera.position.x, camera.position.y));
        
        camera.update();
    }

    public void resize(int width, int height) {
        float aspectRatio = width / (float)height;
        if (aspectRatio > 1) {
            viewportWidth = viewportHeight * aspectRatio;
        } else {
            viewportHeight = viewportWidth / aspectRatio;
        }

        camera.viewportWidth = viewportWidth;
        camera.viewportHeight = viewportHeight;
        
        // Update bounds based on new viewport size
        // Keep the same world bounds but adjust for viewport
        float mapWorldWidth = maxX;
        float mapWorldHeight = maxY;
        
        // Ensure bounds are valid
        minX = 0;
        maxX = mapWorldWidth;
        minY = 0;
        maxY = mapWorldHeight;
        
        // Log resize values for debugging
        Gdx.app.log("Camera", String.format("Resize - Viewport: %.0fx%.0f", viewportWidth, viewportHeight));
        Gdx.app.log("Camera", String.format("Resize - Bounds: X[%.0f, %.0f] Y[%.0f, %.0f]", 
            minX, maxX, minY, maxY));
        
        camera.update();
    }

    public void update(float delta, SpriteBatch batch) {
        // Calculate new position
        float newX = camera.position.x + nextX * delta;
        float newY = camera.position.y + nextY * delta;
        
        // Clamp position within bounds
        camera.position.x = MathUtils.clamp(newX, minX, maxX);
        camera.position.y = MathUtils.clamp(newY, minY, maxY);
        
        // Log position for debugging
        if (nextX != 0 || nextY != 0) {
            Gdx.app.log("Camera", String.format("Position: %.0f, %.0f", 
                camera.position.x, camera.position.y));
        }
        
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public void slidePosition(float deltaX, float deltaY) {
        nextX = -deltaX * xMoveScaling;
        nextY = deltaY * yMoveScaling;
        Gdx.app.log("Camera", String.format("Slide - Delta: %.2f, %.2f, Next: %.2f, %.2f", 
            deltaX, deltaY, nextX, nextY));
    }

    public void stopMovement() {
        nextX = 0;
        nextY = 0;
    }
}
