package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
    public static BufferedImage loadImage(String pPath){
        try {
            return ImageIO.read(ImageLoader.class.getResource(pPath));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
