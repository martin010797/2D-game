package entities;

import creatures.Direction;
import creatures.Player;
import game.Handler;
import graphics.Assets;

import java.awt.*;

public class ShotgunProjectile extends Projectile {

    public static final int PRICE = 20;
    public static final int MAX_NUMBER_OF_PROJECTILES = 40;
    private static final float DEFAULT_SPEED = 10.0f;

    public ShotgunProjectile(Handler pHandler, float x, float y, int pWidth, int pHeight, Direction pDirection, Player pPlayer) {
        super(pHandler, x, y, pWidth, pHeight, pDirection, pPlayer);
        speed = DEFAULT_SPEED;

        switch (direction){
            case UP:
                projectileImage = Assets.shotgun_projectile_up;
                break;
            case UP2:
                projectileImage = Assets.shotgun_projectile_up;
                break;
            case UP3:
                projectileImage = Assets.shotgun_projectile_up;
                break;
            case DOWN:
                projectileImage = Assets.shotgun_projectile_down;
                break;
            case DOWN2:
                projectileImage = Assets.shotgun_projectile_down;
                break;
            case DOWN3:
                projectileImage = Assets.shotgun_projectile_down;
                break;
            case LEFT:
                projectileImage = Assets.shotgun_projectile_left;
                break;
            case LEFT2:
                projectileImage = Assets.shotgun_projectile_left;
                break;
            case LEFT3:
                projectileImage = Assets.shotgun_projectile_left;
                break;
            case RIGHT:
                projectileImage = Assets.shotgun_projectile_right;
                break;
            case RIGHT2:
                projectileImage = Assets.shotgun_projectile_right;
                break;
            case RIGHT3:
                projectileImage = Assets.shotgun_projectile_right;
                break;
            case UP_LEFT:
                projectileImage = Assets.shotgun_projectile_up_left;
                break;
            case UP_LEFT2:
                projectileImage = Assets.shotgun_projectile_up_left;
                break;
            case UP_LEFT3:
                projectileImage = Assets.shotgun_projectile_up_left;
                break;
            case UP_RIGHT:
                projectileImage = Assets.shotgun_projectile_up_right;
                break;
            case UP_RIGHT2:
                projectileImage = Assets.shotgun_projectile_up_right;
                break;
            case UP_RIGHT3:
                projectileImage = Assets.shotgun_projectile_up_right;
                break;
            case DOWN_LEFT:
                projectileImage = Assets.shotgun_projectile_down_left;
                break;
            case DOWN_LEFT2:
                projectileImage = Assets.shotgun_projectile_down_left;
                break;
            case DOWN_LEFT3:
                projectileImage = Assets.shotgun_projectile_down_left;
                break;
            case DOWN_RIGHT:
                projectileImage = Assets.shotgun_projectile_down_right;
                break;
            case DOWN_RIGHT2:
                projectileImage = Assets.shotgun_projectile_down_right;
                break;
            case DOWN_RIGHT3:
                projectileImage = Assets.shotgun_projectile_down_right;
                break;
        }

        bounds.x = 27;
        bounds.y = 25;
        bounds.width = 10;
        bounds.height = 12;
    }

    @Override
    public void tick() {
        float diagonalSpeed = (float) (Math.sqrt((double)((speed*speed)/2)));
        switch (direction){
            case UP:
                yMove = -speed;
                break;
            case UP2:
                xMove = -(speed/6);
                yMove = -speed;
                break;
            case UP3:
                xMove = speed/6;
                yMove = -speed;
                break;
            case DOWN:
                yMove = speed;
                break;
            case DOWN2:
                yMove = speed;
                xMove = -(speed/6);
                break;
            case DOWN3:
                yMove = speed;
                xMove = speed/6;
                break;
            case LEFT:
                xMove = -speed;
                break;
            case LEFT2:
                xMove = -speed;
                yMove = -(speed/6);
                break;
            case LEFT3:
                xMove = -speed;
                yMove = speed/6;
                break;
            case RIGHT:
                xMove = speed;
                break;
            case RIGHT2:
                xMove = speed;
                yMove = -(speed/6);
                break;
            case RIGHT3:
                xMove = speed;
                yMove = speed/6;
                break;
            case UP_LEFT:
                yMove = -diagonalSpeed;
                xMove = -diagonalSpeed;
                break;
            case UP_LEFT2:
                yMove = -diagonalSpeed - diagonalSpeed*1/6;
                xMove = -diagonalSpeed * 5/6;
                break;
            case UP_LEFT3:
                yMove = -diagonalSpeed * 5/6;
                xMove = -diagonalSpeed - diagonalSpeed*1/6;
                break;
            case UP_RIGHT:
                yMove = -diagonalSpeed;
                xMove = diagonalSpeed;
                break;
            case UP_RIGHT2:
                yMove = -diagonalSpeed - diagonalSpeed*1/6;
                xMove = diagonalSpeed * 5/6;
                break;
            case UP_RIGHT3:
                yMove = -diagonalSpeed * 5/6;
                xMove = diagonalSpeed + diagonalSpeed*1/6;
                break;
            case DOWN_LEFT:
                yMove = diagonalSpeed;
                xMove = -diagonalSpeed;
                break;
            case DOWN_LEFT2:
                yMove = diagonalSpeed + diagonalSpeed*1/6;
                xMove = -diagonalSpeed * 5/6;
                break;
            case DOWN_LEFT3:
                yMove = diagonalSpeed * 5/6;
                xMove = -diagonalSpeed - diagonalSpeed*1/6;
                break;
            case DOWN_RIGHT:
                yMove = diagonalSpeed;
                xMove = diagonalSpeed;
                break;
            case DOWN_RIGHT2:
                yMove = diagonalSpeed + diagonalSpeed*1/6;
                xMove = diagonalSpeed * 5/6;
                break;
            case DOWN_RIGHT3:
                yMove = diagonalSpeed * 5/6;
                xMove = diagonalSpeed + diagonalSpeed*1/6;
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
}
