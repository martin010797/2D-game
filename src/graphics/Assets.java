package graphics;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;



public class Assets {

    private static final int width = 32;
    private static final int height = 32;
    public static BufferedImage enemy, water, grass, nothing, rock, tree, world1Texture, spawner, coin;
    public static BufferedImage[] player_down, player_up, player_left, player_right;
    public static BufferedImage[] player_down_left, player_down_right, player_up_left, player_up_right;
    public static BufferedImage static_player_down, static_player_up, static_player_left, static_player_right;
    public static BufferedImage static_player_down_left, static_player_down_right, static_player_up_left, static_player_up_right;
    public static BufferedImage projectile_left, projectile_right, projectile_up, projectile_down,
            projectile_up_right, projectile_up_left, projectile_down_right, projectile_down_left;
    public static BufferedImage rifle_projectile_left, rifle_projectile_right, rifle_projectile_up, rifle_projectile_down,
            rifle_projectile_up_right, rifle_projectile_up_left, rifle_projectile_down_right, rifle_projectile_down_left;
    public static BufferedImage[] btn_start;
    public static BufferedImage[] spawner_door;
    public static BufferedImage[] enemy_down, enemy_up, enemy_right, enemy_left, enemy_down_left, enemy_down_right,
            enemy_up_left, enemy_up_right;


