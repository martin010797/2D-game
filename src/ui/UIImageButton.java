package ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIImageButton extends UIObject {

    private BufferedImage[] images;
    private ClickListener clicker;


    public UIImageButton(float pX, float pY, int pWidth, int pHeight, BufferedImage[] pImages, ClickListener pClicker) {
        super(pX, pY, pWidth, pHeight);
        images = pImages;
        clicker = pClicker;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        if (hovering)
            g.drawImage(images[1], (int) x, (int) y, width, height, null);
        else
            g.drawImage(images[0], (int) x, (int) y, width, height, null);
    }

    @Override
    public void onClick() {
        clicker.onClick();
    }
}
