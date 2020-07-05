package entities;

import creatures.Enemy;
import creatures.Player;
import game.Handler;
import statics.Boost;
import statics.Spawner;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class EntityManager {
    private Handler handler;
    private Player player;
    private ArrayList<Entity> entities;
    private ArrayList<Enemy> enemies;
    private ArrayList<Spawner> spawners;
    private ArrayList<Coin> coins;


    private Comparator<Entity> renderSorter = new Comparator<Entity>() {
        @Override
        public int compare(Entity a, Entity b) {
            if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
                return -1;
            return 1;
        }
    };

    public EntityManager(Handler pHandler, Player pPlayer){
        handler = pHandler;
        player = pPlayer;
        entities = new ArrayList<Entity>();
        enemies = new ArrayList<Enemy>();
        spawners = new ArrayList<Spawner>();
        coins = new ArrayList<Coin>();
        addEntity(player);
    }

    public void tick(){
        for (int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.tick();
        }
        //entities.sort(renderSorter);
        Enemy killedEnemy = null;
        for (Enemy e: enemies) {
            if (e.isDead())
                killedEnemy = e;
        }
        if (killedEnemy != null){
            Coin c = new Coin(handler, killedEnemy.x, killedEnemy.y, killedEnemy.width, killedEnemy.height);
            addEntity(c);
            //added
            coins.add(c);
            enemies.remove(killedEnemy);
            entities.remove(killedEnemy);
            handler.getWorld().addDefeatedEnemy();
        }
        Iterator itr = entities.iterator();
        while (itr.hasNext()){
            Entity e = (Entity) itr.next();
            if (e instanceof Coin){
                if(((Coin) e).isDestroyed()){
                    //added because of dog
                    coins.remove(e);
                    itr.remove();
                }
            }
            if (e instanceof Boost){
                if (((Boost) e).isDestroyed())
                    itr.remove();
            }
        }
    }

    public void render(Graphics g){
        for (Entity e : entities){
            e.render(g);
        }
    }

    public void addEntity(Entity e){
        entities.add(e);
    }

    public void addEntity(Entity e, int index){
        entities.add(index, e);
    }

    //getters and setter
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Spawner> getSpawners() {
        return spawners;
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

}
