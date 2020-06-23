package entities;

import creatures.Enemy;
import creatures.Player;
import game.Game;
import game.Handler;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.Comparator;

public abstract class Entity {

    protected Handler handler;
    protected float x, y;
    protected int width, height;
    protected Rectangle bounds;
    private Entity entity;

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


    /*
        if (!chceckEntityCollision(xMove, 0f))
                moveX();
            if (!chceckEntityCollision(0f, yMove))
                moveY();
         */
    public boolean chceckEntityCollision(float xOffset, float yOffset){
        for (Entity e: handler.getWorld().getEntityManager().getEntities()){
            if (this instanceof Enemy && e instanceof Enemy){
                //to avoid collisions enemy with enemy
                continue;
            }
            //not getting collison with coins
            if (this instanceof Coin || e instanceof Coin){
                if (e instanceof Coin && this instanceof Player ){
                    if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))){
                        if (!((Coin) e).isDestroyed()){
                            ((Coin) e).destroyCoin();
                            ((Player) this).addCoin();
                        }
                    }
                    continue;
                }
                else
                    continue;
            }
            //if player is immortal then dont check collision with enemy
            if (this instanceof Player && e instanceof Enemy ){
                if (((Player) this).isImmortal())
                    continue;
            }else if (this instanceof Enemy && e instanceof Player){
                if (((Player) e).isImmortal())
                    continue;
            }
            //if both are projectiles
            if (this instanceof Projectile && e instanceof Projectile)
                continue;
            if (e.equals(this))
                continue;
            //checking if entity dont want to leave map
            if ((int) (x + bounds.x + xOffset) < 0 || ((int) (x + bounds.x + bounds.width + xOffset) > handler.getWidth())
                    || (int) (y + bounds.y + yOffset) < 0)
                return true;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
            {
                if (this instanceof Player && e instanceof Enemy){
                    //if ((this instanceof Player && e instanceof Enemy) || (this instanceof Enemy && e instanceof Player)){
                    ((Player) this).die();
                }
                return true;
            }
        }
        return false;
    }

    public boolean chceckEntityCollisionExcludeEntity(float xOffset, float yOffset, Entity pEntity){
        for (Entity e: handler.getWorld().getEntityManager().getEntities()){
            //not getting collison with coins
            if (this instanceof Coin || e instanceof Coin){
                if (e instanceof Coin && this instanceof Player ){
                    if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))){
                        if (!((Coin) e).isDestroyed()){
                            ((Coin) e).destroyCoin();
                            ((Player) this).addCoin();
                        }
                    }
                    continue;
                }
                else
                    continue;
            }
            //if player is immortal then dont check collision with enemy
            if (this instanceof Player && e instanceof Enemy ){
                if (((Player) this).isImmortal())
                    continue;
            }else if (this instanceof Enemy && e instanceof Player){
                if (((Player) e).isImmortal())
                    continue;
            }


            if (this instanceof Enemy && e instanceof Enemy){
                //to avoid collisions enemy with enemy
                continue;
            }
            if (e.equals(this) || e.equals(pEntity))
                continue;
            //if both are projectiles
            if (this instanceof Projectile && e instanceof Projectile)
                continue;
            if ((int) (x + bounds.x + xOffset) < 0 || ((int) (x + bounds.x + bounds.width + xOffset) > handler.getWidth())
                    || (int) (y + bounds.y + yOffset) < 0)
                return true;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))){
                if (this instanceof Projectile && e instanceof Enemy){
                    ((Enemy) e).setDead(true);
                    //System.out.println("kill");
                }
                return true;
            }
        }
        return false;
    }


    public Rectangle getCollisionBounds(float xOffset, float yOffset){
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
    }
}
