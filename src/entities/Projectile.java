package entities;

import creatures.Direction;
import creatures.Player;
import game.Handler;
import graphics.Assets;
import tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Projectile extends Entity {

    //private static final float DEFAULT_SPEED = 6.0f;

    protected Player player;
    protected float speed;
    protected float xMove, yMove;
    protected Direction direction;
    protected BufferedImage projectileImage;
    protected boolean destroyed = false;

    public Projectile(Handler pHandler, float x, float y, int pWidth, int pHeight, Direction pDirection, Player pPlayer) {
        super(pHandler, x, y, pWidth, pHeight);

        //speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
        direction = pDirection;
        player = pPlayer;
    }

    public void move(){
        //if (!chceckEntityCollision(xMove, 0f))
        if (!chceckEntityCollisionExcludeEntity(xMove, 0f, player))
            moveX();
        else
            destroyed = true;
        if (!chceckEntityCollisionExcludeEntity(0f, yMove, player))
            moveY();
        else
            destroyed = true;
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
                destroyed = true;
            }
        }else if (xMove < 0){//moving left
            int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
            //checking upper and lower right corners of the box
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) /Tile.TILEHEIGHT)){
                x += xMove;
            }else {
                x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
                destroyed = true;
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
                destroyed = true;
            }
        }else if (yMove > 0){//moving down
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;

            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                y += yMove;
            }else {
                y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
                destroyed = true;
            }
        }
    }

    private boolean collisionWithTile(int x, int y){
        return handler.getWorld().getTile(x,y).isSolid();
    }

    //getters and setters

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

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
