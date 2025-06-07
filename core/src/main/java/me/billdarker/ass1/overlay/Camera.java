package me.billdarker.ass1.overlay;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Camera {

    public OrthographicCamera camera;

    private float viewportWidth = 640f;
    private float viewportHeight = 640f;
    private float xMoveScaling = 1f;
    private float yMoveScaling = 1f;

    private float nextX;
    private float nextY;


    public Camera() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        
        float aspectRatio = width / height;
        if (aspectRatio > 1) {
            viewportWidth = viewportHeight * aspectRatio;
        } else {
            viewportHeight = viewportWidth / aspectRatio;
        }
        
        camera = new OrthographicCamera(viewportWidth, viewportHeight);
        camera.position.set(viewportWidth/2, viewportHeight/2, 0);
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
        camera.update();
    }

    public void update(float delta, SpriteBatch batch) {
        camera.translate(nextX * delta, nextY * delta);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public void slidePosition(float deltaX, float deltaY) {
        nextX = -deltaX * xMoveScaling;
        nextY = deltaY * yMoveScaling;
        Gdx.app.log("Camera", "Delta readings x " + deltaX + " y " + deltaY + " nextX " + nextX + " nextY " + nextY);
    }

    public void stopMovement() {
        nextX = 0;
        nextY = 0;
    }
}
