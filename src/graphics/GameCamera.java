package graphics;

import entities.Entity;
import game.Game;
import game.Handler;
import tiles.Tile;

//if I want to change view part of game
public class GameCamera {

    private Handler handler;
    private float xOffset,yOffset;

    public GameCamera(Handler pHandler, float pXOffset, float pYOffset){
        handler = pHandler;
        xOffset = pXOffset;
        yOffset = pYOffset;
    }

    public void checkBlankSpace(){
        if (xOffset < 0){
            xOffset = 0;
        }else if (xOffset > handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getWidth()){
            xOffset = handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getWidth();
        }
        if (yOffset < 0){
            yOffset = 0;
        }else if (yOffset > handler.getWorld().getHeight() * Tile.TILEHEIGHT - handler.getHeight()){
            yOffset = handler.getWorld().getHeight() * Tile.TILEHEIGHT - handler.getHeight();
        }
    }

    //if I want to have my player (entity) in the center
    public void centerOnEntity(Entity pEntity){
        xOffset = pEntity.getX() - handler.getWidth() / 2 + pEntity.getWidth() / 2;
        yOffset = pEntity.getY() - handler.getHeight() / 2 + pEntity.getHeight() / 2;
        checkBlankSpace();
    }

    public void move(float xAmt, float yAmt){
        xOffset += xAmt;
        yOffset += yAmt;
        checkBlankSpace();
    }

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
        //checkBlankSpace();
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
        //checkBlankSpace();
    }


}
