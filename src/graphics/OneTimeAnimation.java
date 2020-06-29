package graphics;

import java.awt.image.BufferedImage;

public class OneTimeAnimation {

    private int speed;

    private int index;
    private BufferedImage[] frames;

    private long lastTime, timer;

    public OneTimeAnimation(int pSpeed, BufferedImage[] pFrames){
        speed = pSpeed;
        frames = pFrames;
        index = 0;
        lastTime = System.currentTimeMillis();
        timer = 0;
    }

    public BufferedImage getCurrentFrame(){
        if (index >= frames.length)
            return null;
        return frames[index];
    }

    public int getCurrentIndex() {
        return index;
    }

    public void tick(){
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (timer > speed){
            index++;
            timer = 0;
        }
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
        index = 0;
        timer = 0;
    }

    public int getSpeed() {
        return speed;
    }
}
