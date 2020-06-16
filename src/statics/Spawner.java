package statics;

import game.Handler;
import graphics.Assets;
import tiles.Tile;

import java.awt.*;

public class Spawner extends StaticEntity {

    public Spawner(Handler pHandler, float x, float y) {
        super(pHandler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);
        //later can set bounds
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 0;
        bounds.height = 0;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.spawner, (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }
}
