package statics;

import game.Handler;

public abstract class Boost extends StaticEntity {

    public static int DURATION_ON_SCREEN = 15000;

    protected boolean destroyed = false;
    private long timer = 0;
    private long lastTime = System.currentTimeMillis();

    public Boost(Handler pHandler, float x, float y, int pWidth, int pHeight) {
        super(pHandler, x, y, pWidth, pHeight);
    }

    @Override
    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (timer >= DURATION_ON_SCREEN){
            destroyed = true;
        }
    }

    public void destroy(){
        destroyed = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }
}
