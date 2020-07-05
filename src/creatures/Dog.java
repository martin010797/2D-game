package creatures;

import entities.Coin;
import game.Handler;
import graphics.Animation;
import graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class Dog extends Creature {

    private static final int MINVALUEOFDIRECTIONN = 0, MAXVALUEOFDIRECTION = 7, PROBABILITYOFDIRECTIONCHANGE = 1,
            HUNDREDPERCENT = 100, ZERO = 0, UNDEFINED = -1;

    private static final float DOG_SPEED = 2.7f;
    private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, UPLEFT = 4, UPRIGHT = 5, DOWNLEFT = 6, DOWNRIGHT = 7;

    private Animation animDown, animUp;
    private Direction direction;

    public Dog(Handler pHandler, float x, float y) {
        super(pHandler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        direction = Direction.DOWN;

        //temporary
        bounds.x = 20;
        bounds.y = 2;
        bounds.width = 22;
        bounds.height = 60;

        speed = DOG_SPEED;

        //animations
        animDown = new Animation(175, Assets.dog_down);
        animUp = new Animation(175, Assets.dog_up);
    }

    @Override
    public void tick() {
        animUp.tick();
        animDown.tick();

        simpleAIMove();
        move();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(),(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    private Direction chooseDirection(){
        if (handler.getWorld().getEntityManager().getCoins().isEmpty())
            return null;
        else {
            Coin closestCoin = null;
            float minDifference = UNDEFINED;
            //finding the closest coin
            for (Coin c: handler.getWorld().getEntityManager().getCoins()){
                float differenceX = Math.abs(x - c.getX());
                float differenceY = Math.abs(y - c.getY());
                if (closestCoin == null){
                    closestCoin = c;
                    minDifference = differenceX + differenceY;
                } else {
                    if ((differenceX + differenceY) < minDifference){
                        closestCoin = c;
                        minDifference = differenceX + differenceY;
                    }
                }
            }
            if (closestCoin != null){
                if ( (x - (int)closestCoin.getX()) > 2){//dog is on right side of coin
                    if ( (y - closestCoin.getY()) > 1){
                        return Direction.UP_LEFT;
                    }else {
                        if ( (y - closestCoin.getY()) < -1){
                            return Direction.DOWN_LEFT;
                        }else
                            return Direction.LEFT;
                    }
                }else {
                    if ((x - closestCoin.getX()) < -2){//dog is on left side of coin
                        if ((y - closestCoin.getY()) > 1){
                            return Direction.UP_RIGHT;
                        }else {
                            if ((y - closestCoin.getY()) < -1){
                                return Direction.DOWN_RIGHT;
                            }else
                                return Direction.RIGHT;
                        }
                    }else {//dog is on same x position with coin
                        if ((int) y > (int) closestCoin.getY()){
                            return Direction.UP;
                        }else {
                            if ((int) y < (int) closestCoin.getY())
                                return Direction.DOWN;
                        }
                    }
                }
               /*if ((int) x > (int) closestCoin.getX()){//dog is on right side of coin
                    if ((int) y > (int) closestCoin.getY()){
                        return Direction.UP_LEFT;
                    }else {
                        if ((int) y < (int) closestCoin.getY()){
                            return Direction.DOWN_LEFT;
                        }else
                            return Direction.LEFT;
                    }
                }else {
                    if ((int) x < (int) closestCoin.getX()){//dog is on left side of coin
                        if ((int) y > (int) closestCoin.getY()){
                            return Direction.UP_RIGHT;
                        }else {
                            if ((int) y < (int) closestCoin.getY()){
                                return Direction.DOWN_RIGHT;
                            }else
                                return Direction.RIGHT;
                        }
                    }else {//dog is on same x position with coin
                        if ((int) y > (int) closestCoin.getY()){
                            return Direction.UP;
                        }else {
                            if ((int) y < (int) closestCoin.getY())
                                return Direction.DOWN;
                        }
                    }
                }*/
            }
        }
        return null;
    }

    private void simpleAIMove(){
        Direction dir = chooseDirection();
        if (dir == null){
            //1 percent chance of changing direction
            int changeOfDirection = ThreadLocalRandom.current().nextInt(ZERO, HUNDREDPERCENT + 1);
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

    private BufferedImage getCurrentAnimationFrame(){
        /*if (xMove < 0){//moving left
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
        return Assets.enemy_down[0];*/

        //temporary
        return animDown.getCurrentFrame();
    }
}
