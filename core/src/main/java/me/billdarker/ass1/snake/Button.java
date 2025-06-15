//Class derived from week 4 practical of Android Games Development INFT 3034 SP2 2025 by UniSA

package me.billdarker.ass1.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {

    float x;
    float y;
    float w;
    float h;
    boolean isDown;

    Texture textureUp;
    Texture textureDown;

    public Button(float x, float y, float w, float h, Texture textureUp, Texture textureDown) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        isDown = false;

        this.textureUp = textureUp;
        this.textureDown = textureDown;
    }

    public void update(boolean checkTouch, int touchX, int touchY) {
        isDown = false;

        if (checkTouch) {
            int h2 = Gdx.graphics.getHeight();
            //Touch coordinates have origin in top-left instead of bottom left
            if (touchX >= x && touchX <= x + w && h2 - touchY >= y && h2 - touchY <= y + h) {
                isDown = true;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        if (!isDown) {
            batch.draw(textureUp, x, y, w, h);
        } else {
            batch.draw(textureDown, x, y, w, h);
        }
    }
}
