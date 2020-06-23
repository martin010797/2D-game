package states;

import creatures.Enemy;
import creatures.Player;
import game.Game;
import game.Handler;
import statics.Tree;
import tiles.Tile;
import worlds.World;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
