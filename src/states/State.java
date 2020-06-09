package states;

import game.Game;
import game.Handler;

import java.awt.*;

public abstract class State {

    //State manager part
    private static State currentState = null;

    public static void setState(State pState){
        currentState = pState;
    }

    public static State getState(){
        return currentState;
    }

    protected Handler handler;

    public State(Handler pHandler){
        handler = pHandler;
    }

    //abstract part
    public abstract void tick();
    public abstract void render(Graphics g);
}
