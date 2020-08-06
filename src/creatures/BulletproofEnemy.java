package creatures;

import audio.AudioPlayer;
import audio.Sounds;
import game.Handler;
import graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class BulletproofEnemy extends Enemy {

    private static final float ENEMY_SPEED = 1.8f;
    private static final int SIZE_OF_REACH = 200;

    private Rectangle headBounds;
    private BufferedImage testTexture;

    public BulletproofEnemy(Handler pHandler, float x, float y) {
        super(pHandler, x, y);

        speed = ENEMY_SPEED;
        sizeOfReach = SIZE_OF_REACH;
        /*
         bounds.x = 20;
        bounds.y = 2;
        bounds.width = 22;
        bounds.height = 60;
         */
        headBounds = new Rectangle(19, 1, 24, 25);

        //temporary
        testTexture = Assets.bulletproof_enemy_test;

    }

    @Override
    public void tick() {
        //temporary commented
        //super.tick();
        simpleAIMove();
        move();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) (x + headBounds.x - handler.getGameCamera().getxOffset()),
        (int) (y + headBounds.y - handler.getGameCamera().getyOffset()), headBounds.width, headBounds.height);

        g.drawImage(testTexture,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }

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

    @Override
    public AudioPlayer getSound() {
        //maybe change later for specific sound
        return Sounds.getSound("enemy_death");
    }

    /*
    public Rectangle getCollisionBounds(float xOffset, float yOffset){
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
    }
     */

    public Rectangle getCollisionHeadBounds(float xOffset, float yOffset) {
        return new Rectangle((int) (x + headBounds.x + xOffset), (int) (y + headBounds.y + yOffset), headBounds.width, headBounds.height);
    }
}
