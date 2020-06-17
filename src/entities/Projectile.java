package entities;

import creatures.Direction;
import creatures.Player;
import game.Handler;
import graphics.Assets;
import tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends Entity {

    private static final float DEFAULT_SPEED = 6.0f;

    private Player player;
    private float speed;
    private float xMove, yMove;
    private Direction direction;
    private BufferedImage projectileImage;
    private boolean destroyed = false;

    public Projectile(Handler pHandler, float x, float y, int pWidth, int pHeight, Direction pDirection, Player pPlayer) {
        super(pHandler, x, y, pWidth, pHeight);

        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
        direction = pDirection;
        player = pPlayer;

        switch (direction){
            case UP:
                projectileImage = Assets.projectile_up;
                break;
            case DOWN:
                projectileImage = Assets.projectile_down;
                break;
            case LEFT:
                projectileImage = Assets.projectile_left;
                break;
            case RIGHT:
                projectileImage = Assets.projectile_right;
                break;
            case UP_LEFT:
                projectileImage = Assets.projectile_up_left;
                break;
            case UP_RIGHT:
                projectileImage = Assets.projectile_up_right;
                break;
            case DOWN_LEFT:
                projectileImage = Assets.projectile_down_left;
                break;
            case DOWN_RIGHT:
                projectileImage = Assets.projectile_down_right;
                break;
        }

        //change later
        bounds.x = 27;
        bounds.y = 27;
        bounds.width = 10;
        bounds.height = 10;
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


    @Override
    public void tick() {
        switch (direction){
            case UP:
                projectileImage = Assets.projectile_up;
                yMove = -speed;
                break;
            case DOWN:
                projectileImage = Assets.projectile_down;
                yMove = speed;
                break;
            case LEFT:
                projectileImage = Assets.projectile_left;
                xMove = -speed;
                break;
            case RIGHT:
                projectileImage = Assets.projectile_right;
                xMove = speed;
                break;
                //temporary
            case UP_LEFT:
                projectileImage = Assets.projectile_up_left;
                yMove = -speed;
                xMove = -speed;
                break;
            case UP_RIGHT:
                projectileImage = Assets.projectile_up_right;
                yMove = -speed;
                xMove = speed;
                break;
            case DOWN_LEFT:
                projectileImage = Assets.projectile_down_left;
                yMove = speed;
                xMove = -speed;
                break;
            case DOWN_RIGHT:
                projectileImage = Assets.projectile_down_right;
                yMove = speed;
                xMove = speed;
                break;
        }

        move();
    }

    @Override
    public void render(Graphics g) {
        //g.setColor(Color.red);
        //g.fillRect((int) (x + bounds.x), (int) (y + bounds.y), bounds.width, bounds.height);
        g.drawImage(projectileImage,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
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
