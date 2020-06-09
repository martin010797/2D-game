package tiles;

import graphics.Assets;

public class WaterTile extends Tile {
    public WaterTile(int pId) {
        super(Assets.water, pId);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
