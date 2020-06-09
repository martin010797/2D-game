package statics;

import game.Handler;
import graphics.Assets;
import tiles.Tile;

import java.awt.*;

public class Tree extends StaticEntity {


    public Tree(Handler pHandler, float x, float y) {
        super(pHandler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);
        bounds.x = 20;
        bounds.y = (int) (height / 1.5f);
        bounds.width = 16;
        bounds.height = (int) (height - height / 1.5f);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        //g.setColor(Color.red);
        //g.fillRect((int) (x + bounds.x), (int) (y + bounds.y), bounds.width, bounds.height);
    }
}
