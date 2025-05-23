package me.billdarker.ass1.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class Map {
    /**
     * Represents a 2D tile-based game map.
     * The map consists of a grid of tiles, each with a specific type represented by an integer.
     * Each tile is rendered as a colored rectangle where the color is determined by the tile type:
     * - 0: Green
     * - 1: Gray
     * - 2: Blue
     * - 3: Brown
     * - default: White
     * The map has a fixed width and height, and each tile has a standard size of 32 pixels.
     * Tiles can be set individually using coordinates, and the entire map can be rendered
     * using a SpriteBatch.
     */
    private final int width;
    private final int height;
    private final int[][] tiles; // 2D array to store tile types
    private static final int TILE_SIZE = 32; // Size of each tile in pixels

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new int[width][height];
    }

    public void setTile(int x, int y, int tileType) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[x][y] = tileType;
        }
    }

    public void draw(SpriteBatch batch) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color tileColor = getTileColor(tiles[x][y]);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(tileColor);
                shapeRenderer.rect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                shapeRenderer.end();
            }
        }
    }

    private Color getTileColor(int tileType) {
        switch (tileType) {
            case 0:
                return Color.GREEN;
            case 1:
                return Color.GRAY;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.BROWN;
            default:
                return Color.WHITE;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] screenToTileCoordinates(float screenX, float screenY, OrthographicCamera camera) {
        // Convert screen coordinates to world coordinates
        Vector3 worldCoords = new Vector3(screenX, screenY, 0);
        camera.unproject(worldCoords);

        // Convert world coordinates to tile coordinates
        int tileX = (int)(worldCoords.x / TILE_SIZE);
        int tileY = (int)(worldCoords.y / TILE_SIZE);

        return new int[]{tileX, tileY};
    }
}
