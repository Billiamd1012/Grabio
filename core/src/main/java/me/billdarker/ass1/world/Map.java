package me.billdarker.ass1.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import java.util.List;

public class Map {
    /**
     * Represents a 2D tile-based game map.
     * The map consists of a grid of tiles, each with specific properties and terrain type.
     * The map has a fixed width and height, and each tile has a standard size of 32 pixels.
     * Tiles can be set individually using coordinates, and the entire map can be rendered
     * using a SpriteBatch.
     */
    private final int width;
    private final int height;
    private final Player wild;
    private final Tile[][] tiles; // 2D array to store tiles
    private static final int TILE_SIZE = 32; // Size of each tile in pixels

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        wild = new Player(this, playerType.WILD, "Wild"); // Create a neutral player for unowned tiles

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                // If x+y is even, set to green (0), if odd set to gray (1)
                spawnTile(x, y, (x + y) % 2 == 0 ? 0 : 1, wild);
            }
        }
    }

    public void update() {
        wild.update();
    }

    public void setOwner(int x, int y, Player owner) {
        if (x >= 0 && x < width && y >= 0 && y < height && tiles[x][y] != null) {
            Player oldOwner = tiles[x][y].owner;
            if (oldOwner != null) {
                oldOwner.removeTile(tiles[x][y]);
            }
            tiles[x][y].setOwner(owner);
            owner.addTile(tiles[x][y]);
        }
    }

    public void tapTile(Player _player, int x, int y) {
        Tile tile = getTile(x, y);
        _player.attack(tile);
    }

    public Tile getTile(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tiles[x][y];
        }
        return null;
    }

    public void spawnTile(int x, int y, int tileType, Player owner) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[x][y] = new Tile(owner, tileType, x, y);
            owner.addTile(tiles[x][y]);
        }
    }

    public void draw(SpriteBatch batch) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y] != null) {
                    tiles[x][y].draw(shapeRenderer);
                }
            }
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
        int tileX = (int) (worldCoords.x / TILE_SIZE);
        int tileY = (int) (worldCoords.y / TILE_SIZE);

        return new int[]{tileX, tileY};
    }


    public boolean isAdjacent(Player attacking, Tile tile) {
        List<Tile> attackingTiles = attacking.getTiles();

        Player defender = tile.owner;

        List<Tile> defendingTiles = defender.getTiles();

        for (Tile attackTile : attackingTiles) {
            for (Tile defendTile : defendingTiles) {
                if (areTilesAdjacent(attackTile, defendTile)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isBordering(Player attacking, Tile tile) {
        List<Tile> attackingTiles = attacking.getTiles();
        for (Tile attackTile : attackingTiles) {
            if (areTilesAdjacent(attackTile, tile)) {
                return true;
            }
        }
        return false;
    }

    private boolean areTilesAdjacent(Tile tile1, Tile tile2) {
        int dx = Math.abs(tile1.getX() - tile2.getX());
        int dy = Math.abs(tile1.getY() - tile2.getY());
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    public void setMaxPopulation() {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                tile.setMaxPopulation();
            }
        }
    }

    public boolean checkPlayerWin() {
        boolean playerWin = true;
        for(Tile[] row : tiles) {
            for (Tile tile: row) {
                if (tile.owner.getType() == playerType.BOT || tile.owner.getType() == playerType.WILD) {
                    playerWin = false;
                    break;
                }
            }
        }
        return playerWin;
    }
}

