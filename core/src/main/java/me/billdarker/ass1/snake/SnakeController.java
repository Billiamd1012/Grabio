package me.billdarker.ass1.snake;

import com.badlogic.gdx.Gdx;

import me.billdarker.ass1.world.Map;
import me.billdarker.ass1.world.Player;
import me.billdarker.ass1.world.Tile;
import me.billdarker.ass1.world.playerType;

public class SnakeController {
    //on update set next tile in direction to tail and check if collisions occur
    //if next tile == goal tile set to game screen
    // all tiles in bot set to player
    //if collisions occur set screen to game over
    //if no collisions occur do nothing
    public Map map;
    private Direction direction = Direction.UP;
    private Direction previousDirection;
    private int x = 0;
    private int y = 0;
    private final int goalX;
    private final int goalY;

    private final Player snake;

    public SnakeController(Map _map, Player bot){
        map = _map;
        goalX = bot.getStartX();
        goalY = bot.getStartY();
        snake = new Player(map, playerType.PLAYER,"");
        map.setOwner(0,0,snake);
    }

    public void move(){
        switch (direction){
            case UP:
                y += 1;
                break;
            case DOWN:
                y -= 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
        }
    }
    public void update(){
        map.setOwner(x,y,snake);
    }

    public boolean hasGoal(){
        return x == goalX && y == goalY;
    }

    public boolean hasDied(){
        //is within map bounds
        if (x < 0 || x > map.getWidth()-1 || y < 0 || y > map.getHeight()-1){
            Gdx.app.log("Snake","Hit edge of map");
            return true;
        }
        //has not hit itself
        if (map.getTile(x,y).owner == snake){

            Gdx.app.log("Snake","Hit itself" + x + " " + y);

            return true;
        }
        //has not hit edge of bot
        boolean hitBotBorder = true;
        for (Tile tile : map.getTile(x,y).owner.getTiles()){
            if (tile.getX() == x && tile.getY() == y){
                hitBotBorder = false;
                break;
            }
        }
        if (hitBotBorder){
            Gdx.app.log("Snake","Hit border");
            return true;
        }

        return false;
    }

    public void setDirection(Direction incomingDirection) {
        switch (incomingDirection){
            case UP:
                if (direction != Direction.DOWN){
                    direction = incomingDirection;
                }
                break;
            case DOWN:
                if (direction != Direction.UP){
                    direction = incomingDirection;
                }
                break;
            case LEFT:
                if (direction != Direction.RIGHT){
                    direction = incomingDirection;
                }
                break;
            case RIGHT:
                if (direction != Direction.LEFT){
                    direction = incomingDirection;
                }
                break;
        }

    }

}
