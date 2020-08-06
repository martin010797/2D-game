package creatures;

import audio.AudioPlayer;
import audio.Sounds;
import game.Handler;
import graphics.Animation;
import graphics.Assets;
import tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class BasicEnemy extends Enemy {

    private static final float ENEMY_SPEED = 1.5f;
    private static final int SIZE_OF_REACH = 150;

    //sound
    //private int deathIndexSound = 0;

    public BasicEnemy(Handler pHandler, float x, float y) {
        super(pHandler, x, y);

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

        sizeOfReach = SIZE_OF_REACH;
    }

    @Override
    public void tick() {
        super.tick();
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
    public AudioPlayer getSound() {
        return Sounds.getSound("enemy_death");
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
}
