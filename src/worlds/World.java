package worlds;

import creatures.Enemy;
import creatures.Player;
import entities.EntityManager;
import game.Handler;
import graphics.Assets;
import jdk.swing.interop.SwingInterOpUtils;
import statics.DoubleCoinsBoost;
import statics.ImmortalityBoost;
import statics.Spawner;
import statics.SpeedBoost;
import tiles.Tile;
import utils.Utils;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class World {

    private static final int NUMBEROFENEMIES = 250, MAXNUMBEROFENEMIESONSCREEN = 20, NUMBEROFSPAWNERS = 4, ZERO = 0,
            NUMBER_OF_BOOSTS = 3;
    private static final int FIRSTSPAWNER = 0, SECONDSPAWNER = 1, THIRDSPAWNER = 2, FOURTHSPAWNER = 3;
    private static final int FIRST_BOOST = 0, SECOND_BOOST = 1, THIRD_BOOST = 2;
    private static final int XFIRSTSPAWNER = 192, YFIRSTSPAWNER = 0, XSECONDSPAWNER = 0, YSECONDSPAWNER = 511,
        XTHIRDSPAWNER = 1152, YTHIRDSPAWNER = 128, XFOURTHSPAWNER = 960, YFOURTHSPAWNER = 639;
    private static final int ONESECOND = 1000, BOOST_TIMER_EACH = 20000;

    private Handler handler;
    //how big world will be (number of tiles)
    private int width, height;

    private int spawnX, spawnY;
    //x and y coordinates
    private int[][] tiles;
    //entities
    private EntityManager entityManager;
    //array list of enemies
    //private ArrayList<Enemy> enemies;
    private int defeatedEnemies = 0;

    //for timing
    private long now;
    private long lastTime = System.currentTimeMillis();;
    private long delta = 0;

    //timing booster
    private long lastTimeBooster = System.currentTimeMillis();
    private long timerBooster = 0;

    public World(Handler pHandler, String pPath){
        handler = pHandler;
        loadWorld(pPath);
        entityManager = new EntityManager(handler, new Player(handler, 200, 200));

        Spawner spawner1 = new Spawner(handler, XFIRSTSPAWNER, YFIRSTSPAWNER);
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
        entityManager.getSpawners().add(spawner4);

        //creating world from file
        //loadWorld(pPath);

        entityManager.getPlayer().setX(spawnX);
        entityManager.getPlayer().setY(spawnY);

        //enemies = new ArrayList<Enemy>();
    }

    public boolean elapsedOneSecond(){
        now = System.currentTimeMillis();
        delta += now - lastTime;
        lastTime = now;
        if (delta >= ONESECOND)
            return true;
        else
            return false;
    }

    public void tick(){
        entityManager.tick();
        if (NUMBEROFENEMIES == defeatedEnemies){
            System.out.println("WIN");
        }
        if (entityManager.getEnemies().size() < MAXNUMBEROFENEMIESONSCREEN && (defeatedEnemies + entityManager.getEnemies().size()) < NUMBEROFENEMIES){
            //entityManager.getSpawners().get(0).setOpening(true);
            if (elapsedOneSecond()){
                int randomSpawner = ThreadLocalRandom.current().nextInt(ZERO, NUMBEROFSPAWNERS);
                switch (randomSpawner){
                    case FIRSTSPAWNER:{
                        Enemy e = new Enemy(handler, XFIRSTSPAWNER, YFIRSTSPAWNER);
                        entityManager.getSpawners().get(FIRSTSPAWNER).setOpening(true);
                        entityManager.getEnemies().add(e);
                        entityManager.addEntity(e);
                        break;
                    }
                    case SECONDSPAWNER:{
                        Enemy e = new Enemy(handler, XSECONDSPAWNER, YSECONDSPAWNER);
                        entityManager.getSpawners().get(SECONDSPAWNER).setOpening(true);
                        entityManager.getEnemies().add(e);
                        entityManager.addEntity(e);
                        break;
                    }
                    case THIRDSPAWNER:{
                        Enemy e = new Enemy(handler, XTHIRDSPAWNER, YTHIRDSPAWNER);
                        entityManager.getSpawners().get(THIRDSPAWNER).setOpening(true);
                        entityManager.getEnemies().add(e);
                        entityManager.addEntity(e);
                        break;
                    }
                    case FOURTHSPAWNER:{
                        Enemy e = new Enemy(handler, XFOURTHSPAWNER, YFOURTHSPAWNER);
                        entityManager.getSpawners().get(FOURTHSPAWNER).setOpening(true);
                        entityManager.getEnemies().add(e);
                        entityManager.addEntity(e);
                        break;
                    }
                }
                delta = 0;
            }

        }
    }

    public void render(Graphics g){
        //render just object which we can see on screen
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH );
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
        int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
        int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getTile(x,y).render(g,(int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
                //if else because of rock tile need grass under it
                /*if (tiles[x][y] != 2){
                    getTile(x,y).render(g,(int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
                }else {
                    Tile.tiles[0].render(g,(int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
                    getTile(x,y).render(g,(int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
                }*/
            }
        }
        for (int i = 0; i < 7; i++){
            g.drawImage(Assets.player_stats_background, i * Tile.TILEWIDTH, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
        }
        g.drawImage(Assets.bubble_background1, 0, handler.getGame().getHeight() - Tile.TILEHEIGHT, Tile.TILEWIDTH - 20 ,Tile.TILEHEIGHT , null);
        g.drawImage(Assets.static_player_down, - 7, handler.getGame().getHeight() - Tile.TILEHEIGHT + 3,Tile.TILEWIDTH - 7 ,Tile.TILEHEIGHT - 7 , null);
        g.drawImage(Assets.bubble_background1, 100, handler.getGame().getHeight() - Tile.TILEHEIGHT + 3,Tile.TILEWIDTH - 22 ,Tile.TILEHEIGHT - 7 , null);
        g.drawImage(Assets.coin, 92, handler.getGame().getHeight() - Tile.TILEHEIGHT + 3,Tile.TILEWIDTH - 7 ,Tile.TILEHEIGHT - 7 , null);
        g.drawImage(Assets.player_ability, 5 * Tile.TILEWIDTH, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);

        /*
        timerAbility += System.currentTimeMillis() - lastTimeAbility;
        lastTimeAbility = System.currentTimeMillis();
        if (timerAbility >= ABILITYCHARGETIME && !abilityReady){
            abilityReady = true;
            //System.out.println("ability ready");
        }
         */

        timerBooster += System.currentTimeMillis() - lastTimeBooster;
        lastTimeBooster = System.currentTimeMillis();
        if (timerBooster >= BOOST_TIMER_EACH){
            int randomBoost = ThreadLocalRandom.current().nextInt(ZERO, NUMBER_OF_BOOSTS);
            int randomX = ThreadLocalRandom.current().nextInt((int) (Tile.TILEWIDTH * 2) , (int) (width * Tile.TILEWIDTH - Tile.TILEWIDTH * 2 + 1));
            int randomY = ThreadLocalRandom.current().nextInt((int) (Tile.TILEHEIGHT * 2), (int) (height * Tile.TILEWIDTH - Tile.TILEHEIGHT * 2 + 1));
            switch (randomBoost){
                case FIRST_BOOST:{
                    DoubleCoinsBoost boost = new DoubleCoinsBoost(handler, randomX, randomY, Tile.TILEWIDTH, Tile.TILEHEIGHT);
                    entityManager.addEntity(boost);
                    break;
                }
                case SECOND_BOOST:{
                    ImmortalityBoost boost = new ImmortalityBoost(handler, randomX, randomY, Tile.TILEWIDTH, Tile.TILEHEIGHT);
                    entityManager.addEntity(boost);
                    break;
                }
                case THIRD_BOOST:{
                    SpeedBoost boost = new SpeedBoost(handler, randomX, randomY, Tile.TILEWIDTH, Tile.TILEHEIGHT);
                    entityManager.addEntity(boost);
                    break;
                }
            }
            timerBooster = 0;
        }
        //entities
        entityManager.render(g);
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

    private void loadWorld(String pPath){
        /*width = 5;
        height = 5;
        tiles = new int[width][height];

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height ;y++){
                tiles[x][y] = 0;
            }
        }
         */
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

}
