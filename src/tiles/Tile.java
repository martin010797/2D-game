package tiles;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Tile {

    public static Tile[] tiles = new Tile[256];
    //public static Tile grassTile = new GrassTile(0);
    //public static Tile waterTile = new WaterTile(1);
    public static Tile nothingTile = new NothingTile(0);
    public static Tile world1TextureTile = new World1TextureTile(1);
    public static Tile world2TextureTile = new World2TextureTile(2);

    public static final int TILEWIDTH = 64, TILEHEIGHT = 64;

    protected BufferedImage texture;
    protected final int id;

    public Tile(BufferedImage pTexture, int pId){
        texture = pTexture;
        id = pId;

        tiles[id] = this;
    }

    public void tick(){

    }

    public void render(Graphics g, int x, int y){
        g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
    }

    //default is false so if I want to use some tile which should be solid just override it in class
    public boolean isSolid(){
        return false;
    }

    public int getId(){
        return id;
    }
}
