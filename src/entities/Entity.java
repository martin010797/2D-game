package entities;

import creatures.Enemy;
import creatures.Player;
import creatures.TypeOfBoost;
import game.Game;
import game.Handler;
import org.w3c.dom.css.Rect;
import statics.Boost;
import statics.DoubleCoinsBoost;
import statics.ImmortalityBoost;
import statics.SpeedBoost;

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

    public boolean chceckEntityCollision(float xOffset, float yOffset){
        for (Entity e: handler.getWorld().getEntityManager().getEntities()){
            //avoiding collisions enemy with boosts
            if (this instanceof Enemy && e instanceof Boost || this instanceof Boost && e instanceof Enemy)
                continue;
            //avoiding collisions projectile with boosts
            if (this instanceof Projectile && e instanceof Boost || this instanceof Boost && e instanceof Projectile)
                continue;

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
                //rpg projectile wont destroy after hitting enemy
                if ((this instanceof RPGProjectile && e instanceof Enemy) || (this instanceof Enemy && e instanceof RPGProjectile) ){
                    return false;
                }
                //checking collision player with boosts
                if (this instanceof Player && e instanceof SpeedBoost){
                    ((Player) this).setTypeOfBoost(TypeOfBoost.SPEED);
                    ((SpeedBoost) e).destroy();
                }
                if (this instanceof Player && e instanceof DoubleCoinsBoost){
                    ((Player) this).setTypeOfBoost(TypeOfBoost.DOUBLE_COINS);
                    ((DoubleCoinsBoost) e).destroy();
                }
                if (this instanceof Player && e instanceof ImmortalityBoost){
                    ((Player) this).setTypeOfBoost(TypeOfBoost.IMMORTALITY);
                    ((ImmortalityBoost) e).destroy();
                }
                return true;
            }
        }
        return false;
    }

    public boolean chceckEntityCollisionExcludeEntity(float xOffset, float yOffset, Entity pEntity){
        for (Entity e: handler.getWorld().getEntityManager().getEntities()){
            //avoiding collisions enemy with boosts
            if (this instanceof Enemy && e instanceof Boost || this instanceof Boost && e instanceof Enemy)
                continue;
            //avoiding collisions projectile with boosts
            if (this instanceof Projectile && e instanceof Boost || this instanceof Boost && e instanceof Projectile)
                continue;
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
            //checking if entity dont want to leave map
            //different for projectile to allow player to shoot while he is near edge of the map
            if (this instanceof Projectile){
                if ((int) (x + bounds.x + xOffset) < -20 || ((int) (x + bounds.x + bounds.width + xOffset) > handler.getWidth() + 25)
                        || (int) (y + bounds.y + yOffset) < -20)
                    return true;
            }else {
                if ((int) (x + bounds.x + xOffset) < 0 || ((int) (x + bounds.x + bounds.width + xOffset) > handler.getWidth())
                        || (int) (y + bounds.y + yOffset) < 0)
                    return true;
            }

            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))){
                if (this instanceof Projectile && e instanceof Enemy){
                    ((Enemy) e).setDead(true);
                }
                //rpg projectile wont destroy after hitting enemy
                if ((this instanceof RPGProjectile && e instanceof Enemy) || (this instanceof Enemy && e instanceof RPGProjectile) ){
                    return false;
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
