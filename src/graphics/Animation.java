package graphics;

import java.awt.image.BufferedImage;

public class Animation {

    private int speed;

    private int index;
    private BufferedImage[] frames;
    private long lastTime, timer;

    public Animation(int pSpeed, BufferedImage[] pFrames){
        speed = pSpeed;
        frames = pFrames;
        index = 0;
        //System.currentTimeMillis give us time from beginning of the game
        lastTime = System.currentTimeMillis();
        timer = 0;
    }

    public BufferedImage getCurrentFrame(){
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
            if (index >= frames.length)
                index = 0;
        }
    }
}
