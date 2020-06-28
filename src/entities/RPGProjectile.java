package entities;

import creatures.Direction;
import creatures.Player;
import game.Handler;
import graphics.Assets;

import java.awt.*;

public class RPGProjectile extends Projectile {

    public static final int PRICE = 15;
    private static final float DEFAULT_SPEED = 12.0f;
    public static final int MAX_NUMBER_OF_PROJECTILES = 70;


    public RPGProjectile(Handler pHandler, float x, float y, int pWidth, int pHeight, Direction pDirection, Player pPlayer) {
        super(pHandler, x, y, pWidth, pHeight, pDirection, pPlayer);
        speed = DEFAULT_SPEED;

        switch (direction){
            case UP:
                projectileImage = Assets.rpg_projectile_up;
                bounds.x = 2;
                bounds.y = 19;
                bounds.width = 62;
                bounds.height = 30;
                break;
            case DOWN:
                projectileImage = Assets.rpg_projectile_down;
                bounds.x = 2;
                bounds.y = 19;
                bounds.width = 62;
                bounds.height = 30;
                break;
            case LEFT:
                projectileImage = Assets.rpg_projectile_left;
                bounds.x = 19;
                bounds.y = 2;
                bounds.width = 30;
                bounds.height = 60;
                break;
            case RIGHT:
                projectileImage = Assets.rpg_projectile_right;
                bounds.x = 19;
                bounds.y = 2;
                bounds.width = 30;
                bounds.height = 60;
                break;
            case UP_LEFT:
                projectileImage = Assets.rpg_projectile_up_left;
                bounds.x = 0;
                bounds.y = 17;
                bounds.width = 62;
                bounds.height = 30;
                break;
            case UP_RIGHT:
                projectileImage = Assets.rpg_projectile_up_right;
                bounds.x = 3;
                bounds.y = 17;
                bounds.width = 62;
                bounds.height = 30;
                break;
            case DOWN_LEFT:
                projectileImage = Assets.rpg_projectile_down_left;
                bounds.x = 0;
                bounds.y = 17;
                bounds.width = 62;
                bounds.height = 30;
                break;
            case DOWN_RIGHT:
                projectileImage = Assets.rpg_projectile_down_right;
                bounds.x = 3;
                bounds.y = 17;
                bounds.width = 62;
                bounds.height = 30;
                break;
        }

        //change later
        /*bounds.x = 15;
        bounds.y = 15;
        bounds.width = 35;
        bounds.height = 30;*/
    }

    @Override
    public void tick() {
        float diagonalSpeed = (float) (Math.sqrt((double)((speed*speed)/2)));
        switch (direction){
            case UP:
                yMove = -speed;
                break;
            case DOWN:
                yMove = speed;
                break;
            case LEFT:
                xMove = -speed;
                break;
            case RIGHT:
                xMove = speed;
                break;
            case UP_LEFT:
                yMove = -diagonalSpeed;
                xMove = -diagonalSpeed;
                break;
            case UP_RIGHT:
                yMove = -diagonalSpeed;
                xMove = diagonalSpeed;
                break;
            case DOWN_LEFT:
                yMove = diagonalSpeed;
                xMove = -diagonalSpeed;
                break;
            case DOWN_RIGHT:
                yMove = diagonalSpeed;
                xMove = diagonalSpeed;
                break;
        }

        move();
    }

    @Override
    public void render(Graphics g) {
        if (direction == Direction.LEFT || direction == Direction.RIGHT){
            g.drawImage(projectileImage,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - (int) (height/4) - handler.getGameCamera().getyOffset()), width, height, null);
            g.drawImage(projectileImage,(int) (x - handler.getGameCamera().getxOffset()),(int) (y + (int) (height/4) - handler.getGameCamera().getyOffset()), width, height, null);
        }else {
            g.drawImage(projectileImage,(int) (x - (int) (width/4) - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            g.drawImage(projectileImage,(int) (x + (int) (width/4) - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
        //g.setColor(Color.red);
        //g.fillRect((int) (x + bounds.x), (int) (y + bounds.y), bounds.width, bounds.height);
    }
}
