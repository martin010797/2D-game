package worlds;

import creatures.BasicEnemy;
import creatures.BulletproofEnemy;
import creatures.Enemy;
import creatures.Player;
import entities.Coin;
import entities.Entity;
import entities.EntityManager;
import entities.Projectile;
import game.Handler;
import graphics.Assets;
import statics.DoubleCoinsBoost;
import statics.ImmortalityBoost;
import statics.Spawner;
import statics.SpeedBoost;
import tiles.Tile;

import java.awt.*;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class SecondWorld extends World {

    //TODO implement adding two types of enemies
    private static final int NUMBEROFENEMIES = 15, MAXNUMBEROFENEMIESONSCREEN = 20, NUMBER_OF_BASIC_ENEMIES = 20,
            NUMBER_OF_BULLETPROOF_ENEMIES = 10;

    public SecondWorld(Handler pHandler, String pPath, EntityManager pEntityManager) {
        super(pHandler, pPath, pEntityManager);
    }

    private boolean elapsedOneSecond(){
        now = System.currentTimeMillis();
        delta += now - lastTime;
        lastTime = now;
        if (delta >= ONESECOND)
            return true;
        else
            return false;
    }

    @Override
    public void tick() {
        //entityManager.tick();
        endLevelAnimation.tick();

        timerStartLevel += System.currentTimeMillis() - lastTimeStartLevel;
        lastTimeStartLevel = System.currentTimeMillis();
        if (timerStartLevel >= WAIT_TIME_BEFORE_START_OF_LEVEL){
            entityManager.tick();
            //level cleared
            if (NUMBEROFENEMIES == defeatedEnemies){
                //black screen for end level
                if (!blackscreen){
                    lastTimeEndLevel = System.currentTimeMillis();
                    timerEndLevel = 0;
                    blackscreen = true;
                }
                timerEndLevel += System.currentTimeMillis() - lastTimeEndLevel;
                lastTimeEndLevel = System.currentTimeMillis();

                if (timerEndLevel >= BLACK_SCREEN_LENGTH){
                    //clearing entities except player and spawner
                    Iterator itr = handler.getWorld().getEntityManager().getEntities().iterator();
                    while (itr.hasNext()){
                        Entity e = (Entity) itr.next();
                        if (e instanceof Coin){
                            handler.getWorld().getEntityManager().getCoins().remove(e);
                        }
                        if (e instanceof Enemy){
                            handler.getWorld().getEntityManager().getEnemies().remove(e);
                        }
                        if (!(e instanceof Player) && !(e instanceof Spawner))
                            itr.remove();
                    }
                    itr = entityManager.getPlayer().getProjectiles().iterator();
                    while (itr.hasNext()){
                        Projectile p = (Projectile) itr.next();
                        itr.remove();
                    }
                    /*entityManager.getSpawners().get(0).setX(XFIRSTSPAWNER_SECOND_WORLD);
                    entityManager.getSpawners().get(0).setY(YFIRSTSPAWNER_SECOND_WORLD);
                    entityManager.getSpawners().get(1).setX(XSECONDSPAWNER_SECOND_WORLD);
                    entityManager.getSpawners().get(1).setY(YSECONDSPAWNER_SECOND_WORLD);
                    entityManager.getSpawners().get(2).setX(XTHIRDSPAWNER_SECOND_WORLD);
                    entityManager.getSpawners().get(2).setY(YTHIRDSPAWNER_SECOND_WORLD);
                    entityManager.getSpawners().get(3).setX(XFOURTHSPAWNER_SECOND_WORLD);
                    entityManager.getSpawners().get(3).setY(YFOURTHSPAWNER_SECOND_WORLD);*/

                    //maybe not needed
                    handler.getWorld().setLastTimeStartLevel(System.currentTimeMillis());
                    handler.getWorld().setTimerStartLevel(0);

                    entityManager.getPlayer().respawn();
                    defeatedWorld = true;
                    System.out.println("WIN");
                }
            }
            if (entityManager.getEnemies().size() < MAXNUMBEROFENEMIESONSCREEN && (defeatedEnemies + entityManager.getEnemies().size()) < NUMBEROFENEMIES){
                //entityManager.getSpawners().get(0).setOpening(true);
                if (elapsedOneSecond()){
                    int randomSpawner = ThreadLocalRandom.current().nextInt(ZERO, NUMBEROFSPAWNERS);
                    int randomEnemy = ThreadLocalRandom.current().nextInt(ZERO, 3);
                    switch (randomSpawner){
                        case FIRSTSPAWNER:{
                            /*Enemy e = null;
                            if (randomEnemy == 0){

                                e = new BulletproofEnemy(handler, XFIRSTSPAWNER_SECOND_WORLD, YFIRSTSPAWNER_SECOND_WORLD);
                            }else {

                            }*/

                            //BasicEnemy e = new BasicEnemy(handler, XFIRSTSPAWNER_SECOND_WORLD, YFIRSTSPAWNER_SECOND_WORLD);
                            BulletproofEnemy e = new BulletproofEnemy(handler, XFIRSTSPAWNER_SECOND_WORLD, YFIRSTSPAWNER_SECOND_WORLD);
                            entityManager.getSpawners().get(FIRSTSPAWNER).setOpening(true);
                            entityManager.getEnemies().add(e);
                            entityManager.addEntity(e);
                            break;
                        }
                        case SECONDSPAWNER:{
                            //BasicEnemy e = new BasicEnemy(handler, XSECONDSPAWNER_SECOND_WORLD, YSECONDSPAWNER_SECOND_WORLD);
                            BulletproofEnemy e = new BulletproofEnemy(handler, XSECONDSPAWNER_SECOND_WORLD, YSECONDSPAWNER_SECOND_WORLD);
                            entityManager.getSpawners().get(SECONDSPAWNER).setOpening(true);
                            entityManager.getEnemies().add(e);
                            entityManager.addEntity(e);
                            break;
                        }
                        case THIRDSPAWNER:{
                            //BasicEnemy e = new BasicEnemy(handler, XTHIRDSPAWNER_SECOND_WORLD, YTHIRDSPAWNER_SECOND_WORLD);
                            BulletproofEnemy e = new BulletproofEnemy(handler, XTHIRDSPAWNER_SECOND_WORLD, YTHIRDSPAWNER_SECOND_WORLD);
                            entityManager.getSpawners().get(THIRDSPAWNER).setOpening(true);
                            entityManager.getEnemies().add(e);
                            entityManager.addEntity(e);
                            break;
                        }
                        case FOURTHSPAWNER:{
                            //BasicEnemy e = new BasicEnemy(handler, XFOURTHSPAWNER_SECOND_WORLD, YFOURTHSPAWNER_SECOND_WORLD);
                            BulletproofEnemy e = new BulletproofEnemy(handler, XFOURTHSPAWNER_SECOND_WORLD, YFOURTHSPAWNER_SECOND_WORLD);
                            entityManager.getSpawners().get(FOURTHSPAWNER).setOpening(true);
                            entityManager.getEnemies().add(e);
                            entityManager.addEntity(e);
                            break;
                        }
                    }
                    delta = 0;
                }

            }

            timerBooster += System.currentTimeMillis() - lastTimeBooster;
            lastTimeBooster = System.currentTimeMillis();
            if (timerBooster >= BOOST_TIMER_EACH){
                int randomBoost = ThreadLocalRandom.current().nextInt(ZERO, NUMBER_OF_BOOSTS);
                int randomX = ThreadLocalRandom.current().nextInt((int) (Tile.TILEWIDTH * 2) , (int) (width * Tile.TILEWIDTH - Tile.TILEWIDTH * 2 + 1));
                int randomY = ThreadLocalRandom.current().nextInt((int) (Tile.TILEHEIGHT * 2), (int) (height * Tile.TILEWIDTH - Tile.TILEHEIGHT * 2 + 1));
                switch (randomBoost){
                    case FIRST_BOOST:{
                        DoubleCoinsBoost boost = new DoubleCoinsBoost(handler, randomX, randomY, Tile.TILEWIDTH, Tile.TILEHEIGHT);
                        entityManager.addEntity(boost);
                        break;
                    }
                    case SECOND_BOOST:{
                        ImmortalityBoost boost = new ImmortalityBoost(handler, randomX, randomY, Tile.TILEWIDTH, Tile.TILEHEIGHT);
                        entityManager.addEntity(boost);
                        break;
                    }
                    case THIRD_BOOST:{
                        SpeedBoost boost = new SpeedBoost(handler, randomX, randomY, Tile.TILEWIDTH, Tile.TILEHEIGHT);
                        entityManager.addEntity(boost);
                        break;
                    }
                }
                timerBooster = 0;
            }

            //timerBooster = 0;
            lastTimeBooster = System.currentTimeMillis();
        }
    }

    @Override
    public void render(Graphics g) {
        //render just object which we can see on screen
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH );
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
        int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
        int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getTile(x,y).render(g,(int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
            }
        }
        for (int i = 0; i < 7; i++){
            g.drawImage(Assets.player_stats_background, i * Tile.TILEWIDTH, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
        }
        g.drawImage(Assets.bubble_background1, 0, handler.getGame().getHeight() - Tile.TILEHEIGHT, Tile.TILEWIDTH - 20 ,Tile.TILEHEIGHT , null);
        g.drawImage(Assets.static_player_down, - 7, handler.getGame().getHeight() - Tile.TILEHEIGHT + 3,Tile.TILEWIDTH - 7 ,Tile.TILEHEIGHT - 7 , null);
        g.drawImage(Assets.bubble_background1, 100, handler.getGame().getHeight() - Tile.TILEHEIGHT + 3,Tile.TILEWIDTH - 22 ,Tile.TILEHEIGHT - 7 , null);
        g.drawImage(Assets.coin, 92, handler.getGame().getHeight() - Tile.TILEHEIGHT + 3,Tile.TILEWIDTH - 7 ,Tile.TILEHEIGHT - 7 , null);
        g.drawImage(Assets.player_ability, 5 * Tile.TILEWIDTH, handler.getGame().getHeight() - Tile.TILEHEIGHT, null);
        //entities
        entityManager.render(g);

        //banner for level end
        if (timerEndLevel <= BLACK_SCREEN_LENGTH && blackscreen){
            renderEndLevelBanner(g);
        }
    }


}
