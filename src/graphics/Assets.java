package graphics;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;



public class Assets {

    private static final int width = 64;
    private static final int height = 64;
    public static BufferedImage enemy, nothing, world1Texture, spawner, coin, player_stats_background,
            bubble_background1, bubble_background2, store_background;
    public static BufferedImage static_player_down, static_player_up, static_player_left, static_player_right;
    public static BufferedImage static_player_down_left, static_player_down_right, static_player_up_left, static_player_up_right;
    public static BufferedImage projectile_left, projectile_right, projectile_up, projectile_down,
            projectile_up_right, projectile_up_left, projectile_down_right, projectile_down_left;
    public static BufferedImage rifle_projectile_left, rifle_projectile_right, rifle_projectile_up, rifle_projectile_down,
            rifle_projectile_up_right, rifle_projectile_up_left, rifle_projectile_down_right, rifle_projectile_down_left;
    public static BufferedImage rpg_projectile_left, rpg_projectile_right, rpg_projectile_up, rpg_projectile_down,
            rpg_projectile_up_right, rpg_projectile_up_left, rpg_projectile_down_right, rpg_projectile_down_left;
    public static BufferedImage shotgun_projectile_left, shotgun_projectile_right, shotgun_projectile_up, shotgun_projectile_down,
            shotgun_projectile_up_right, shotgun_projectile_up_left, shotgun_projectile_down_right, shotgun_projectile_down_left,
            shotgun_image;
    public static BufferedImage one, two, three, four, five, six,seven, eight, nine, zero;
    public static BufferedImage store_rpg_green, store_rpg_red, store_rifle_green, store_rifle_red, store_shotgun_green,
            store_shotgun_red;
    public static BufferedImage banner_not_enough_money, immortalBubble, player_ability, loading_bar_green_full,no_active_boost,
            speed_boost, double_coins_boost, immortality_boost;
    //arrays
    public static BufferedImage[] speed_boost_array, double_coins_boost_array, immortality_boost_array;
    public static BufferedImage[] btn_start;
    public static BufferedImage[] spawner_door;
    public static BufferedImage[] enemy_down, enemy_up, enemy_right, enemy_left, enemy_down_left, enemy_down_right,
            enemy_up_left, enemy_up_right;
    public static BufferedImage[] loadingBarArray;
    public static BufferedImage[] coin_animation;
    public static BufferedImage[] player_down, player_up, player_left, player_right;
    public static BufferedImage[] player_down_left, player_down_right, player_up_left, player_up_right;
    public static BufferedImage[] respawn_animation;



