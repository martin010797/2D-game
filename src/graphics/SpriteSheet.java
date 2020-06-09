package graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage sheet;

    public SpriteSheet(BufferedImage pSheet){
        sheet = pSheet;
    }

    //you can cropp any part of sheet
    public BufferedImage crop(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }
}
