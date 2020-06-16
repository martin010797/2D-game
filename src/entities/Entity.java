package entities;

import game.Game;
import game.Handler;
import org.w3c.dom.css.Rect;

import java.awt.*;

public abstract class Entity {

    protected Handler handler;
    protected float x, y;
    protected int width, height;
    protected Rectangle bounds;

    public Entity(Handler pHandler, float x, float y,int pWidth, int pHeight){
        handler = pHandler;
        this.x = x;
        this.y = y;
        width = pWidth;
        height = pHeight;

        //by default are bounds set for whole image of entity
        bounds = new Rectangle(0, 0, width, height);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public abstract void tick();
    public abstract void render(Graphics g);

    public boolean chceckEntityCollision(float xOffset, float yOffset){
        for (Entity e: handler.getWorld().getEntityManager().getEntities()){
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
                return true;
        }
        return false;
    }

    public boolean chceckEntityCollisionExcludeEntity(float xOffset, float yOffset, Entity pEntity){
        for (Entity e: handler.getWorld().getEntityManager().getEntities()){
            if (e.equals(this) || e.equals(pEntity))
                continue;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
                return true;
        }
        return false;
    }

    public Rectangle getCollisionBounds(float xOffset, float yOffset){
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
    }
}
