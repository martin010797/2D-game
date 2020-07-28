package game;

import audio.Sounds;
import creatures.Player;
import display.Display;
import entities.EntityManager;
import graphics.Assets;
import graphics.GameCamera;
import input.KeyManager;
import input.MouseManager;
import states.FirstLevelState;
import states.MenuState;
import states.SecondLevelState;
import states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    private Display display;
    private Thread thread;
    private boolean running = false;
    private BufferStrategy bs;
    private Graphics g;

    //private BufferedImage testImage;
    //private SpriteSheet sheet;

    private int width;
    private int height;
    public String title;

    //States
    public State firstLevelState;
    public State secondLevelState;
    public State menuState;

    //Input
    private KeyManager keyManager;
    private MouseManager mouseManager;

    //Camera
    private GameCamera gameCamera;

    //Handler
    private Handler handler;

    public static Level level;
    private EntityManager entityManager;

    public Game(String pTitle, int pWidth, int pHeight){
        width = pWidth;
        height = pHeight;
        title = pTitle;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        Sounds.init();
        level = Level.FIRST_LEVEL;
    }

    //initialization of graphics
    private void init(){
        display = new Display(title,width,height);
        //keyboard manager
        display.getJFrame().addKeyListener(keyManager);
        //mouse manager
        display.getJFrame().addMouseListener(mouseManager);
        display.getJFrame().addMouseMotionListener(mouseManager);
        //these two should be added because without it, it will behave glitchy
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        //testImage = ImageLoader.loadImage("/textures/image.png");
        //sheet = new SpriteSheet(testImage);
        //initialization all items from sprite sheet
        Assets.init();

        handler = new Handler(this);
        gameCamera = new GameCamera(handler, 0,0);

        entityManager = new EntityManager(handler, new Player(handler, 200, 200));
        firstLevelState = new FirstLevelState(handler, entityManager);
        secondLevelState = new SecondLevelState(handler, entityManager);
        menuState = new MenuState(handler);

        State.setState(menuState);
        ((FirstLevelState)firstLevelState).setWorld();
    }

    private void tick(){
        keyManager.tick();
        if(State.getState() != null){
            //changing levels
            switch (level){
                case FIRST_LEVEL:{
                    if(State.getState() instanceof FirstLevelState){
                        if(((FirstLevelState) (State.getState())).isDefeated() == true){
                            level = Level.SECOND_LEVEL;
                            ((SecondLevelState)secondLevelState).setWorld();
                            State.setState(secondLevelState);
                        }

                    }
                    break;
                }
                case SECOND_LEVEL:{
                    break;
                }
            }
            //((SecondLevelState) (State.getState())).isDefeated();
            State.getState().tick();
        }
    }

    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();

        //clear the screen
        //what part of screen we want to clear
        g.clearRect(0,0, width, height);

        //start of drawing


        //g.drawRect(10,50,50,70);
        /*g.fillRect(60,50,50,70);
        //everything what is drawn after this is with red color
        g.setColor(Color.red);
        g.fillRect(10,50,50,70);
        g.fill3DRect(120,50,50,70,true);
        g.setColor(Color.ORANGE);
        g.fill3DRect(-10,50,50,70,true);*/

        //drawing image from folder
        //g.drawImage(testImage,150,20,null);

        //g.fillRect(0,0,width,height);

        //g.drawImage(Assets.player,0,0,null);

        //g.drawImage(sheet.crop(0,0,32, 32),5,5,null);
        //g.drawImage(sheet.crop(32,32,32, 32),35,5,null);


        /*
        for (int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++){
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)){
                    g.drawImage(Assets.grass, i*32,j*32,null);
                }else{
                    g.drawImage(Assets.water, i*32,j*32,null);
                }
            }
        }
        for (int i = 15; i < 20; i++){
            for (int j = 0; j < 15; j++){
                g.drawImage(Assets.water, i*32,j*32,null);
            }
        }
        g.drawImage(Assets.player, 5,5,null);
        g.drawImage(Assets.enemy, 25,25,null);*/

        if(State.getState() != null){
            State.getState().render(g);
        }

        //end of drawing

        //showing what is draw in buffer
        bs.show();
        g.dispose();
    }

    //@Override
    public void run() {
        init();

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        //game loop
        while (running){
            now = System.nanoTime();
            delta += (now - lastTime) /timePerTick;
            lastTime = now;

            if (delta >= 1){
                tick();
                render();
                delta--;
            }

        }

        stop();
    }

    //starting thread
    //always when you start or stop thread have to use synchronized
    public synchronized void start(){
        if (running)
            return;
        running = true;
        //starting thread for this class(game)
        thread = new Thread(this);
        //this will call run method
        thread.start();
    }
    //stopping thread
    public synchronized void stop(){
        if (!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public KeyManager getKeyManager(){
        return keyManager;
    }

    public MouseManager getMouseManager(){
        return mouseManager;
    }

    public GameCamera getGameCamera(){
        return gameCamera;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight(){
        return height;
    }
}
