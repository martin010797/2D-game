package worlds;

import creatures.Player;
import entities.EntityManager;
import game.Game;
import game.Handler;
import jdk.jshell.execution.Util;
import statics.Rock;
import statics.Tree;
import tiles.Tile;
import utils.Utils;

import java.awt.*;

public class World {

    private Handler handler;
    //how big world will be (number of tiles)
    private int width, height;
    private int spawnX, spawnY;
    //x and y coordinates
    private int[][] tiles;
    //entities
    private EntityManager entityManager;


    public World(Handler pHandler, String pPath){
        handler = pHandler;
        entityManager = new EntityManager(handler, new Player(handler, 200, 200));
        //adding other entities
        entityManager.addEntity(new Tree(handler, 350, 200));
        entityManager.addEntity(new Rock(handler, 100,100));
        entityManager.addEntity(new Rock(handler, 300,120));

        //creating world from file
        loadWorld(pPath);

        entityManager.getPlayer().setX(spawnX);
        entityManager.getPlayer().setY(spawnY);
    }

    public void tick(){
        entityManager.tick();
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
        //entities
        entityManager.render(g);
    }

    public Tile getTile(int x, int y){
        //make sure its not outside of the map
        if (x < 0 || y < 0 || x >= width || y >= height)
            return Tile.grassTile;

        Tile t = Tile.tiles[tiles[x][y]];
        if (t == null){
            //default return water tile
            return Tile.waterTile;
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

}
