package entities;

import creatures.Direction;
import creatures.Player;
import game.Handler;
import graphics.Assets;

import java.awt.*;

public class DefaultProjectile extends Projectile{
    private static final float DEFAULT_SPEED = 6.0f;

    public DefaultProjectile(Handler pHandler, float x, float y, int pWidth, int pHeight, Direction pDirection, Player pPlayer) {
        super(pHandler, x, y, pWidth, pHeight, pDirection, pPlayer);
        speed = DEFAULT_SPEED;

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

        bounds.x = 27;
        bounds.y = 27;
        bounds.width = 10;
        bounds.height = 10;
    }

    @Override
    public void tick() {
        switch (direction){
            case UP:
                //projectileImage = Assets.projectile_up;
                yMove = -speed;
                break;
            case DOWN:
                //projectileImage = Assets.projectile_down;
                yMove = speed;
                break;
            case LEFT:
                //projectileImage = Assets.projectile_left;
                xMove = -speed;
                break;
            case RIGHT:
                //projectileImage = Assets.projectile_right;
                xMove = speed;
                break;
            case UP_LEFT:
                //projectileImage = Assets.projectile_up_left;
                yMove = -speed;
                xMove = -speed;
                break;
            case UP_RIGHT:
                //projectileImage = Assets.projectile_up_right;
                yMove = -speed;
                xMove = speed;
                break;
            case DOWN_LEFT:
                //projectileImage = Assets.projectile_down_left;
                yMove = speed;
                xMove = -speed;
                break;
            case DOWN_RIGHT:
                //projectileImage = Assets.projectile_down_right;
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
}
