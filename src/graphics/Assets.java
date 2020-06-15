package graphics;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;



public class Assets {

    private static final int width = 32;
    private static final int height = 32;
    public static BufferedImage enemy, water, grass, nothing, rock, tree, world1Texture, spawner;
    public static BufferedImage[] player_down, player_up, player_left, player_right;
    public static BufferedImage[] btn_start;


    public static void  init(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/image.png"));
        SpriteSheet sheet2 = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
        SpriteSheet sheet3 = new SpriteSheet(ImageLoader.loadImage("/textures/sheet2.png"));
        SpriteSheet sheet4 = new SpriteSheet(ImageLoader.loadImage("/textures/testwick3.png"));

        //size of animation, now its 2
        player_down = new BufferedImage[3];
        player_up = new BufferedImage[2];
        player_left = new BufferedImage[2];
        player_right = new BufferedImage[2];

        btn_start = new BufferedImage[2];
        btn_start[0] = sheet3.crop(width * 6, height * 4, width * 2,height);
        btn_start[1] = sheet3.crop(width * 6, height * 5, width * 2, height);

        //player_down[0] = sheet2.crop(128, 0, width, height);
        //player_down[1] = sheet2.crop(160, 0, width, height);
        player_down[0] = sheet4.crop(0, 0, width * 2, height * 2);
        player_down[1] = sheet4.crop(64, 0, width * 2, height * 2);
        player_down[2] = sheet4.crop(128, 0, width * 2, height * 2);
        //player_up[0] = sheet2.crop(192, 0, width, height);
        //player_up[1] = sheet2.crop(224, 0, width, height);
        player_up[0] = sheet4.crop(192, 0, width * 2, height * 2);
        player_up[1] = sheet4.crop(320, 0, width * 2, height * 2);
        //player_right[0] = sheet2.crop(128, 32, width, height);
        //player_right[1] = sheet2.crop(160, 32, width, height);
        player_right[0] = sheet4.crop(448, 0, width * 2, height * 2);
        player_right[1] = sheet4.crop(512, 0, width * 2, height * 2);
        //player_left[0] = sheet2.crop(192, 32, width, height);
        //player_left[1] = sheet2.crop(224, 32, width, height);
        player_left[0] = sheet4.crop(640, 0, width * 2, height * 2);
        //player_left[1] = sheet4.crop(704, 0, width * 2, height * 2);
        player_left[1] = sheet4.crop(0, 64, width * 2, height * 2);


        //depends on my sprite sheet
        //player = sheet2.crop(128, 0, width, height);
        enemy = sheet.crop(32,32,width, height);
        water = sheet.crop(32,0, width, height);
        grass = sheet.crop(0,32, width, height);
        rock = sheet.crop(64,0, width, height);
        nothing = sheet.crop(64, 32, width, height);
        tree = sheet2.crop(0, 0, width, height);
        world1Texture = sheet4.crop(7 * width * 2, 64, width * 2, height * 2);
        spawner = sheet4.crop(8 * width * 2, 64, width * 2, height * 2);
        //...
    }
}
