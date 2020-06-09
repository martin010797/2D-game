package statics;

import game.Handler;
import graphics.Assets;
import tiles.Tile;

import java.awt.*;

public class Rock extends StaticEntity{


    public Rock(Handler pHandler, float x, float y) {
        super(pHandler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);

        bounds.x = 8;
        bounds.y = 32;
        bounds.width = (int) (width * 3/5);
        bounds.height = 11;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.rock, (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        //g.setColor(Color.red);
        //g.fillRect((int) (x + bounds.x), (int) (y + bounds.y), bounds.width, bounds.height);
    }
}
