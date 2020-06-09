package statics;

import entities.Entity;
import game.Handler;

//for entities which dont move
public abstract class StaticEntity extends Entity {

    public StaticEntity(Handler pHandler, float x, float y, int pWidth, int pHeight) {
        super(pHandler, x, y, pWidth, pHeight);
    }
}
