package creatures;
import entities.Entity;
import entities.Projectile;
import game.Handler;
import graphics.Animation;
import graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Creature {

    private static final int DEFAULT_DELTA_PROJECTILE = 250;

    private Animation animDown, animUp, animLeft, animRight, animDownLeft, animDownRight, animUpLeft, animUpRight;
    private Direction direction;
    private ArrayList<Projectile> projectiles;
    private long delta = 0;
    private long now;
    private long lastTime = System.currentTimeMillis();


    private boolean shooting = false;

    public Player(Handler pHandler, float x, float y) {
        super(pHandler, x, y,Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        direction = Direction.DOWN;

        bounds.x = 16;
        bounds.y = 30;
        bounds.width = 30;
        bounds.height = 34;

        //animations
        animDown = new Animation(175, Assets.player_down);
        animUp = new Animation(175, Assets.player_up);
        animLeft = new Animation(175, Assets.player_left);
        animRight = new Animation(175, Assets.player_right);
        animDownLeft = new Animation(175, Assets.player_down_left);
        animDownRight = new Animation(175, Assets.player_down_right);
        animUpLeft = new Animation(175, Assets.player_up_left);
        animUpRight = new Animation(175, Assets.player_up_right);

        projectiles = new ArrayList<Projectile>();
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

        //movement
        getInput();
        move();
        //if I want to have him in the center
        //handler.getGameCamera().centerOnEntity(this);
        for (Projectile p: projectiles) {
            p.tick();
        }
    }

    private void getInput(){
        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().up){
            yMove = -speed;
            direction = Direction.UP;
        }
        if (handler.getKeyManager().down){
            yMove = speed;
            direction = Direction.DOWN;
        }
        if (handler.getKeyManager().left) {
            xMove = -speed;
            direction = Direction.LEFT;
        }
        if (handler.getKeyManager().right){
            xMove = speed;
            direction = Direction.RIGHT;
        }

        if (handler.getKeyManager().up && handler.getKeyManager().left)
            direction = Direction.UP_LEFT;
        if (handler.getKeyManager().up && handler.getKeyManager().right)
            direction = Direction.UP_RIGHT;
        if (handler.getKeyManager().down && handler.getKeyManager().left)
            direction = Direction.DOWN_LEFT;
        if (handler.getKeyManager().down && handler.getKeyManager().right)
            direction = Direction.DOWN_RIGHT;

        //shooting
        now = System.currentTimeMillis();
        delta += now - lastTime;
        lastTime = now;
        //each second
        if (delta >= DEFAULT_DELTA_PROJECTILE || !shooting){
            if (handler.getKeyManager().space){
                shooting = true;
                float posX = x;
                float posY = y;
                switch (direction){
                    case UP:
                        posY = y - 30;
                        break;
                    case DOWN:
                        posY = y + 20;
                        break;
                    case LEFT:
                        posX = x - 22;
                        break;
                    case RIGHT:
                        posX = x + 22;
                        break;
                    case UP_LEFT:
                        posX = x - 22;
                        posY = y - 23;
                        break;
                    case UP_RIGHT:
                        posX = x + 27;
                        posY = y - 23;
                        break;
                    case DOWN_LEFT:
                        posX = x - 25;
                        posY = y + 18;
                        break;
                    case DOWN_RIGHT:
                        posX = x + 24;
                        posY = y + 18;
                        break;
                }
                Projectile newProjectile = new Projectile(handler, posX, posY, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, direction, this);
                projectiles.add(newProjectile);
                //System.out.println("shooting");
            }
            if (!handler.getKeyManager().space){
                shooting = false;
                //System.out.println("not shooting");
            }
            delta = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        //setting size just by adding to parametres
        //if I dont want player in the center
        //g.drawImage(Assets.player,(int) (x),(int)y, width, height, null);
        //collision box
        //if I want to see collison box
        //g.setColor(Color.red);

        //g.fillRect((int) (x + bounds.x), (int) (y + bounds.y), bounds.width, bounds.height);
        //if I want to have it in center
        g.drawImage(getCurrentAnimationFrame(),(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        Projectile destroyedProjectile = null;
        for (Projectile p: projectiles) {
            p.render(g);
            if (p.isDestroyed())
                destroyedProjectile = p;
        }
        if (destroyedProjectile != null)
            projectiles.remove(destroyedProjectile);
        //collision box
        //if I want to see collison box
        //g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
                //(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
    }

    private BufferedImage getCurrentAnimationFrame(){
        //if (xMove < 0 && yMove > 0)
          //  System.out.println("moving down left");
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
        }else {
            switch (direction){
                case UP:
                    return Assets.static_player_up;
                case DOWN:
                    return Assets.static_player_down;
                case LEFT:
                    return Assets.static_player_left;
                case RIGHT:
                    return Assets.static_player_right;
                case UP_LEFT:
                    return Assets.static_player_up_left;
                case UP_RIGHT:
                    return Assets.static_player_up_right;
                case DOWN_LEFT:
                    return Assets.static_player_down_left;
                case DOWN_RIGHT:
                    return Assets.static_player_down_right;
            }
        }
        //default value
        return Assets.player_down[0];
    }

}
