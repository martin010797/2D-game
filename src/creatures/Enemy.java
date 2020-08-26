package creatures;

import audio.AudioPlayer;
import audio.Sounds;
import game.Handler;
import graphics.Animation;
import graphics.Assets;
import tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Enemy extends Creature {

    protected static final int MINVALUEOFDIRECTIONN = 0, MAXVALUEOFDIRECTION = 7, PROBABILITYOFDIRECTIONCHANGE = 1,
            HUNDREDPERCENT = 100, ZERO = 0, UNDEFINED = -1, NEGLIGIBLE_DIFFERENCE = 20;
    protected static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, UPLEFT = 4, UPRIGHT = 5, DOWNLEFT = 6, DOWNRIGHT = 7;

    protected static int sizeOfReach = 150;
    protected Animation animDown, animUp, animLeft, animRight, animDownLeft, animDownRight, animUpLeft, animUpRight;
    protected Direction direction;
    protected BufferedImage enemyImage;
    protected boolean dead = false;
    protected boolean wrongDirection = false;

    //sound
    protected int deathIndexSound = 0;

    public Enemy(Handler pHandler, float x, float y) {
        super(pHandler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        direction = Direction.DOWN;
        enemyImage = Assets.enemy;

        bounds.x = 20;
        bounds.y = 2;
        bounds.width = 22;
        bounds.height = 60;

    }

    @Override
    public void tick() {
        //animations
        animDown.tick();
        animUp.tick();
        animRight.tick();
        animLeft.tick();
        animUpLeft.tick();
        animUpRight.tick();
        animDownLeft.tick();
        animDownRight.tick();

    }

    @Override
    public abstract void render(Graphics g);

    //overriden because of changing direction
    //enemies kept walking into wall
    @Override
    public void moveX() {
        if (xMove > 0){//moving right
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
            //checking upper and lower right corners of the box
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) /Tile.TILEHEIGHT)){
                x += xMove;
            }else {
                x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
                wrongDirection = true;
            }
        }else if (xMove < 0){//moving left
            int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
            //checking upper and lower right corners of the box
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) /Tile.TILEHEIGHT)){
                x += xMove;
            }else {
                x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
                wrongDirection = true;
            }
        }
    }

    @Override
    public void moveY() {
        if (yMove < 0){//movin up
            int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;

            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                y += yMove;
            }else {
                y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
                wrongDirection = true;
            }
        }else if (yMove > 0){//moving down
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;

            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                y += yMove;
            }else {
                y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
                wrongDirection = true;
            }
        }
    }

    protected BufferedImage getCurrentAnimationFrame(){
        if (xMove < 0){//moving left
            if (yMove > 0)
                return animDownLeft.getCurrentFrame();
            if(yMove < 0)
                return animUpLeft.getCurrentFrame();
            return animLeft.getCurrentFrame();
        }else if (xMove > 0){//moving right
            if (yMove > 0)
                return animDownRight.getCurrentFrame();
            if (yMove < 0)
                return animUpRight.getCurrentFrame();
            else
                return animRight.getCurrentFrame();
        }else if (yMove < 0){//moving up
            return animUp.getCurrentFrame();
        }else if (yMove > 0){//moving down
            return animDown.getCurrentFrame();
        }
        //default value
        return Assets.enemy_down[0];
    }

    public boolean isDead() {
        return dead;
    }

    //override in specific class for different sound
    public void setDead(boolean dead) {
        if (!this.dead && dead){
            //Sounds.getSound("enemy_death").play();
            getSound().play();
        }
        this.dead = dead;
    }

    public abstract AudioPlayer getSound();

    public Direction checkReachToPlayer(){
        Player p = handler.getWorld().getEntityManager().getPlayer();
        if(p.isImmortal())
            return null;
        //checking if enemy can reach player
        if (((int) x >= (int) (p.getX() - sizeOfReach)) && ((int) x <= (int) (p.getX() + sizeOfReach)) &&
                ((int) y >= (int) (p.getY() - sizeOfReach)) && ((int) y <= (int) (p.getY() + sizeOfReach))){
            //checking where is enemy
            if ((int) x > (int) (p.getX() + NEGLIGIBLE_DIFFERENCE)){//enemy is on right side of player
                if ((int) y > (int) (p.getY() + NEGLIGIBLE_DIFFERENCE)){
                    return Direction.UP_LEFT;
                }else {
                    if ((int) y < (int) (p.getY() - NEGLIGIBLE_DIFFERENCE)){
                        return Direction.DOWN_LEFT;
                    }else
                        return Direction.LEFT;
                }
            }else {
                if ((int) x < (int) (p.getX() - NEGLIGIBLE_DIFFERENCE)){//enemy is on left side of player
                    if ((int) y > (int) (p.getY() + NEGLIGIBLE_DIFFERENCE)){
                        return Direction.UP_RIGHT;
                    }else {
                        if ((int) y < (int) (p.getY() - NEGLIGIBLE_DIFFERENCE)){
                            return Direction.DOWN_RIGHT;
                        }else
                            return Direction.RIGHT;
                    }
                }else {//enemy is on same x position with player
                    if ((int) y > (int) p.getY()){
                        return Direction.UP;
                    }else {
                        if ((int) y < (int) p.getY())
                            return Direction.DOWN;
                    }
                }
            }

        }
        return null;
    }
}
