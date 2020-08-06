package worlds;

import entities.EntityManager;
import game.Handler;
import graphics.Animation;
import graphics.Assets;
import statics.Spawner;
import tiles.Tile;
import utils.Utils;

import java.awt.*;

public abstract class World {

    private static final int NUMBEROFENEMIES = 1, MAXNUMBEROFENEMIESONSCREEN = 20, BANNER_END_LEVEL_WIDTH = 600,
            BANNER_END_LEVEL_HEIGHT = 70;

    protected static final int NUMBEROFSPAWNERS = 4, ZERO = 0, NUMBER_OF_BOOSTS = 3;
    protected static final int FIRSTSPAWNER = 0, SECONDSPAWNER = 1, THIRDSPAWNER = 2, FOURTHSPAWNER = 3;
    protected static final int FIRST_BOOST = 0, SECOND_BOOST = 1, THIRD_BOOST = 2;
    public static final int XFIRSTSPAWNER = 192, YFIRSTSPAWNER = 0, XSECONDSPAWNER = 0, YSECONDSPAWNER = 511,
            XTHIRDSPAWNER = 1152, YTHIRDSPAWNER = 128, XFOURTHSPAWNER = 960, YFOURTHSPAWNER = 639;
    protected static final int XFIRSTSPAWNER_SECOND_WORLD = 960, YFIRSTSPAWNER_SECOND_WORLD = 0, XSECONDSPAWNER_SECOND_WORLD = 0,
            YSECONDSPAWNER_SECOND_WORLD = 128, XTHIRDSPAWNER_SECOND_WORLD = 1152, YTHIRDSPAWNER_SECOND_WORLD = 511,
            XFOURTHSPAWNER_SECOND_WORLD = 192, YFOURTHSPAWNER_SECOND_WORLD = 639;
    protected static final int ONESECOND = 1000, BOOST_TIMER_EACH = 20000, WAIT_TIME_BEFORE_START_OF_LEVEL = 0,
            BLACK_SCREEN_LENGTH = 3000;

    protected Handler handler;

    protected int spawnX, spawnY;
    //x and y coordinates
    protected int[][] tiles;

    //entities
    protected EntityManager entityManager;

    //how big world will be (number of tiles)
    protected int width, height;

    protected int defeatedEnemies = 0;

    protected boolean defeatedWorld = false;

    //for timing
    protected long now;
    protected long lastTime = System.currentTimeMillis();;
    protected long delta = 0;

    //timing booster
    protected long lastTimeBooster = System.currentTimeMillis();
    protected long timerBooster = 0;

    //timing start
    protected long lastTimeStartLevel = System.currentTimeMillis();
    protected long timerStartLevel = 0;

    //timing end
    protected long lastTimeEndLevel = System.currentTimeMillis();
    protected long timerEndLevel = 0;
    protected boolean blackscreen = false;
    protected Animation endLevelAnimation;

    public World(Handler pHandler, String pPath, EntityManager pEntityManager){
        entityManager = pEntityManager;

        handler = pHandler;
        loadWorld(pPath);

        /*Spawner spawner1 = new Spawner(handler, XFIRSTSPAWNER, YFIRSTSPAWNER);
        entityManager.addEntity(spawner1, 0);
        entityManager.getSpawners().add(spawner1);
        Spawner spawner2 = new Spawner(handler, XSECONDSPAWNER, YSECONDSPAWNER);
        entityManager.addEntity(spawner2, 1);
        entityManager.getSpawners().add(spawner2);
        Spawner spawner3 = new Spawner(handler, XTHIRDSPAWNER, YTHIRDSPAWNER);
        entityManager.addEntity(spawner3, 2);
        entityManager.getSpawners().add(spawner3);
        Spawner spawner4 = new Spawner(handler, XFOURTHSPAWNER, YFOURTHSPAWNER);
        entityManager.addEntity(spawner4, 3);
        entityManager.getSpawners().add(spawner4);*/

        entityManager.getPlayer().setX(spawnX);
        entityManager.getPlayer().setY(spawnY);

        endLevelAnimation = new Animation(175, Assets.banner_end_level_animation);

        //lastTimeBooster = System.currentTimeMillis();
    }

    protected void loadWorld(String pPath){
        String file = Utils.loadFileAsString(pPath);
        //split up every number of String and put it into array
        String[] tokens = file.split("\\s+");
        //depends on out file
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tiles = new int[width][height];
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width ;x++){
                //adding 4 because on first four positions are width, height, x and y
                tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
            }
        }
    }

    public Tile getTile(int x, int y){
        //make sure its not outside of the map
        if (x < 0 || y < 0 || x >= width || y >= height)
            return Tile.nothingTile;

        Tile t = Tile.tiles[tiles[x][y]];
        if (t == null){
            //default return nothing tile
            return Tile.nothingTile;
        }else
            return t;
    }

    protected void renderEndLevelBanner(Graphics g){
        g.drawImage(endLevelAnimation.getCurrentFrame(), handler.getGame().getWidth() / 2 - BANNER_END_LEVEL_WIDTH / 2,
                handler.getGame().getHeight() / 4 - (handler.getGame().getHeight() / 18) / 2,
                BANNER_END_LEVEL_WIDTH,
                BANNER_END_LEVEL_HEIGHT, null);
    }

    //abstract part
    public abstract void tick();
    public abstract void render(Graphics g);

    //getters and setters
    public boolean isDefeatedWorld() {
        return defeatedWorld;
    }

    public void setDefeatedWorld(boolean defeatedWorld) {
        this.defeatedWorld = defeatedWorld;
        if (!defeatedWorld){
            blackscreen = false;
        }
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(int spawnX) {
        this.spawnX = spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public void setSpawnY(int spawnY) {
        this.spawnY = spawnY;
    }

    public void addDefeatedEnemy(){
        defeatedEnemies++;
    }

    public void setDefeatedEnemies(int defeatedEnemies) {
        this.defeatedEnemies = defeatedEnemies;
    }

    public void setLastTimeBooster(long lastTimeBooster) {
        this.lastTimeBooster = lastTimeBooster;
    }

    public void setLastTimeStartLevel(long lastTimeStartLevel) {
        this.lastTimeStartLevel = lastTimeStartLevel;
    }

    public void setTimerStartLevel(long timerStartLevel) {
        this.timerStartLevel = timerStartLevel;
    }
    public long getTimerStartLevel() {
        return timerStartLevel;
    }
}
