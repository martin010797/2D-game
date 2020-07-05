package creatures;
import entities.*;
import game.Handler;
import graphics.Animation;
import graphics.Assets;
import graphics.OneTimeAnimation;
import statics.DoubleCoinsBoost;
import statics.ImmortalityBoost;
import statics.SpeedBoost;
import tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Creature {

    private static final int DEFAULT_DELTA_PROJECTILE = 250, RIFLE_PROJECTILE_DELTA = 100, RPG_PROJECTILE_DELTA = 280, DEFAULT_NUMBER_OF_LIVES = 3,
            NUMBER_WIDTH = 32, NUMBER_HEIGHT = 32, CONFIRMATION_TIME = 750, ONESECOND = 1000, DEFAULT_PROJECTILES_REMINING = -1,
            SHOTGUN_PROJECTILE_DELTA = 300, ABILITYCHARGETIME = 15000, NO_BOOST_DURATION = -1, DOG_PRICE = 1;

    private Animation animDown, animUp, animLeft, animRight, animDownLeft, animDownRight, animUpLeft, animUpRight;
    private OneTimeAnimation animation_respawn;
    private Direction direction;
    private ArrayList<Projectile> projectiles;
    private int numberOfLives;
    private boolean dead = false;
    private boolean endOfGame = false;
    private boolean immortal = false;
    private TypeOfProjectile typeOfProjectile;
    private int remainingProjectiles = DEFAULT_PROJECTILES_REMINING;
    private int numberOfCoins = 0;
    private boolean activeDog = false;

    private boolean ignoreBuyRifle= false;
    private boolean ignoreBuyRPG= false;
    private boolean ignoreBuyShotgun = false;
    private boolean ignoreBuyDog = false;
    private boolean notEnoughMoney = false;

    //confiramtions for buying items
    private long nowItemQ;
    private long nowItemW;
    private long nowItemE;
    private long nowItemT;
    private long deltaItemQ = 0;
    private long deltaItemW = 0;
    private long deltaItemE = 0;
    private long deltaItemT = 0;
    private long lastTimeItemQ = System.currentTimeMillis();
    private long lastTimeItemW = System.currentTimeMillis();
    private long lastTimeItemE = System.currentTimeMillis();
    private long lastTimeItemT = System.currentTimeMillis();


    //ability
    private boolean abilityReady = false;
    private long lastTimeAbility = System.currentTimeMillis();
    private long timerAbility = 0;

    //boost
    private TypeOfBoost typeOfBoost;
    private long durationOfBoost = NO_BOOST_DURATION;
    private long timerBoost = 0;
    private long lastTimeBoost = System.currentTimeMillis();

    //wait time before disappearing banner
    private long nowBanner;
    private long deltaBanner = 0;
    private long lastTimeBanner = System.currentTimeMillis();

    //wait time before buying next item
    private long nowWaitTime;
    private long deltaWaitTime = 0;
    private long lastTimeWaitTime = System.currentTimeMillis();

    //time before respawning
    private long deltaRespawn = 0;
    private long nowRespawn;
    private long lastTimeRespawn = System.currentTimeMillis();

    //time before shooting
    private long delta = 0;
    private long now;
    private long lastTime = System.currentTimeMillis();

    private boolean shooting = false;

    public Player(Handler pHandler, float x, float y) {
        super(pHandler, x, y,Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        direction = Direction.DOWN;
        numberOfLives = DEFAULT_NUMBER_OF_LIVES;
        typeOfProjectile = TypeOfProjectile.DEFAULT;
        //remainingProjectiles = DefaultProjectile.

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
        animation_respawn = new OneTimeAnimation(100,Assets.respawn_animation);

        immortal = true;
        lastTimeRespawn = System.currentTimeMillis();
        animation_respawn.setLastTime(System.currentTimeMillis());
        typeOfBoost = TypeOfBoost.NO_ACTIVE_BOOST;
    }

    public void respawn(){
        //bounds.x = 0;
        //bounds.y = 0;
        //bounds.width = 0;
        //bounds.height = 0;
        x = handler.getWorld().getSpawnX();
        y = handler.getWorld().getSpawnY();
        immortal = true;
        animation_respawn.setLastTime(System.currentTimeMillis());
    }

    public void die(){
        if (!dead){
            respawn();
            lastTimeRespawn = System.currentTimeMillis();
            dead = true;
            numberOfLives--;
            //reseting boosts when die
            if (typeOfBoost == TypeOfBoost.SPEED)
                this.speed = DEFAULT_SPEED;
            typeOfBoost = TypeOfBoost.NO_ACTIVE_BOOST;
            durationOfBoost = NO_BOOST_DURATION;
            //resetting projectile
            typeOfProjectile = TypeOfProjectile.DEFAULT;
            remainingProjectiles = DEFAULT_PROJECTILES_REMINING;

            if (numberOfLives == -1){
                numberOfLives = 0;
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

        //respawning
        animation_respawn.tick();

        //dead and respawn
        if (dead || immortal){
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
        //special ability
        timerAbility += System.currentTimeMillis() - lastTimeAbility;
        lastTimeAbility = System.currentTimeMillis();
        if (timerAbility >= ABILITYCHARGETIME && !abilityReady){
            abilityReady = true;
            //System.out.println("ability ready");
        }

        //boost
        if (typeOfBoost != TypeOfBoost.NO_ACTIVE_BOOST){
            switch (typeOfBoost){
                case IMMORTALITY:{
                    immortal = true;
                    break;
                }
                case SPEED:{
                    this.speed = 4.0f;
                }
            }
            timerBoost += System.currentTimeMillis() - lastTimeBoost;
            lastTimeBoost = System.currentTimeMillis();
            if (timerBoost >= durationOfBoost){
                if (typeOfBoost == TypeOfBoost.IMMORTALITY)
                    immortal = false;
                if (typeOfBoost == TypeOfBoost.SPEED)
                    this.speed = DEFAULT_SPEED;
                typeOfBoost = TypeOfBoost.NO_ACTIVE_BOOST;
                durationOfBoost = NO_BOOST_DURATION;
            }
        }


        //movement
        if (animation_respawn.getCurrentFrame() == null){
            getInput();
            move();
        }
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

        float diagonalSpeed = (float) (Math.sqrt((double)((speed*speed)/2)));
        if (handler.getKeyManager().up && handler.getKeyManager().left){
            direction = Direction.UP_LEFT;
            xMove = -diagonalSpeed;
            yMove = -diagonalSpeed;
        }
        if (handler.getKeyManager().up && handler.getKeyManager().right){
            direction = Direction.UP_RIGHT;
            xMove = diagonalSpeed;
            yMove = -diagonalSpeed;
        }
        if (handler.getKeyManager().down && handler.getKeyManager().left){
            direction = Direction.DOWN_LEFT;
            xMove = -diagonalSpeed;
            yMove = diagonalSpeed;
        }
        if (handler.getKeyManager().down && handler.getKeyManager().right){
            direction = Direction.DOWN_RIGHT;
            xMove = diagonalSpeed;
            yMove = diagonalSpeed;
        }

        //shooting
        now = System.currentTimeMillis();
        delta += now - lastTime;
        lastTime = now;
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
        switch (typeOfProjectile){
            case RIFLE:{
                //each 100 milisecs
                if (delta >= RIFLE_PROJECTILE_DELTA || !shooting){
                    if (handler.getKeyManager().space){
                        shooting = true;
                        Projectile newProjectile = new RifleProjectile(handler, posX, posY, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, direction, this);
                        projectiles.add(newProjectile);
                        remainingProjectiles--;
                        if (remainingProjectiles == 0){
                            remainingProjectiles = DEFAULT_PROJECTILES_REMINING;
                            typeOfProjectile = TypeOfProjectile.DEFAULT;
                        }
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
                        Projectile newProjectile = new DefaultProjectile(handler, posX, posY, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, direction, this);
                        projectiles.add(newProjectile);
                        //System.out.println("shooting");
                    }
                    if (!handler.getKeyManager().space){
                        shooting = false;
                    }
                    delta = 0;
                }
                break;
            }
            case RPG:{
                if (delta >= RPG_PROJECTILE_DELTA || !shooting){
                    if (handler.getKeyManager().space){
                        shooting = true;
                        Projectile newProjectile = new RPGProjectile(handler, posX, posY, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, direction, this);
                        projectiles.add(newProjectile);
                        remainingProjectiles--;
                        if (remainingProjectiles == 0){
                            remainingProjectiles = DEFAULT_PROJECTILES_REMINING;
                            typeOfProjectile = TypeOfProjectile.DEFAULT;
                        }
                    }
                    if (!handler.getKeyManager().space){
                        shooting = false;
                    }
                    delta = 0;
                }
                break;
            }
            case SHOTGUN:{
                if (delta >= SHOTGUN_PROJECTILE_DELTA || !shooting) {
                    if (handler.getKeyManager().space) {
                        Direction secondProjectileDirection = Direction.DOWN2;
                        Direction thirdProjcetileDirection = Direction.DOWN3;
                        shooting = true;
                        switch (direction) {
                            case UP:
                                secondProjectileDirection = Direction.UP2;
                                thirdProjcetileDirection = Direction.UP3;
                                break;
                            case DOWN:
                                secondProjectileDirection = Direction.DOWN2;
                                thirdProjcetileDirection = Direction.DOWN3;
                                break;
                            case LEFT:
                                secondProjectileDirection = Direction.LEFT2;
                                thirdProjcetileDirection = Direction.LEFT3;
                                break;
                            case RIGHT:
                                secondProjectileDirection = Direction.RIGHT2;
                                thirdProjcetileDirection = Direction.RIGHT3;
                                break;
                            case UP_LEFT:
                                secondProjectileDirection = Direction.UP_LEFT2;
                                thirdProjcetileDirection = Direction.UP_LEFT3;
                                break;
                            case UP_RIGHT:
                                secondProjectileDirection = Direction.UP_RIGHT2;
                                thirdProjcetileDirection = Direction.UP_RIGHT3;
                                break;
                            case DOWN_LEFT:
                                secondProjectileDirection = Direction.DOWN_LEFT2;
                                thirdProjcetileDirection = Direction.DOWN_LEFT3;
                                break;
                            case DOWN_RIGHT:
                                secondProjectileDirection = Direction.DOWN_RIGHT2;
                                thirdProjcetileDirection = Direction.DOWN_RIGHT3;
                                break;
                        }
                        Projectile newProjectile = new ShotgunProjectile(handler, posX, posY, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, direction, this);
                        Projectile newProjectile2 = new ShotgunProjectile(handler, posX, posY, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, secondProjectileDirection, this);
                        Projectile newProjectile3 = new ShotgunProjectile(handler, posX, posY, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, thirdProjcetileDirection, this);
                        projectiles.add(newProjectile);
                        projectiles.add(newProjectile2);
                        projectiles.add(newProjectile3);
                        remainingProjectiles--;
                        if (remainingProjectiles == 0) {
                            remainingProjectiles = DEFAULT_PROJECTILES_REMINING;
                            typeOfProjectile = TypeOfProjectile.DEFAULT;
                        }
                    }
                    if (!handler.getKeyManager().space) {
                        shooting = false;
                    }
                    delta = 0;
                }
                break;
            }
        }

        //buying item Q
        if (!handler.getKeyManager().q) {
            lastTimeItemQ = System.currentTimeMillis();
            deltaItemQ = 0;
            ignoreBuyShotgun = false;
        }
        if (!ignoreBuyShotgun){
            if (handler.getKeyManager().q){
                nowItemQ = System.currentTimeMillis();
                deltaItemQ += nowItemQ - lastTimeItemQ;
                lastTimeItemQ = nowItemQ;
                if (deltaItemQ >= CONFIRMATION_TIME){
                    if (numberOfCoins >= ShotgunProjectile.PRICE){
                        typeOfProjectile = TypeOfProjectile.SHOTGUN;
                        deltaItemQ = 0;
                        numberOfCoins = numberOfCoins - ShotgunProjectile.PRICE;
                        remainingProjectiles = ShotgunProjectile.MAX_NUMBER_OF_PROJECTILES;
                    }else {
                        System.out.println("not enough money");
                        notEnoughMoney = true;
                        deltaBanner = 0;
                        lastTimeBanner = System.currentTimeMillis();
                        deltaItemQ = 0;
                    }
                    ignoreBuyShotgun = true;
                }
            }
        }

        //buying item W
        if (!handler.getKeyManager().w) {
            lastTimeItemW = System.currentTimeMillis();
            deltaItemW = 0;
            ignoreBuyRifle = false;
        }

        if (!ignoreBuyRifle){
            if (handler.getKeyManager().w){
                nowItemW = System.currentTimeMillis();
                deltaItemW += nowItemW - lastTimeItemW;
                lastTimeItemW = nowItemW;
                if (deltaItemW >= CONFIRMATION_TIME){
                    if (numberOfCoins >= RifleProjectile.PRICE){
                        typeOfProjectile = TypeOfProjectile.RIFLE;
                        deltaItemW = 0;
                        numberOfCoins = numberOfCoins - RifleProjectile.PRICE;
                        remainingProjectiles = RifleProjectile.MAX_NUMBER_OF_PROJECTILES;
                    }else {
                        System.out.println("not enough money");
                        notEnoughMoney = true;
                        deltaBanner = 0;
                        lastTimeBanner = System.currentTimeMillis();
                        deltaItemW = 0;
                    }
                    ignoreBuyRifle = true;
                }
            }
        }

        //buying item E
        if (!handler.getKeyManager().e) {
            lastTimeItemE = System.currentTimeMillis();
            deltaItemE = 0;
            ignoreBuyRPG = false;
        }

        if (!ignoreBuyRPG){
            if (handler.getKeyManager().e){
                nowItemE = System.currentTimeMillis();
                deltaItemE += nowItemE - lastTimeItemE;
                lastTimeItemE = nowItemE;
                if (deltaItemE >= CONFIRMATION_TIME){
                    if (numberOfCoins >= RPGProjectile.PRICE){
                        typeOfProjectile = TypeOfProjectile.RPG;
                        deltaItemE = 0;
                        numberOfCoins = numberOfCoins - RPGProjectile.PRICE;
                        remainingProjectiles = RPGProjectile.MAX_NUMBER_OF_PROJECTILES;
                    }else {
                        System.out.println("not enough money");
                        notEnoughMoney = true;
                        deltaBanner = 0;
                        lastTimeBanner = System.currentTimeMillis();
                        deltaItemE = 0;
                    }
                    ignoreBuyRPG = true;
                }
            }
        }

        //buying item T
        if (!handler.getKeyManager().t) {
            lastTimeItemT = System.currentTimeMillis();
            deltaItemT = 0;
            if (activeDog)
                ignoreBuyDog = true;
            else
                ignoreBuyDog = false;
        }
        if (!ignoreBuyDog){
            if (handler.getKeyManager().t){
                nowItemT = System.currentTimeMillis();
                deltaItemT += nowItemT - lastTimeItemT;
                lastTimeItemT = nowItemT;
                if (deltaItemT >= CONFIRMATION_TIME){
                    if (numberOfCoins >= DOG_PRICE){
                        activeDog = true;
                        handler.getWorld().getEntityManager().addEntity(new Dog(handler, 200, 200));
                        //entityManager.addEntity(spawner1, 0);
                        deltaItemT = 0;
                        numberOfCoins = numberOfCoins - DOG_PRICE;
                        System.out.println("bought dog");
                    }else {
                        System.out.println("not enough money");
                        notEnoughMoney = true;
                        deltaBanner = 0;
                        lastTimeBanner = System.currentTimeMillis();
                        deltaItemT = 0;
                    }
                    ignoreBuyDog = true;
                }
            }
        }

        //ability
        if (handler.getKeyManager().a && abilityReady){
            specialAbility();
            abilityReady = false;
            timerAbility = 0;
            lastTimeAbility = System.currentTimeMillis();
        }
    }

    public void specialAbility(){
        //UP
        Projectile newProjectile = new DefaultProjectile(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, Direction.UP, this);
        projectiles.add(newProjectile);
        //DOWN
        Projectile newProjectile2 = new DefaultProjectile(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, Direction.DOWN, this);
        projectiles.add(newProjectile2);
        //LEFT
        Projectile newProjectile3 = new DefaultProjectile(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, Direction.LEFT, this);
        projectiles.add(newProjectile3);
        //RIGHT
        Projectile newProjectile4 = new DefaultProjectile(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, Direction.RIGHT, this);
        projectiles.add(newProjectile4);
        //UP LEFT
        Projectile newProjectile5 = new DefaultProjectile(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, Direction.UP_LEFT, this);
        projectiles.add(newProjectile5);
        //UP RIGHT
        Projectile newProjectile6 = new DefaultProjectile(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, Direction.UP_RIGHT, this);
        projectiles.add(newProjectile6);
        //DOWN LEFT
        Projectile newProjectile7 = new DefaultProjectile(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, Direction.DOWN_LEFT, this);
        projectiles.add(newProjectile7);
        //DOWN RIGHT
        Projectile newProjectile8 = new DefaultProjectile(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, Direction.DOWN_RIGHT, this);
        projectiles.add(newProjectile8);
        //System.out.println("ability used");
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

        //respawning
        if (animation_respawn.getCurrentFrame() != null){
            g.drawImage(animation_respawn.getCurrentFrame(),(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }else{
            g.drawImage(getCurrentAnimationFrame(),(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            if (immortal)
                g.drawImage(Assets.immortalBubble,(int) (x - 5 - handler.getGameCamera().getxOffset()),
                        (int) (y - 5 - handler.getGameCamera().getyOffset()),
                        width + 10, height + 10, null);
        }

        Iterator itr = projectiles.iterator();
        while (itr.hasNext()){
            Projectile p = (Projectile) itr.next();
            //p.render(g);
            if (p.isDestroyed())
                itr.remove();
            else
                p.render(g);
        }

        renderNumberOflives(g);
        renderNumberOfCoins(g);


        //g.drawImage(Assets.store_background, handler.getGame().getWidth() - Tile.TILEWIDTH, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
        //g.drawImage(Assets.store_background, handler.getGame().getWidth() - Tile.TILEWIDTH * 2, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
        //g.drawImage(Assets.store_background, handler.getGame().getWidth() - Tile.TILEWIDTH * 3, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);

        if (numberOfCoins >= RPGProjectile.PRICE)
            g.drawImage(Assets.store_rpg_green, handler.getGame().getWidth() - Tile.TILEWIDTH * 2, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
        else
            g.drawImage(Assets.store_rpg_red, handler.getGame().getWidth() - Tile.TILEWIDTH * 2, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);

        if (numberOfCoins >= RifleProjectile.PRICE)
            g.drawImage(Assets.store_rifle_green, handler.getGame().getWidth() - Tile.TILEWIDTH * 4, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
        else
            g.drawImage(Assets.store_rifle_red, handler.getGame().getWidth() - Tile.TILEWIDTH * 4, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
        if (numberOfCoins >= ShotgunProjectile.PRICE)
            g.drawImage(Assets.store_shotgun_green, handler.getGame().getWidth() - Tile.TILEWIDTH * 6, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
        else
            g.drawImage(Assets.store_shotgun_red, handler.getGame().getWidth() - Tile.TILEWIDTH * 6, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);

        //loading bar for buying item E
        long rest = deltaItemE;
        for (int i = 0; i < 32; i++){
            rest -= (int) (CONFIRMATION_TIME / 32);
            if (rest <= (int) (CONFIRMATION_TIME / 32)){
                g.drawImage(Assets.loadingBarArray[31 - i], handler.getGame().getWidth() - Tile.TILEWIDTH, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
                break;
            }
        }
        //loading bar for buying item W
        rest = deltaItemW;
        for (int i = 0; i < 32; i++){
            rest -= (int) (CONFIRMATION_TIME / 32);
            if (rest <= (int) (CONFIRMATION_TIME / 32)){
                g.drawImage(Assets.loadingBarArray[31 - i], handler.getGame().getWidth() - Tile.TILEWIDTH * 3, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
                break;
            }
        }
        //loading bar for buying item Q
        rest = deltaItemQ;
        for (int i = 0; i < 32; i++){
            rest -= (int) (CONFIRMATION_TIME / 32);
            if (rest <= (int) (CONFIRMATION_TIME / 32)){
                g.drawImage(Assets.loadingBarArray[31 - i], handler.getGame().getWidth() - Tile.TILEWIDTH * 5, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
                break;
            }
        }

        //rendering bar for ability
        if (abilityReady)
            g.drawImage(Assets.loading_bar_green_full, Tile.TILEWIDTH * 5, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
        else {
            rest = timerAbility;
            for (int i = 0; i < 32; i++){
                rest -= (int) (ABILITYCHARGETIME / 32);
                if (rest <= (int) (ABILITYCHARGETIME / 32)){
                    g.drawImage(Assets.loadingBarArray[31 - i], Tile.TILEWIDTH * 5, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
                    break;
                }
            }
        }


        //renderinng type of projectile and progress of spending it
        renderTypeOfProjectile(g);
        //rendering banner
        if (notEnoughMoney){
            renderNotEnoughMoneyBanner(g);
        }
        //boosts
        renderBoostBar(g);
        //collision box
        //if I want to see collison box
        //g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
                //(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
    }

    public void renderBoostBar(Graphics g){
        if (typeOfBoost != TypeOfBoost.NO_ACTIVE_BOOST){
            long rest = timerBoost;
            for (int i = 0; i < 32; i++){
                rest -= (int) (durationOfBoost / 32);
                if (rest <= (int) (durationOfBoost / 32)){
                    g.drawImage(Assets.loadingBarArray[i], Tile.TILEWIDTH * 6, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
                    break;
                }
            }
        }
        switch (typeOfBoost){
            case SPEED:{
                g.drawImage(Assets.speed_boost, Tile.TILEWIDTH * 6, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
                break;
            }
            case IMMORTALITY:{
                g.drawImage(Assets.immortality_boost, Tile.TILEWIDTH * 6, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
                break;
            }
            case DOUBLE_COINS:{
                g.drawImage(Assets.double_coins_boost, Tile.TILEWIDTH * 6, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
                break;
            }
            case NO_ACTIVE_BOOST:{
                g.drawImage(Assets.no_active_boost, Tile.TILEWIDTH * 6, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
                break;
            }
        }

    }

    //if I'll decide to have more banners I'l create class for that
    public void renderNotEnoughMoneyBanner(Graphics g){
        nowBanner = System.currentTimeMillis();
        deltaBanner += nowBanner - lastTimeBanner;
        lastTimeBanner = nowBanner;
        if (deltaBanner <= 3000){
            //drawing banner
            g.drawImage(Assets.banner_not_enough_money, handler.getGame().getWidth() / 2 - (handler.getGame().getWidth()/5)/2,
                    handler.getGame().getHeight() / 4 - (handler.getGame().getHeight()/18)/2,
                    handler.getGame().getWidth()/5,
                    handler.getGame().getHeight()/18, null);
        }else {
            notEnoughMoney = false;
        }
    }

    public void renderTypeOfProjectile(Graphics g){
        g.drawImage(Assets.bubble_background1, 244, handler.getGame().getHeight() - NUMBER_HEIGHT - 30, NUMBER_WIDTH * 2, NUMBER_HEIGHT + 7, null);
        //g.drawImage(Assets.projectile_right, 240, handler.getGame().getHeight() - NUMBER_HEIGHT - 45, NUMBER_WIDTH * 2 + 10, NUMBER_HEIGHT * 2 + 10, null);
        //g.drawImage(Assets.loadingBar, 244, handler.getGame().getHeight() - NUMBER_HEIGHT - 35, NUMBER_WIDTH + 35, NUMBER_HEIGHT * 2 , null);
        switch (typeOfProjectile){
            case RPG:{
                g.drawImage(Assets.rpg_projectile_right, 245, handler.getGame().getHeight() - NUMBER_HEIGHT - 45, NUMBER_WIDTH * 2, NUMBER_HEIGHT * 2, null);
                long rest = remainingProjectiles;
                for (int i = 0; i < 32; i++){
                    rest -= (int) (RPGProjectile.MAX_NUMBER_OF_PROJECTILES / 32);
                    if (rest <= (int) (RPGProjectile.MAX_NUMBER_OF_PROJECTILES / 32)){
                        g.drawImage(Assets.loadingBarArray[31 - i], 244, handler.getGame().getHeight() - NUMBER_HEIGHT - 35 , null);
                        break;
                    }
                }
                if (rest > (int) (RPGProjectile.MAX_NUMBER_OF_PROJECTILES / 32)){
                    g.drawImage(Assets.loadingBarArray[0], 244, handler.getGame().getHeight() - NUMBER_HEIGHT - 35 , null);
                }
                break;
            }
            case DEFAULT:{
                g.drawImage(Assets.projectile_right, 235, handler.getGame().getHeight() - NUMBER_HEIGHT - 50, NUMBER_WIDTH * 2 + 20, NUMBER_HEIGHT * 2 + 20, null);
                g.drawImage(Assets.loadingBarArray[0], 244, handler.getGame().getHeight() - NUMBER_HEIGHT - 35 , null);
                break;
            }
            case RIFLE:{
                g.drawImage(Assets.rifle_projectile_right, 235, handler.getGame().getHeight() - NUMBER_HEIGHT - 45, NUMBER_WIDTH * 2 + 10, NUMBER_HEIGHT * 2 + 10, null);
                long rest = remainingProjectiles;
                for (int i = 0; i < 32; i++){
                    rest -= (int) (RifleProjectile.MAX_NUMBER_OF_PROJECTILES / 32);
                    if (rest <= (int) (RifleProjectile.MAX_NUMBER_OF_PROJECTILES / 32)){
                        g.drawImage(Assets.loadingBarArray[31 - i], 244, handler.getGame().getHeight() - NUMBER_HEIGHT - 35 , null);
                        break;
                    }
                }
                if (rest > (int) (RifleProjectile.MAX_NUMBER_OF_PROJECTILES / 32)){
                    g.drawImage(Assets.loadingBarArray[0], 244, handler.getGame().getHeight() - NUMBER_HEIGHT - 35 , null);
                }
                break;
            }
            case SHOTGUN:{
                g.drawImage(Assets.shotgun_image, 248, handler.getGame().getHeight() - NUMBER_HEIGHT - 40, NUMBER_WIDTH * 2 - 10 , NUMBER_HEIGHT * 2 - 10, null);
                long rest = remainingProjectiles;
                for (int i = 0; i < 32; i++){
                    rest -= (int) (ShotgunProjectile.MAX_NUMBER_OF_PROJECTILES / 32);
                    if (rest <= (int) (ShotgunProjectile.MAX_NUMBER_OF_PROJECTILES / 32)){
                        g.drawImage(Assets.loadingBarArray[31 - i], 244, handler.getGame().getHeight() - NUMBER_HEIGHT - 35 , null);
                        break;
                    }
                }
                if (rest > (int) (ShotgunProjectile.MAX_NUMBER_OF_PROJECTILES / 32)){
                    g.drawImage(Assets.loadingBarArray[0], 244, handler.getGame().getHeight() - NUMBER_HEIGHT - 35 , null);
                }
                break;
            }
        }
    }

    public BufferedImage getNumberTexture(int number){
        switch (number){
            case 0:
                return Assets.zero;
            case 1:
                return Assets.one;
            case 2:
                return Assets.two;
            case 3:
                return Assets.three;
            case 4:
                return Assets.four;
            case 5:
                return Assets.five;
            case 6:
                return Assets.six;
            case 7:
                return Assets.seven;
            case 8:
                return Assets.eight;
            case 9:
                return Assets.nine;
        }
        return Assets.zero;
    }

    public void renderNumberOflives(Graphics g){
        int secondDigitLives = numberOfLives % 10;
        int firstDigitLives = (int) (((numberOfLives - secondDigitLives) % 100) / 10);
        g.drawImage(getNumberTexture(firstDigitLives), 40, handler.getGame().getHeight() - NUMBER_HEIGHT - 15,NUMBER_WIDTH, NUMBER_HEIGHT, null);
        g.drawImage(getNumberTexture(secondDigitLives), 64, handler.getGame().getHeight() - NUMBER_HEIGHT - 15, NUMBER_WIDTH, NUMBER_HEIGHT, null);
    }

    public void renderNumberOfCoins(Graphics g){
        int fourthDigitCoins = numberOfCoins % 10;
        int thirdDigitCoins = (int) (((numberOfCoins - fourthDigitCoins) % 100) / 10);
        int secondDigitCoins = (int) (((numberOfCoins - fourthDigitCoins - (thirdDigitCoins * 10)) % 1000) / 100);
        int firstDigitCoins = (int) (((numberOfCoins - fourthDigitCoins - (thirdDigitCoins * 10) - (secondDigitCoins * 100)) % 10000) / 1000);
        g.drawImage(getNumberTexture(firstDigitCoins), 140, handler.getGame().getHeight() - NUMBER_HEIGHT - 15,NUMBER_WIDTH, NUMBER_HEIGHT, null);
        g.drawImage(getNumberTexture(secondDigitCoins), 164, handler.getGame().getHeight() - NUMBER_HEIGHT - 15, NUMBER_WIDTH, NUMBER_HEIGHT, null);
        g.drawImage(getNumberTexture(thirdDigitCoins), 188, handler.getGame().getHeight() - NUMBER_HEIGHT - 15,NUMBER_WIDTH, NUMBER_HEIGHT, null);
        g.drawImage(getNumberTexture(fourthDigitCoins), 212, handler.getGame().getHeight() - NUMBER_HEIGHT - 15, NUMBER_WIDTH, NUMBER_HEIGHT, null);
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
        if (typeOfBoost == TypeOfBoost.DOUBLE_COINS)
            numberOfCoins++;
        numberOfCoins++;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public void setTypeOfBoost(TypeOfBoost typeOfBoost) {
        this.typeOfBoost = typeOfBoost;
        switch (typeOfBoost){
            case DOUBLE_COINS:
                durationOfBoost = DoubleCoinsBoost.BOOST_DURATION;
                break;
            case IMMORTALITY:
                durationOfBoost = ImmortalityBoost.BOOST_DURATION;
                break;
            case SPEED:
                durationOfBoost = SpeedBoost.BOOST_DURATION;
                break;
        }
        timerBoost = 0;
        lastTimeBoost = System.currentTimeMillis();
    }
}
