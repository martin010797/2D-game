package tiles;

import graphics.Assets;

public class NothingTile extends Tile {
    public NothingTile(int pId) {
        super(Assets.nothing, pId);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
