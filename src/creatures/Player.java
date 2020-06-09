package creatures;
import game.Game;
import game.Handler;
import graphics.Animation;
import graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    private Animation animDown, animUp, animLeft, animRight;

    public Player(Handler pHandler, float x, float y) {
        super(pHandler, x, y,Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        bounds.x = 16;
        bounds.y = 30;
        bounds.width = 30;
        bounds.height = 34;

        //animations
        animDown = new Animation(175, Assets.player_down);
        animUp = new Animation(175, Assets.player_up);
        animLeft = new Animation(175, Assets.player_left);
        animRight = new Animation(175, Assets.player_right);
    }

    @Override
    public void tick() {
        //animations
        animDown.tick();
        animUp.tick();
        animRight.tick();
        animLeft.tick();

        //movement
        getInput();
        move();
        //if I want to have him in the center
        //handler.getGameCamera().centerOnEntity(this);
    }

    private void getInput(){
        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().up)
            yMove = -speed;
        if (handler.getKeyManager().down)
            yMove = speed;
        if (handler.getKeyManager().left)
            xMove = -speed;
        if (handler.getKeyManager().right)
            xMove = speed;
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
        //collision box
        //if I want to see collison box
        //g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
                //(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
    }

    private BufferedImage getCurrentAnimationFrame(){
        if (xMove < 0){//moving left
            return animLeft.getCurrentFrame();
        }else if (xMove > 0){//moving right
            return animRight.getCurrentFrame();
        }else if (yMove < 0){//moving up
            return animUp.getCurrentFrame();
        }else if (yMove > 0){//moving down
            return animDown.getCurrentFrame();
        }
        return Assets.player_down[0];
    }
}
