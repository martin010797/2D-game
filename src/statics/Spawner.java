package statics;

import game.Game;
import game.Handler;
import game.Level;
import graphics.Animation;
import graphics.Assets;
import tiles.Tile;

import java.awt.*;

public class Spawner extends StaticEntity {

    private boolean opening = false;
    private long lastTime, timer;
    //private Level level;

    public Spawner(Handler pHandler, float x, float y) {
        super(pHandler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);
        //later can set bounds
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 0;
        bounds.height = 0;

        lastTime = System.currentTimeMillis();
        timer = 0;

        //Game.level
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {

        if (!opening){
            switch (handler.getGame().getLevel()){
                case FIRST_LEVEL:{
                    g.drawImage(Assets.spawner, (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
                    break;
                }
                case SECOND_LEVEL:{
                    g.drawImage(Assets.spawner_second_world, (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
                    break;
                }
                default:{
                    System.out.println("not identified level for spawner");
                    break;
                }
            }
        }else {
            animationOpeningDoor(g);
        }
    }

    //maybe better way could be implement it as class for one time animation(not cycle)
    public void animationOpeningDoor(Graphics g){
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        switch (handler.getGame().getLevel()){
            case FIRST_LEVEL:{
                if (timer <= 175){
                    g.drawImage(Assets.spawner_door[0], (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
                }else if (timer <= 350){
                    g.drawImage(Assets.spawner_door[1], (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
                }else if (timer <= 525){
                    g.drawImage(Assets.spawner_door[2], (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
                }else if (timer <= 700){
                    g.drawImage(Assets.spawner_door[3], (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
                }else if (timer <= 875){
                    g.drawImage(Assets.spawner_door[4], (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
                }else {
                    opening = false;
                }
                break;
            }
            case SECOND_LEVEL:{
                if (timer <= 175){
                    g.drawImage(Assets.spawner_door_second_world[0], (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
                }else if (timer <= 350){
                    g.drawImage(Assets.spawner_door_second_world[1], (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
                }else if (timer <= 525){
                    g.drawImage(Assets.spawner_door_second_world[2], (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
                }else if (timer <= 700){
                    g.drawImage(Assets.spawner_door_second_world[3], (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
                }else if (timer <= 875){
                    g.drawImage(Assets.spawner_door_second_world[4], (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
                }else {
                    opening = false;
                }                break;
            }
            default:{
                System.out.println("not identified level for spawner animation");
                break;
            }
        }
    }

    public boolean isOpening() {
        return opening;
    }

    public void setOpening(boolean opening) {
        if (!this.opening && opening){
            this.opening = opening;
            timer = 0;
            lastTime = System.currentTimeMillis();
        }else
            this.opening = false;
    }

}
