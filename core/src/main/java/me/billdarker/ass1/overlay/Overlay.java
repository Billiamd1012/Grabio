package me.billdarker.ass1.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;

public class Overlay {
    private Stage stage;
    private Table table;
    private Label populationLabel;
    private Label maxPopulationLabel;
    private Label growthRateLabel;
    private Slider attackSlider;
    private Label attackPercentageLabel;
    private Skin skin;

    public Overlay(Stage stage) {
        this.stage = stage;
        this.skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));
        initializeUI();
    }

    private void initializeUI() {
        table = new Table(skin);
        table.setFillParent(true);
        table.align(Align.top);
        table.pad(0);

        // Create labels with custom style and larger font
        Label.LabelStyle labelStyle = skin.get("default", Label.LabelStyle.class);
        labelStyle.font.getData().setScale(4.0f);

        // Create labels
        populationLabel = new Label("Population: 0", labelStyle);
        maxPopulationLabel = new Label("Max Population: 0", labelStyle);
        growthRateLabel = new Label("Troops Added: 0", labelStyle);
        attackPercentageLabel = new Label("Attack %: 50", labelStyle);

        // Create slider with custom style
        Slider.SliderStyle sliderStyle = skin.get("default-horizontal", Slider.SliderStyle.class);
        // Scale up the slider's knob and background
        sliderStyle.knob.setMinHeight(60);
        sliderStyle.knob.setMinWidth(60);
        sliderStyle.background.setMinHeight(40);
        attackSlider = new Slider(0, 100, 1, false, sliderStyle);
        attackSlider.setValue(50);

        // Create a container for the UI elements to control their size
        Container<Table> container = new Container<>();
        container.fill();

        // Create a grey background
        NinePatch patch = new NinePatch(skin.getRegion("window"), 10, 10, 10, 10);
        patch.setColor(new Color(0.3f, 0.3f, 0.3f, 1f)); // Dark grey with slight transparency
        container.setBackground(new NinePatchDrawable(patch));

        // Create inner table for the elements
        Table innerTable = new Table();
        innerTable.pad(20);

        // Calculate available width for each element
        float screenWidth = Gdx.graphics.getWidth();
        float elementWidth = (screenWidth - 200) / 4;

        // First row: Population and Max Population
        innerTable.add(populationLabel).width(elementWidth).padRight(20).padBottom(20);
        innerTable.add(maxPopulationLabel).width(elementWidth).padRight(20).padBottom(20);
        innerTable.add().width(elementWidth).padRight(20); // Empty cell
        innerTable.add(attackPercentageLabel).width(elementWidth).padRight(20).padBottom(20);
        innerTable.row();

        // Second row: Growth Rate and Attack Slider
        innerTable.add(growthRateLabel).width(elementWidth).padRight(20).padBottom(20);
        innerTable.add().width(elementWidth).padRight(20); // Empty cell
        innerTable.add().width(elementWidth).padRight(20); // Empty cell
        innerTable.add(attackSlider).width(elementWidth).height(80).padBottom(20);
        innerTable.row();

        // Set the inner table as the container's actor
        container.setActor(innerTable);

        // Add the container to the main table at the top, making it wider than the screen
        table.add(container).expandX().fillX().width(screenWidth * 1.2f).height(Gdx.graphics.getHeight() * 0.15f).top();
        table.row();

        // Add table to stage
        stage.addActor(table);

        // Add slider listener
        attackSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int value = (int) attackSlider.getValue();
                attackPercentageLabel.setText("Attack %: " + value);
            }
        });
    }

    public void updatePopulation(float current, float max) {
        populationLabel.setText(String.format("Population: %.1f", current));
        maxPopulationLabel.setText(String.format("Max Population: %.1f", max));
    }

    public void updateGrowthRate(float troops) {
        growthRateLabel.setText(String.format("Troop Growth: %.1f", troops));
    }

    public float getAttackPercentage() {
        return attackSlider.getValue() / 100f;
    }

    public void dispose() {
        skin.dispose();
    }
}
