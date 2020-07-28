package states;

import entities.EntityManager;
import game.Handler;
import worlds.FirstWorld;
import worlds.SecondWorld;
import worlds.World;

import java.awt.*;

public class SecondLevelState extends State {

    private World world;
    private boolean defeated = false;

    public SecondLevelState(Handler pHandler, EntityManager pEntityManager) {
        super(pHandler);
        world = new SecondWorld(pHandler,"resources/worlds/world2.txt", pEntityManager);
        //handler.setWorld(world);
    }

    public void setWorld(){
        handler.setWorld(world);
        world.setDefeatedEnemies(0);
        world.setDefeatedWorld(false);
    }

    @Override
    public void tick() {
        world.tick();
        defeated = world.isDefeatedWorld();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
        world.setDefeatedWorld(defeated);
    }
}
