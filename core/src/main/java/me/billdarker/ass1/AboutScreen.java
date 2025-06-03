package me.billdarker.ass1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AboutScreen implements Screen {
    Main game;
    private Stage stage;


    Texture back = new Texture("Grabio_Back.png");

    TextureRegionDrawable drawable_back = new TextureRegionDrawable(new TextureRegion(back));

    public AboutScreen(Main game) {
        this.game = game;
    }
    @Override
    public void show() {
        SpriteBatch batch = new SpriteBatch();
        stage = new Stage();


        Table table = new Table();

        table.setFillParent(true); // Makes the table fill the stage
        stage.addActor(table);


        ImageButton Back_Button = new ImageButton(drawable_back);
        table.row();

        table.add(Back_Button).size(500, 300).pad(10);


        Back_Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(Main.menuScreen);
            }
        });


        Gdx.input.setInputProcessor(stage);
    }

        @Override
        public void render ( float delta){
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.draw();

        }

        @Override
        public void resize ( int width, int height){

        }

        @Override
        public void pause () {

        }

        @Override
        public void resume () {

        }

        @Override
        public void hide () {

        }

        @Override
        public void dispose () {

        }


}
