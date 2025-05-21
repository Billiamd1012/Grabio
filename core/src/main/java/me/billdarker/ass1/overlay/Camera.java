package me.billdarker.ass1.overlay;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Camera {

    public OrthographicCamera camera;

    private float viewportWidth = 300f;
    private float viewportHeight = 100f;

    private Texture texture;

    public Camera() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(viewportWidth, viewportHeight*height/width);
        camera.position.set(300,0,0);
        camera.update();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = viewportWidth;
        camera.viewportHeight = viewportHeight * height/width;
        camera.update();
    }

    public void update(SpriteBatch batch){
        camera.translate(camera.position.x+2,camera.position.y);
        camera.update();
        Gdx.app.log("Camera","Moved camera to "+camera.position.x);
        batch.setProjectionMatrix(camera.combined);
    }
}
