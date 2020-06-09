package states;

import game.Game;
import game.Handler;
import graphics.Assets;

import java.awt.*;

public class MenuState extends State {
    public MenuState(Handler pHandler){
        super(pHandler);
    }
    @Override
    public void tick() {
        if (handler.getMouseManager().isLeftPressed() || handler.getMouseManager().isRightPressed())
            State.setState(handler.getGame().gameState);
        //System.out.println(handler.getMouseManager().getMouseX() + " " + handler.getMouseManager().getMouseY());
    }

    @Override
    public void render(Graphics g) {
        //just random things... change later when creating menu
        g.setColor(Color.red);
        g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8,8);
    }
}
