package creatures;

import audio.Sounds;
import game.Handler;
import graphics.Animation;
import graphics.Assets;
import tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends Creature {

    private static final int MINVALUEOFDIRECTIONN = 0, MAXVALUEOFDIRECTION = 7, PROBABILITYOFDIRECTIONCHANGE = 1,
            HUNDREDPERCENT = 100, ZERO = 0, UNDEFINED = -1;
    private static final float ENEMY_SPEED = 1.5f;
    private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, UPLEFT = 4, UPRIGHT = 5, DOWNLEFT = 6, DOWNRIGHT = 7;
    private static final int SIZE_OF_REACH = 150;

    private Animation animDown, animUp, animLeft, animRight, animDownLeft, animDownRight, animUpLeft, animUpRight;
    private Direction direction;
    private BufferedImage enemyImage;
    private boolean dead = false;
    private boolean wrongDirection = false;

    //sound
    private int deathIndexSound = 0;

    public Enemy(Handler pHandler, float x, float y) {
        super(pHandler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        direction = Direction.DOWN;
        enemyImage = Assets.enemy;

        bounds.x = 20;
        bounds.y = 2;
        bounds.width = 22;
        bounds.height = 60;

        speed = ENEMY_SPEED;

        //animations
        animDown = new Animation(175, Assets.enemy_down);
        animUp = new Animation(175, Assets.enemy_up);
        animLeft = new Animation(175, Assets.enemy_left);
        animRight = new Animation(175, Assets.enemy_right);
        animDownLeft = new Animation(175, Assets.enemy_down_left);
        animDownRight = new Animation(175, Assets.enemy_down_right);
        animUpLeft = new Animation(175, Assets.enemy_up_left);
        animUpRight = new Animation(175, Assets.enemy_up_right);
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

        simpleAIMove();
        move();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(),(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        //g.drawImage(Assets.enemy,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        //g.setColor(Color.red);
        //g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
        //(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
    }

    @Override
    public void move() {
        if (!chceckEntityCollisionExcludeEntity(xMove, 0f, handler.getWorld().getEntityManager().getPlayer()))
            moveX();
        else wrongDirection = true;
        if (!chceckEntityCollisionExcludeEntity(0f, yMove, handler.getWorld().getEntityManager().getPlayer()))
            moveY();
        else wrongDirection = true;
    }

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

    private void simpleAIMove(){
        Direction dir = checkReachToPlayer();
        if (dir == null){
            //1 percent chance of changing direction
            int changeOfDirection = ThreadLocalRandom.current().nextInt(ZERO, HUNDREDPERCENT + 1);
            //if enemmy want to go to wall he'll change direction;
            if (wrongDirection) {
                changeOfDirection = PROBABILITYOFDIRECTIONCHANGE;
                wrongDirection = false;
            }

            int randomDirection = UNDEFINED;
            if (changeOfDirection <= PROBABILITYOFDIRECTIONCHANGE){
                //choosing random direction
                randomDirection = ThreadLocalRandom.current().nextInt(MINVALUEOFDIRECTIONN, MAXVALUEOFDIRECTION + 1);
                switch (randomDirection){
                    case UP:{
                        direction = Direction.UP;
                        break;
                    }
                    case DOWN:{
                        direction = Direction.DOWN;
                        break;
                    }
                    case LEFT:{
                        direction = Direction.LEFT;
                        break;
                    }
                    case RIGHT:{
                        direction = Direction.RIGHT;
                        break;
                    }
                    case UPLEFT:{
                        direction = Direction.UP_LEFT;
                        break;
                    }
                    case UPRIGHT:{
                        direction = Direction.UP_RIGHT;
                        break;
                    }
                    case DOWNLEFT:{
                        direction = Direction.DOWN_LEFT;
                        break;
                    }
                    case DOWNRIGHT:{
                        direction = Direction.DOWN_RIGHT;
                        break;
                    }
                }
            }
        }else
            direction = dir;

        yMove = 0;
        xMove = 0;
        float diagonalSpeed = (float) (Math.sqrt((double)((speed*speed)/2)));
        if(direction != null){
            switch (direction){
                case UP:
                    yMove = -speed;
                    break;
                case DOWN:
                    yMove = speed;
                    break;
                case RIGHT:
                    xMove = speed;
                    break;
                case LEFT:
                    xMove = -speed;
                    break;
                case DOWN_RIGHT:
                    xMove = diagonalSpeed;
                    yMove = diagonalSpeed;
                    break;
                case DOWN_LEFT:
                    xMove = -diagonalSpeed;
                    yMove = diagonalSpeed;
                    break;
                case UP_LEFT:
                    xMove = -diagonalSpeed;
                    yMove = -diagonalSpeed;
                    break;
                case UP_RIGHT:
                    xMove = diagonalSpeed;
                    yMove = -diagonalSpeed;
                    break;
            }
        }
    }

    public Direction checkReachToPlayer(){
        Player p = handler.getWorld().getEntityManager().getPlayer();
        if(p.isImmortal())
            return null;
        //checking if enemy can reach player
        if (((int) x >= (int) (p.getX() - SIZE_OF_REACH)) && ((int) x <= (int) (p.getX() + SIZE_OF_REACH)) &&
                ((int) y >= (int) (p.getY() - SIZE_OF_REACH)) && ((int) y <= (int) (p.getY() + SIZE_OF_REACH))){
            //checking where is enemy
            if ((int) x > (int) p.getX()){//enemy is on right side of player
                if ((int) y > (int) p.getY()){
                    return Direction.UP_LEFT;
                }else {
                    if ((int) y < (int) p.getY()){
                        return Direction.DOWN_LEFT;
                    }else
                        return Direction.LEFT;
                }
            }else {
                if ((int) x < (int) p.getX()){//enemy is on left side of player
                    if ((int) y > (int) p.getY()){
                        return Direction.UP_RIGHT;
                    }else {
                        if ((int) y < (int) p.getY()){
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

    private BufferedImage getCurrentAnimationFrame(){
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

    public void setDead(boolean dead) {
        if (!this.dead && dead){
            /*if (deathIndexSound == Sounds.SIZE_OF_ENEMY_SOUND_BUFFER){
                deathIndexSound = 0;
            }
            Sounds.sounds.get("enemy_death").get(deathIndexSound).play();
            deathIndexSound++;*/
            Sounds.getSound("enemy_death").play();
            //handler.getWorld().addDefeatedEnemy();
        }
        this.dead = dead;
    }
}
