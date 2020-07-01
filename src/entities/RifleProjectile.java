package entities;

import creatures.Direction;
import creatures.Player;
import game.Handler;
import graphics.Assets;

import java.awt.*;

public class RifleProjectile extends Projectile {
    public static final int PRICE = 10;
    public static final int MAX_NUMBER_OF_PROJECTILES = 150;
    private static final float DEFAULT_SPEED = 14.0f;

    public RifleProjectile(Handler pHandler, float x, float y, int pWidth, int pHeight, Direction pDirection, Player pPlayer) {
        super(pHandler, x, y, pWidth, pHeight, pDirection, pPlayer);
        speed = DEFAULT_SPEED;


        switch (direction){
            case UP:
                projectileImage = Assets.rifle_projectile_up;
                break;
            case DOWN:
                projectileImage = Assets.rifle_projectile_down;
                break;
            case LEFT:
                projectileImage = Assets.rifle_projectile_left;
                break;
            case RIGHT:
                projectileImage = Assets.rifle_projectile_right;
                break;
            case UP_LEFT:
                projectileImage = Assets.rifle_projectile_up_left;
                break;
            case UP_RIGHT:
                projectileImage = Assets.rifle_projectile_up_right;
                break;
            case DOWN_LEFT:
                projectileImage = Assets.rifle_projectile_down_left;
                break;
            case DOWN_RIGHT:
                projectileImage = Assets.rifle_projectile_down_right;
                break;
        }

        bounds.x = 27;
        bounds.y = 27;
        bounds.width = 15;
        bounds.height = 10;
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
        //g.setColor(Color.red);
        //g.fillRect((int) (x + bounds.x), (int) (y + bounds.y), bounds.width, bounds.height);
        g.drawImage(projectileImage,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }
}
