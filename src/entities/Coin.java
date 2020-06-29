package entities;

import game.Handler;
import graphics.Animation;
import graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Coin extends Entity {

    private static final int FIVESECONDS = 5000;
    private boolean destroyed = false;
    private BufferedImage coinImage;
    private long now;
    private long creation;
    private Animation animCoin;


    public Coin(Handler pHandler, float x, float y, int pWidth, int pHeight) {
        super(pHandler, x, y, pWidth, pHeight);

        coinImage = Assets.coin;
        creation = System.currentTimeMillis();

        bounds.x = 12;
        bounds.y = 12;
        bounds.width = 40;
        bounds.height = 38;

        animCoin = new Animation(125, Assets.coin_animation);
    }

    @Override
    public void tick() {
        animCoin.tick();
        now = System.currentTimeMillis();
        if ((now - creation) >= 5000)
            destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void render(Graphics g) {
        //g.setColor(Color.RED);
        //g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
                //(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
        //if (!destroyed)
            //g.drawImage(coinImage,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        if (!destroyed)
            g.drawImage(animCoin.getCurrentFrame(),(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }

    public void destroyCoin(){
        destroyed = true;
    }
}
