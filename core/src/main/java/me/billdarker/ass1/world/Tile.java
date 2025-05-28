package me.billdarker.ass1.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

enum TERRAIN {
    //high growth low defense
    GRASS,
    // low growth low defense
    DESERT,
    // low growth high defense
    MOUNTAIN,
    // high growth high defense
    SWAMP
}

public class Tile {
    /*    A tile is an object that has the information
        - Owned by
        - Growth rate
        - Defense bonus
        - Terrain type
    */
    public Player owner;
    private float growthRate;
    private float defenseRate;
    private TERRAIN terrain;
    private static final int TILE_SIZE = 32; // Size of each tile in pixels
    private int x, y; // Position in the grid

    public Tile(Player _owner, int _terrainType, int _x, int _y) {
        this.x = _x;
        this.y = _y;
        this.owner = _owner;

        switch (_terrainType) {
            case 0:
                terrain = TERRAIN.GRASS;
                growthRate = 2f;
                defenseRate = 1f;
                break;
            case 1:
                terrain = TERRAIN.DESERT;
                growthRate = 1f;
                defenseRate = 1f;
                break;
            case 2:
                terrain = TERRAIN.MOUNTAIN;
                growthRate = 1f;
                defenseRate = 2f;
                break;
            case 3:
                terrain = TERRAIN.SWAMP;
                growthRate = 2f;
                defenseRate = 2f;
                break;
        }
    }

    public void draw(ShapeRenderer shapeRenderer) {
        Color tileColor = getTileColor();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(tileColor);
        shapeRenderer.rect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        shapeRenderer.end();
    }

    private Color getTileColor() {
        if (owner.getType() == playerType.PLAYER){
            //TODO: get colour from player
            return Color.RED;
        } else if (owner.getType() == playerType.BOT) {
            return Color.GRAY;
        }
        else {
            switch (terrain) {
                case GRASS:
                    return Color.GREEN;
                case DESERT:
                    return Color.GRAY;
                case MOUNTAIN:
                    return Color.BLUE;
                case SWAMP:
                    return Color.BROWN;
                default:
                    return Color.WHITE;
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getGrowthRate(){
        return growthRate;
    }

    public void setOwner(Player _owner){
        owner = _owner;
    }
}
