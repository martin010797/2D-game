package states;

import entities.EntityManager;
import game.Handler;
import worlds.FirstWorld;
import worlds.World;

import java.awt.*;

public class FirstLevelState extends State {

    //private Player player;
    private World testWorld;

    private boolean defeated = false;

    public FirstLevelState(Handler pHandler, EntityManager pEntityManager){
        super(pHandler);
        testWorld = new FirstWorld(pHandler,"resources/worlds/world1.txt", pEntityManager);
        //handler.setWorld(testWorld);

        //player = new Player(pHandler,200,200);

        //changing camera
        //game.getGameCamera().move(50,150);
    }

    public void setWorld(){
        handler.setWorld(testWorld);
        testWorld.setDefeatedEnemies(0);
        testWorld.setDefeatedWorld(false);
    }

    @Override
    public void tick() {
        testWorld.tick();
        defeated = testWorld.isDefeatedWorld();
        //if (!backgroundMusic.isPlaying())
            //backgroundMusic.play();
        //player.tick();

    }

    @Override
    public void render(Graphics g) {
        //g.drawImage(Assets.enemy,50,20,null);
        //rendering grass
        //Tile.tiles[0].render(g,0,0);
        testWorld.render(g);
        //player.render(g);
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
        testWorld.setDefeatedWorld(defeated);
    }
}
