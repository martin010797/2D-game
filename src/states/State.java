package states;

import audio.AudioPlayer;
import game.Game;
import game.Handler;

import java.awt.*;

public abstract class State {

    //State manager part
    private static State currentState = null;
    protected static AudioPlayer backgroundMusic;
    protected static AudioPlayer menuMusic;
    protected static AudioPlayer level1Music;

    public State(Handler pHandler){
        handler = pHandler;
        if(menuMusic == null){
            menuMusic = new AudioPlayer("/music/menu.mp3");
        }
        if(level1Music == null){
            level1Music = new AudioPlayer("/music/level_1.mp3");
            level1Music.lowerVolume();
        }
        //"/textures/testwick.png"
        //backgroundMusic.play();
    }

    public static void setState(State pState){
        currentState = pState;
        //backgroundMusic.play();
        if (pState instanceof MenuState){
            if (backgroundMusic != null){
                backgroundMusic.stop();
            }
            backgroundMusic = menuMusic;
            //backgroundMusic.play();
            backgroundMusic.loop();
        }else if (pState instanceof GameState){
            if (backgroundMusic != null){
                backgroundMusic.stop();
            }
            backgroundMusic = level1Music;
            //backgroundMusic.play();
            backgroundMusic.loop();
        }

    }


    public static State getState(){
        return currentState;
    }

    protected Handler handler;

    //abstract part
    public abstract void tick();
    public abstract void render(Graphics g);
}
