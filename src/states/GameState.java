package states;

import audio.AudioPlayer;
import game.Handler;
import worlds.World;

import java.awt.*;

public class GameState extends State {

    //private Player player;
    private World testWorld;

    public GameState(Handler pHandler){
        super(pHandler);
        testWorld = new World(pHandler,"resources/worlds/world1.txt");
        handler.setWorld(testWorld);

        //player = new Player(pHandler,200,200);

        //changing camera
        //game.getGameCamera().move(50,150);
    }

    @Override
    public void tick() {
        testWorld.tick();
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
}