    public static void  init(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/image.png"));
        SpriteSheet sheet2 = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
        SpriteSheet sheet3 = new SpriteSheet(ImageLoader.loadImage("/textures/sheet2.png"));
        SpriteSheet sheet4 = new SpriteSheet(ImageLoader.loadImage("/textures/testwick.png"));

        //size of animation, now its 2
        player_down = new BufferedImage[2];
        player_up = new BufferedImage[2];
        player_left = new BufferedImage[2];
        player_right = new BufferedImage[2];
        player_down_left = new BufferedImage[2];
        player_down_right = new BufferedImage[2];
        player_up_left = new BufferedImage[2];
        player_up_right = new BufferedImage[2];

        spawner_door = new BufferedImage[5];

        enemy_down = new BufferedImage[2];
        enemy_up = new BufferedImage[2];
        enemy_left = new BufferedImage[2];
        enemy_right = new BufferedImage[2];
        enemy_down_left = new BufferedImage[2];
        enemy_down_right = new BufferedImage[2];
        enemy_up_left = new BufferedImage[2];
        enemy_up_right = new BufferedImage[2];

        //menu
        btn_start = new BufferedImage[2];
        btn_start[0] = sheet3.crop(width * 6, height * 4, width * 2,height);
        btn_start[1] = sheet3.crop(width * 6, height * 5, width * 2, height);

        //player
        static_player_down = sheet4.crop(0,0,width * 2, height * 2);
        player_down[0] = sheet4.crop(64, 0, width * 2, height * 2);
        player_down[1] = sheet4.crop(128, 0, width * 2, height * 2);
        static_player_up = sheet4.crop(256, 0, width * 2, height * 2);
        player_up[0] = sheet4.crop(192, 0, width * 2, height * 2);
        player_up[1] = sheet4.crop(320, 0, width * 2, height * 2);
        static_player_right = sheet4.crop(6 * width * 2, 0, width * 2, height * 2);
        player_right[0] = sheet4.crop(448, 0, width * 2, height * 2);
        player_right[1] = sheet4.crop(512, 0, width * 2, height * 2);
        static_player_left = sheet4.crop(9 * width * 2, 0, width * 2, height * 2);
        player_left[0] = sheet4.crop(0, height * 2, width * 2, height * 2);
        player_left[1] = sheet4.crop(width * 2, height * 2, width * 2, height * 2);
        //moving diagonally
        player_up_right[0] = sheet4.crop(3 * width * 2, height * 2, width * 2, height * 2);
        player_up_right[1] = sheet4.crop(4 * width * 2, height * 2, width * 2, height * 2);
        static_player_up_right = sheet4.crop(2 * width * 2, height * 2, width * 2, height * 2);
        player_up_left[0] = sheet4.crop(6 * width * 2, height * 2, width * 2, height * 2);
        player_up_left[1] = sheet4.crop(7 * width * 2, height * 2, width * 2, height * 2);
        static_player_up_left = sheet4.crop(5 * width * 2, height * 2, width * 2, height * 2);
        player_down_right[0] = sheet4.crop(5 * width * 2, 3 * height * 2, width * 2, height * 2);
        player_down_right[1] = sheet4.crop(6 * width * 2, 3 * height * 2, width * 2, height * 2);
        static_player_down_right = sheet4.crop( 4 * width * 2, 3 *height * 2, width * 2, height * 2);
        player_down_left[0] = sheet4.crop(2 * width * 2, 3 * height * 2, width * 2, height * 2);
        player_down_left[1] = sheet4.crop(3 * width * 2, 3 * height * 2, width * 2, height * 2);
        static_player_down_left = sheet4.crop(width * 2, 3 * height * 2, width * 2, height * 2);

        //enemy
        enemy_down[0] = sheet4.crop(8 * width * 2, 4 * height * 2, width * 2, height * 2);
        enemy_down[1] = sheet4.crop(9 * width * 2, 4 * height * 2, width * 2, height * 2);
        enemy_up[0] = sheet4.crop(width * 2, 5 * height * 2, width * 2, height * 2);
        enemy_up[1] = sheet4.crop(2 * width * 2, 5 * height * 2, width * 2, height * 2);
        enemy_right[0] = sheet4.crop(4 * width * 2, 5 * height * 2, width * 2, height * 2);
        enemy_right[1] = sheet4.crop(5 * width * 2, 5 * height * 2, width * 2, height * 2);
        enemy_left[0] = sheet4.crop(7 * width * 2, 5 * height * 2, width * 2, height * 2);
        enemy_left[1] = sheet4.crop(8 * width * 2, 5 * height * 2, width * 2, height * 2);
        enemy_up_right[0] = sheet4.crop(0, 6 * height * 2, width * 2, height * 2);
        enemy_up_right[1] = sheet4.crop(width * 2, 6 * height * 2, width * 2, height * 2);
        enemy_up_left[0] = sheet4.crop(3 *width * 2, 6 * height * 2, width * 2, height * 2);
        enemy_up_left[1] = sheet4.crop(4 * width * 2, 6 * height * 2, width * 2, height * 2);
        enemy_down_left[0] = sheet4.crop(6 *width * 2, 6 * height * 2, width * 2, height * 2);
        enemy_down_left[1] = sheet4.crop(7 *width * 2, 6 * height * 2, width * 2, height * 2);
        enemy_down_right[0] = sheet4.crop(8 *width * 2, 6 * height * 2, width * 2, height * 2);
        enemy_down_right[1] = sheet4.crop(9 *width * 2, 6 * height * 2, width * 2, height * 2);

        //default projectile
        projectile_left = sheet4.crop(3 *width * 2, 2 * height * 2, width * 2, height * 2);
        projectile_up = sheet4.crop(4 * width * 2, 2 * height * 2, width * 2, height * 2);
        projectile_right = sheet4.crop(5 * width * 2, 2 * height * 2, width * 2, height * 2);
        projectile_down = sheet4.crop(6 * width * 2, 2 * height * 2, width * 2, height * 2);
        projectile_up_left = sheet4.crop(7 * width * 2, 3 * height * 2, width * 2, height * 2);
        projectile_up_right = sheet4.crop(8 * width * 2, 3 * height * 2, width * 2, height * 2);
        projectile_down_left = sheet4.crop(9 * width * 2, 3 * height * 2, width * 2, height * 2);
        projectile_down_right = sheet4.crop(0, 4 * height * 2, width * 2, height * 2);

        //rifle projectile
        rifle_projectile_left = sheet4.crop(0, 7 * height * 2, width * 2, height * 2);
        rifle_projectile_up = sheet4.crop(width * 2, 7 * height * 2, width * 2, height * 2);
        rifle_projectile_right = sheet4.crop(2 * width * 2, 7 * height * 2, width * 2, height * 2);
        rifle_projectile_down = sheet4.crop(3 * width * 2, 7 * height * 2, width * 2, height * 2);
        rifle_projectile_up_left = sheet4.crop(4 * width * 2, 7 * height * 2, width * 2, height * 2);
        rifle_projectile_up_right = sheet4.crop(5 * width * 2, 7 * height * 2, width * 2, height * 2);
        rifle_projectile_down_right = sheet4.crop(6 * width * 2, 7 * height * 2, width * 2, height * 2);
        rifle_projectile_down_left = sheet4.crop(7 * width * 2, 7 * height * 2, width * 2, height * 2);

        //spawners
        spawner = sheet4.crop(9 * width * 2, 64, width * 2, height * 2);
        spawner_door[0] = sheet4.crop(0, 2 * height * 2, width * 2, height * 2);
        spawner_door[1] = sheet4.crop(width * 2, 2 * height * 2, width * 2, height * 2);
        spawner_door[2] = sheet4.crop(2 * width * 2, 2 * height * 2, width * 2, height * 2);
        spawner_door[3] = sheet4.crop(width * 2, 2 * height * 2, width * 2, height * 2);
        spawner_door[4] = sheet4.crop(0, 2 * height * 2, width * 2, height * 2);

        //others
        enemy = sheet4.crop(0,3 * width * 2, width * 2, height * 2);
        water = sheet.crop(32,0, width, height);
        grass = sheet.crop(0,32, width, height);
        rock = sheet.crop(64,0, width, height);
        nothing = sheet.crop(64, 32, width, height);
        tree = sheet2.crop(0, 0, width, height);
        world1Texture = sheet4.crop(width * 2, 4 * height * 2, width * 2, height * 2);
        coin = sheet4.crop(8 * width * 2, 7 * height * 2, width * 2, height * 2);
    }
}
