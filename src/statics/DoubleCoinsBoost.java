package statics;

import game.Handler;
import graphics.Animation;
import graphics.Assets;

import java.awt.*;

public class DoubleCoinsBoost extends Boost {

    public static final int BOOST_DURATION = 15000;
    private Animation animation;

    public DoubleCoinsBoost(Handler pHandler, float x, float y, int pWidth, int pHeight) {
        super(pHandler, x, y, pWidth, pHeight);
        animation = new Animation(125, Assets.double_coins_boost_array);
    }

    @Override
    public void tick() {
        super.tick();
        animation.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animation.getCurrentFrame(),(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }
}