    public static void  init(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/testwick.png"));
        SpriteSheet sheet3 = new SpriteSheet(ImageLoader.loadImage("/textures/sheet2.png"));


        //player
        player_down = new BufferedImage[2];
        player_up = new BufferedImage[2];
        player_left = new BufferedImage[2];
        player_right = new BufferedImage[2];
        player_down_left = new BufferedImage[2];
        player_down_right = new BufferedImage[2];
        player_up_left = new BufferedImage[2];
        player_up_right = new BufferedImage[2];

        spawner_door = new BufferedImage[5];
        coin_animation = new BufferedImage[12];
        loadingBarArray = new BufferedImage[32];
        respawn_animation = new BufferedImage[6];

        //enemy
        enemy_down = new BufferedImage[2];
        enemy_up = new BufferedImage[2];
        enemy_left = new BufferedImage[2];
        enemy_right = new BufferedImage[2];
        enemy_down_left = new BufferedImage[2];
        enemy_down_right = new BufferedImage[2];
        enemy_up_left = new BufferedImage[2];
        enemy_up_right = new BufferedImage[2];

        //boosts
        double_coins_boost_array = new BufferedImage[2];
        immortality_boost_array = new BufferedImage[2];
        speed_boost_array = new BufferedImage[2];

        //menu
        btn_start = new BufferedImage[2];
        btn_start[0] = sheet3.crop((int) (width / 2) * 6, (int) (height / 2) * 4, width,(int) (height / 2));
        btn_start[1] = sheet3.crop((int) (width / 2) * 6, (int) (height / 2) * 5, width, (int) (height / 2));

        //player
        static_player_down = sheet.crop(0,0,width, height);
        player_down[0] = sheet.crop(64, 0, width, height);
        player_down[1] = sheet.crop(128, 0, width, height);
        static_player_up = sheet.crop(256, 0, width, height);
        player_up[0] = sheet.crop(192, 0, width, height);
        player_up[1] = sheet.crop(320, 0, width, height);
        static_player_right = sheet.crop(6 * width, 0, width, height);
        player_right[0] = sheet.crop(448, 0, width, height);
        player_right[1] = sheet.crop(512, 0, width, height);
        static_player_left = sheet.crop(9 * width, 0, width, height);
        player_left[0] = sheet.crop(0, height, width, height);
        player_left[1] = sheet.crop(width, height, width, height);
        //moving diagonally
        player_up_right[0] = sheet.crop(3 * width, height, width, height);
        player_up_right[1] = sheet.crop(4 * width, height, width, height);
        static_player_up_right = sheet.crop(2 * width, height, width, height);
        player_up_left[0] = sheet.crop(6 * width, height, width, height);
        player_up_left[1] = sheet.crop(7 * width, height, width, height);
        static_player_up_left = sheet.crop(5 * width, height, width, height);
        player_down_right[0] = sheet.crop(5 * width, 3 * height, width, height);
        player_down_right[1] = sheet.crop(6 * width, 3 * height, width, height);
        static_player_down_right = sheet.crop( 4 * width, 3 *height, width, height);
        player_down_left[0] = sheet.crop(2 * width, 3 * height, width, height);
        player_down_left[1] = sheet.crop(3 * width, 3 * height, width, height);
        static_player_down_left = sheet.crop(width, 3 * height, width, height);

        //enemy
        enemy_down[0] = sheet.crop(8 * width, 4 * height, width, height);
        enemy_down[1] = sheet.crop(9 * width, 4 * height, width, height);
        enemy_up[0] = sheet.crop(width, 5 * height, width, height);
        enemy_up[1] = sheet.crop(2 * width, 5 * height, width, height);
        enemy_right[0] = sheet.crop(4 * width, 5 * height, width, height);
        enemy_right[1] = sheet.crop(5 * width, 5 * height, width, height);
        enemy_left[0] = sheet.crop(7 * width, 5 * height, width, height);
        enemy_left[1] = sheet.crop(8 * width, 5 * height, width, height);
        enemy_up_right[0] = sheet.crop(0, 6 * height, width, height);
        enemy_up_right[1] = sheet.crop(width, 6 * height, width, height);
        enemy_up_left[0] = sheet.crop(3 *width, 6 * height, width, height);
        enemy_up_left[1] = sheet.crop(4 * width, 6 * height, width, height);
        enemy_down_left[0] = sheet.crop(6 *width, 6 * height, width, height);
        enemy_down_left[1] = sheet.crop(7 *width, 6 * height, width, height);
        enemy_down_right[0] = sheet.crop(8 *width, 6 * height, width, height);
        enemy_down_right[1] = sheet.crop(9 *width, 6 * height, width, height);

        //default projectile
        projectile_left = sheet.crop(3 *width, 2 * height, width, height);
        projectile_up = sheet.crop(4 * width, 2 * height, width, height);
        projectile_right = sheet.crop(5 * width, 2 * height, width, height);
        projectile_down = sheet.crop(6 * width, 2 * height, width, height);
        projectile_up_left = sheet.crop(7 * width, 3 * height, width, height);
        projectile_up_right = sheet.crop(8 * width, 3 * height, width, height);
        projectile_down_left = sheet.crop(9 * width, 3 * height, width, height);
        projectile_down_right = sheet.crop(0, 4 * height, width, height);

        //rifle projectile
        rifle_projectile_left = sheet.crop(0, 7 * height, width, height);
        rifle_projectile_up = sheet.crop(width, 7 * height, width, height);
        rifle_projectile_right = sheet.crop(2 * width, 7 * height, width, height);
        rifle_projectile_down = sheet.crop(3 * width, 7 * height, width, height);
        rifle_projectile_up_left = sheet.crop(4 * width, 7 * height, width, height);
        rifle_projectile_up_right = sheet.crop(5 * width, 7 * height, width, height);
        rifle_projectile_down_right = sheet.crop(6 * width, 7 * height, width, height);
        rifle_projectile_down_left = sheet.crop(7 * width, 7 * height, width, height);

        //rpg projectile
        rpg_projectile_left = sheet.crop(6 * width, 8 * height, width, height);
        rpg_projectile_up = sheet.crop(7 * width, 8 * height, width, height);
        rpg_projectile_right = sheet.crop(8 * width, 8 * height, width, height);
        rpg_projectile_down = sheet.crop(9 * width, 8 * height, width, height);
        rpg_projectile_up_left = sheet.crop(0, 9 * height, width, height);
        rpg_projectile_up_right = sheet.crop(width, 9 * height, width, height);
        rpg_projectile_down_right = sheet.crop(2 * width, 9 * height, width, height);
        rpg_projectile_down_left = sheet.crop(3 * width, 9 * height, width, height);

        //shotgun projectile
        shotgun_projectile_right = sheet.crop(3 * width, 16 * height, width, height);
        shotgun_projectile_left = sheet.crop(4 * width, 16 * height, width, height);
        shotgun_projectile_up = sheet.crop(5 * width, 16 * height, width, height);
        shotgun_projectile_down = sheet.crop(6 * width, 16 * height, width, height);
        shotgun_projectile_down_right = sheet.crop(7 * width, 16 * height, width, height);
        shotgun_projectile_up_left = sheet.crop(8 * width, 16 * height, width, height);
        shotgun_projectile_up_right = sheet.crop(9 * width, 16 * height, width, height);
        shotgun_projectile_down_left = sheet.crop(9 * width, 15 * height, width, height);
        shotgun_image = sheet.crop(5 * width, 17 * height, width, height);

        //spawners
        spawner = sheet.crop(9 * width, 64, width, height);
        spawner_door[0] = sheet.crop(0, 2 * height, width, height);
        spawner_door[1] = sheet.crop(width, 2 * height, width, height);
        spawner_door[2] = sheet.crop(2 * width, 2 * height, width, height);
        spawner_door[3] = sheet.crop(width, 2 * height, width, height);
        spawner_door[4] = sheet.crop(0, 2 * height, width, height);

        //coin
        coin = sheet.crop(8 * width, 7 * height, width, height);
        coin_animation[0] = sheet.crop(8 * width, 7 * height, width, height);
        coin_animation[1] = sheet.crop(0, 8 * height, width, height);
        coin_animation[2] = sheet.crop(width, 8 * height, width, height);
        coin_animation[3] = sheet.crop(2 * width, 8 * height, width, height);
        coin_animation[4] = sheet.crop(3 * width, 8 * height, width, height);
        coin_animation[5] = sheet.crop(4 * width, 8 * height, width, height);
        coin_animation[6] = sheet.crop(5 * width, 8 * height, width, height);
        coin_animation[7] = sheet.crop(4 * width, 8 * height, width, height);
        coin_animation[8] = sheet.crop(3 * width, 8 * height, width, height);
        coin_animation[9] = sheet.crop(2 * width, 8 * height, width, height);
        coin_animation[10] = sheet.crop(width, 8 * height, width, height);
        coin_animation[11] = sheet.crop(0, 8 * height, width, height);

        //numbers
        zero = sheet.crop(4 * width, 9 * height, width, height);
        one = sheet.crop(5 * width, 9 * height, width, height);
        two = sheet.crop(6 * width, 9 * height, width, height);
        three = sheet.crop(7 * width, 9 * height, width, height);
        four = sheet.crop(8 * width, 9 * height, width, height);
        five = sheet.crop(9 * width, 9 * height, width, height);
        six = sheet.crop(0, 10 * height, width, height);
        seven = sheet.crop(width, 10 * height, width, height);
        eight = sheet.crop(2 * width, 10 * height, width, height);
        nine = sheet.crop(3 * width, 10 * height, width, height);

        //loading bar
        loadingBarArray[0] = sheet.crop(6 * width, 10 * height, width, height);
        loadingBarArray[1] = sheet.crop(7 * width, 10 * height, width, height);
        loadingBarArray[2] = sheet.crop(8 * width, 10 * height, width, height);
        loadingBarArray[3] = sheet.crop(9 * width, 10 * height, width, height);
        loadingBarArray[4] = sheet.crop(0, 11 * height, width, height);
        loadingBarArray[5] = sheet.crop(width, 11 * height, width, height);
        loadingBarArray[6] = sheet.crop(2 * width, 11 * height, width, height);
        loadingBarArray[7] = sheet.crop(3 * width, 11 * height, width, height);
        loadingBarArray[8] = sheet.crop(4 * width, 11 * height, width, height);
        loadingBarArray[9] = sheet.crop(5 * width, 11 * height, width, height);
        loadingBarArray[10] = sheet.crop(6 * width, 11 * height, width, height);
        loadingBarArray[11] = sheet.crop(7 * width, 11 * height, width, height);
        loadingBarArray[12] = sheet.crop(8 * width, 11 * height, width, height);
        loadingBarArray[13] = sheet.crop(9 * width, 11 * height, width, height);
        loadingBarArray[14] = sheet.crop(0, 12 * height, width, height);
        loadingBarArray[15] = sheet.crop(width, 12 * height, width, height);
        loadingBarArray[16] = sheet.crop(2 * width, 12 * height, width, height);
        loadingBarArray[17] = sheet.crop(3 * width, 12 * height, width, height);
        loadingBarArray[18] = sheet.crop(4 * width, 12 * height, width, height);
        loadingBarArray[19] = sheet.crop(5 * width, 12 * height, width, height);
        loadingBarArray[20] = sheet.crop(6 * width, 12 * height, width, height);
        loadingBarArray[21] = sheet.crop(7 * width, 12 * height, width, height);
        loadingBarArray[22] = sheet.crop(8 * width, 12 * height, width, height);
        loadingBarArray[23] = sheet.crop(9 * width, 12 * height, width, height);
        loadingBarArray[24] = sheet.crop(0, 13 * height, width, height);
        loadingBarArray[25] = sheet.crop(width, 13 * height, width, height);
        loadingBarArray[26] = sheet.crop(2 * width, 13 * height, width, height);
        loadingBarArray[27] = sheet.crop(3 * width, 13 * height, width, height);
        loadingBarArray[28] = sheet.crop(4 * width, 13 * height, width, height);
        loadingBarArray[29] = sheet.crop(5 * width, 13 * height, width, height);
        loadingBarArray[30] = sheet.crop(6 * width, 13 * height, width, height);
        loadingBarArray[31] = sheet.crop(7 * width, 13 * height, width, height);

        //respawn animation
        respawn_animation[0] = sheet.crop(5 * width, 18 * height, width, height);
        respawn_animation[1] = sheet.crop(4 * width, 18 * height, width, height);
        respawn_animation[2] = sheet.crop(3 * width, 18 * height, width, height);
        respawn_animation[3] = sheet.crop(2 * width, 18 * height, width, height);
        respawn_animation[4] = sheet.crop(width, 18 * height, width, height);
        respawn_animation[5] = sheet.crop(0, 18 * height, width, height);

        //boosts
        no_active_boost = sheet.crop(6 * width, 20 * height, width, height);
        speed_boost = sheet.crop(7 * width, 20 * height, width, height);
        immortality_boost = sheet.crop(8 * width, 20 * height, width, height);
        double_coins_boost = sheet.crop(9 * width, 20 * height, width, height);
        speed_boost_array[0] = sheet.crop(0, 20 * height, width, height);
        speed_boost_array[1] = sheet.crop(width, 20 * height, width, height);
        immortality_boost_array[0] = sheet.crop(2 * width, 20 * height, width, height);
        immortality_boost_array[1] = sheet.crop(3 * width, 20 * height, width, height);
        double_coins_boost_array[0] = sheet.crop(4 * width, 20 * height, width, height);
        double_coins_boost_array[1] = sheet.crop(5 * width, 20 * height, width, height);

        //others
        enemy = sheet.crop(0,3 * width, width, height);
        nothing = sheet.crop(9 * width, 7 * height, width, height);
        world1Texture = sheet.crop(width, 4 * height, width, height);
        player_stats_background = sheet.crop(5 * width, 10 * height, width, height);
        store_background = sheet.crop(4 * width, 10 * height, width, height);
        bubble_background1 = sheet.crop(8 * width, 13 * height, width, height);
        bubble_background2 = sheet.crop(9 * width, 13 * height, width, height);
        store_rpg_green = sheet.crop(0, 14 * height, width * 2, height);
        store_rpg_red = sheet.crop(2 * width, 14 * height, width * 2, height);
        store_rifle_green = sheet.crop(4 * width, 14 * height, width * 2, height);
        store_rifle_red = sheet.crop(6 * width, 14 * height, width * 2, height);
        store_shotgun_green = sheet.crop(0, 17 * height, width * 2, height);
        store_shotgun_red = sheet.crop(2 * width, 17 * height, width * 2, height);
        banner_not_enough_money = sheet.crop(0, 19 * height, width * 10, height);
        immortalBubble = sheet.crop(6 * width, 18 * height, width, height);
        player_ability = sheet.crop(7 * width, 18 * height, width, height);
        loading_bar_green_full = sheet.crop(8 * width, 18 * height, width, height);
    }
}
