package creatures;
import entities.*;
import game.Handler;
import graphics.Animation;
import graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Creature {

    private static final int DEFAULT_DELTA_PROJECTILE = 250, RIFLE_PROJECTILE_DELTA = 100, DEFAULT_NUMBER_OF_LIVES = 3;

    private Animation animDown, animUp, animLeft, animRight, animDownLeft, animDownRight, animUpLeft, animUpRight;
    private Direction direction;
    private ArrayList<Projectile> projectiles;
    private int numberOfLives;
    private boolean dead = false;
    private boolean endOfGame = false;
    private boolean immortal = false;
    private TypeOfProjectile typeOfProjectile;

    private int numberOfCoins = 0;

    private long deltaRespawn = 0;
    private long nowRespawn;
    private long lastTimeRespawn = System.currentTimeMillis();

    private long delta = 0;
    private long now;
    private long lastTime = System.currentTimeMillis();


    private boolean shooting = false;

    public Player(Handler pHandler, float x, float y) {
        super(pHandler, x, y,Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        direction = Direction.DOWN;
        numberOfLives = DEFAULT_NUMBER_OF_LIVES;
        typeOfProjectile = TypeOfProjectile.DEFAULT;

        bounds.x = 20;
        bounds.y = 2;
        bounds.width = 22;
        bounds.height = 60;

        //animations
        animDown = new Animation(175, Assets.player_down);
        animUp = new Animation(175, Assets.player_up);
        animLeft = new Animation(175, Assets.player_left);
        animRight = new Animation(175, Assets.player_right);
        animDownLeft = new Animation(175, Assets.player_down_left);
        animDownRight = new Animation(175, Assets.player_down_right);
        animUpLeft = new Animation(175, Assets.player_up_left);
        animUpRight = new Animation(175, Assets.player_up_right);

        projectiles = new ArrayList<Projectile>();
    }

    public void respawn(){
        //bounds.x = 0;
        //bounds.y = 0;
        //bounds.width = 0;
        //bounds.height = 0;
        x = handler.getWorld().getSpawnX();
        y = handler.getWorld().getSpawnY();
        immortal = true;
    }

    public void die(){
        if (!dead){
            respawn();
            System.out.println("dead");
            lastTimeRespawn = System.currentTimeMillis();
            dead = true;
            numberOfLives--;
            if (numberOfLives == -1){
                endOfGame = true;
                System.out.println("end of game");
            }
        }
    }

    @Override
    public void tick() {
        //animations
        animDown.tick();
        animUp.tick();
        animRight.tick();
        animLeft.tick();
        animUpLeft.tick();
        animUpRight.tick();
        animDownLeft.tick();
        animDownRight.tick();

        //dead and respawn
        if (dead){
            nowRespawn = System.currentTimeMillis();
            deltaRespawn += nowRespawn - lastTimeRespawn;
            lastTimeRespawn = nowRespawn;
            if (deltaRespawn >= 3000){
                //bounds.x = 20;
                //bounds.y = 2;
                //bounds.width = 22;
                //bounds.height = 60;
                dead = false;
                immortal = false;
                System.out.println("alive");
                deltaRespawn = 0;
            }
        }
        //movement
        getInput();
        move();
        //if I want to have him in the center
        //handler.getGameCamera().centerOnEntity(this);
        for (Projectile p: projectiles) {
            p.tick();
        }
    }

    private void getInput(){
        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().up){
            yMove = -speed;
            direction = Direction.UP;
        }
        if (handler.getKeyManager().down){
            yMove = speed;
            direction = Direction.DOWN;
        }
        if (handler.getKeyManager().left) {
            xMove = -speed;
            direction = Direction.LEFT;
        }
        if (handler.getKeyManager().right){
            xMove = speed;
            direction = Direction.RIGHT;
        }

        if (handler.getKeyManager().up && handler.getKeyManager().left)
            direction = Direction.UP_LEFT;
        if (handler.getKeyManager().up && handler.getKeyManager().right)
            direction = Direction.UP_RIGHT;
        if (handler.getKeyManager().down && handler.getKeyManager().left)
            direction = Direction.DOWN_LEFT;
        if (handler.getKeyManager().down && handler.getKeyManager().right)
            direction = Direction.DOWN_RIGHT;

        //shooting
        now = System.currentTimeMillis();
        delta += now - lastTime;
        lastTime = now;
        switch (typeOfProjectile){
            case RIFLE:{
                //each 100 milisecs
                if (delta >= RIFLE_PROJECTILE_DELTA || !shooting){
                    if (handler.getKeyManager().space){
                        shooting = true;
                        float posX = x;
                        float posY = y;
                        switch (direction){
                            case UP:
                                posY = y - 30;
                                break;
                            case DOWN:
                                posY = y + 20;
                                break;
                            case LEFT:
                                posX = x - 22;
                                break;
                            case RIGHT:
                                posX = x + 22;
                                break;
                            case UP_LEFT:
                                posX = x - 22;
                                posY = y - 23;
                                break;
                            case UP_RIGHT:
                                posX = x + 27;
                                posY = y - 23;
                                break;
                            case DOWN_LEFT:
                                posX = x - 25;
                                posY = y + 18;
                                break;
                            case DOWN_RIGHT:
                                posX = x + 24;
                                posY = y + 18;
                                break;
                        }
                        Projectile newProjectile = new RifleProjectile(handler, posX, posY, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, direction, this);
                        projectiles.add(newProjectile);
                    }
                    if (!handler.getKeyManager().space){
                        shooting = false;
                    }
                    delta = 0;
                }
                break;
            }
            case DEFAULT:{
                //each 250 milisecs
                if (delta >= DEFAULT_DELTA_PROJECTILE || !shooting){
                    if (handler.getKeyManager().space){
                        shooting = true;
                        float posX = x;
                        float posY = y;
                        switch (direction){
                            case UP:
                                posY = y - 30;
                                break;
                            case DOWN:
                                posY = y + 20;
                                break;
                            case LEFT:
                                posX = x - 22;
                                break;
                            case RIGHT:
                                posX = x + 22;
                                break;
                            case UP_LEFT:
                                posX = x - 22;
                                posY = y - 23;
                                break;
                            case UP_RIGHT:
                                posX = x + 27;
                                posY = y - 23;
                                break;
                            case DOWN_LEFT:
                                posX = x - 25;
                                posY = y + 18;
                                break;
                            case DOWN_RIGHT:
                                posX = x + 24;
                                posY = y + 18;
                                break;
                        }
                        Projectile newProjectile = new DefaultProjectile(handler, posX, posY, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, direction, this);
                        projectiles.add(newProjectile);
                        //System.out.println("shooting");
                    }
                    if (!handler.getKeyManager().space){
                        shooting = false;
                        //System.out.println("not shooting");
                    }
                    delta = 0;
                }
                break;
            }
        }


    }

    @Override
    public void render(Graphics g) {
        //setting size just by adding to parametres
        //if I dont want player in the center
        //g.drawImage(Assets.player,(int) (x),(int)y, width, height, null);
        //collision box
        //if I want to see collison box
        //g.setColor(Color.red);

        //g.fillRect((int) (x + bounds.x), (int) (y + bounds.y), bounds.width, bounds.height);
        //if I want to have it in center
        g.drawImage(getCurrentAnimationFrame(),(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

        Iterator itr = projectiles.iterator();
        while (itr.hasNext()){
            Projectile p = (Projectile) itr.next();
            //p.render(g);
            if (p.isDestroyed())
                itr.remove();
            else
                p.render(g);
        }
        System.out.println("number of coins:" + numberOfCoins);

        /*Projectile destroyedProjectile = null;
        for (Projectile p: projectiles) {
            p.render(g);
            if (p.isDestroyed())
                destroyedProjectile = p;
        }
        if (destroyedProjectile != null)
            projectiles.remove(destroyedProjectile);*/

        //collision box
        //if I want to see collison box
        //g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
                //(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
    }

    private BufferedImage getCurrentAnimationFrame(){
        //if (xMove < 0 && yMove > 0)
          //  System.out.println("moving down left");
        if (xMove < 0){//moving left
            if (yMove > 0)
                return animDownLeft.getCurrentFrame();
            if(yMove < 0)
                return animUpLeft.getCurrentFrame();
            return animLeft.getCurrentFrame();
        }else if (xMove > 0){//moving right
            if (yMove > 0)
                return animDownRight.getCurrentFrame();
            if (yMove < 0)
                return animUpRight.getCurrentFrame();
            else
                return animRight.getCurrentFrame();
        }else if (yMove < 0){//moving up
            return animUp.getCurrentFrame();
        }else if (yMove > 0){//moving down
            return animDown.getCurrentFrame();
        }else {
            switch (direction){
                case UP:
                    return Assets.static_player_up;
                case DOWN:
                    return Assets.static_player_down;
                case LEFT:
                    return Assets.static_player_left;
                case RIGHT:
                    return Assets.static_player_right;
                case UP_LEFT:
                    return Assets.static_player_up_left;
                case UP_RIGHT:
                    return Assets.static_player_up_right;
                case DOWN_LEFT:
                    return Assets.static_player_down_left;
                case DOWN_RIGHT:
                    return Assets.static_player_down_right;
            }
        }
        //default value
        return Assets.player_down[0];
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isEndOfGame() {
        return endOfGame;
    }

    public void setEndOfGame(boolean endOfGame) {
        this.endOfGame = endOfGame;
    }

    public int getNumberOfCoins() {
        return numberOfCoins;
    }

    public void addCoin(){
        numberOfCoins++;
    }

    public boolean isImmortal() {
        return immortal;
    }
}
