package creatures;

import entities.Entity;
import game.Game;
import game.Handler;
import tiles.Tile;

public abstract class Creature extends Entity {

    public static final int DEFAULT_HEALTH = 10;
    public static final float DEFAULT_SPEED = 2.5f;
    public static final int DEFAULT_CREATURE_WIDTH = 64;
    public static final int DEFAULT_CREATURE_HEIGHT = 64;

    protected int health;
    protected float speed;
    protected float xMove, yMove;

    public Creature(Handler pHandler, float x, float y, int pWidth, int pHeight) {
        super(pHandler, x, y,pWidth,pHeight);
        health = DEFAULT_HEALTH;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
        //width = DEFAULT_CREATURE_WIDTH;
        //height = DEFAULT_CREATURE_HEIGHT;
    }

    public void move(){
        /*if (xMove != 0 && yMove != 0){
            xMove = xMove / 2 + xMove / 4;
            yMove = yMove / 2 + yMove / 4;
        }*/
        if (!chceckEntityCollision(xMove, 0f))
            moveX();
        if (!chceckEntityCollision(0f, yMove))
            moveY();
    }

    public void moveX(){
        if (xMove > 0){//moving right
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
            //checking upper and lower right corners of the box
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) /Tile.TILEHEIGHT)){
                x += xMove;
            }else {
                x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
            }
        }else if (xMove < 0){//moving left
            int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
            //checking upper and lower right corners of the box
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) /Tile.TILEHEIGHT)){
                x += xMove;
            }else {
                x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
            }
        }
    }

    public void moveY(){
        if (yMove < 0){//movin up
            int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;

            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                y += yMove;
            }else {
                y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
            }
        }else if (yMove > 0){//moving down
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;

            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                y += yMove;
            }else {
                y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
            }
        }
    }

    protected boolean collisionWithTile(int x, int y){
        return handler.getWorld().getTile(x,y).isSolid();
    }

    //getters and setters

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }

}
