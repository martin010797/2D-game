package game;

import graphics.GameCamera;
import input.KeyManager;
import input.MouseManager;
import worlds.FirstWorld;
import worlds.World;

public class Handler {

    public Game game;
    public World world;

    public Handler(Game pgame){
        game = pgame;
    }

    public GameCamera getGameCamera(){
        return game.getGameCamera();
    }

    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }

    public int getWidth(){
        return game.getWidth();
    }

    public int getHeight(){
        return game.getHeight();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public MouseManager getMouseManager(){
        return game.getMouseManager();
    }

}
