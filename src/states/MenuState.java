package states;

import game.Game;
import game.Handler;
import graphics.Assets;
import statics.StaticEntity;
import ui.ClickListener;
import ui.UIImageButton;
import ui.UIManager;

import java.awt.*;

public class MenuState extends State {

    private UIManager uiManager;

    public MenuState(Handler pHandler){

        super(pHandler);
        uiManager = new UIManager(pHandler);
        handler.getMouseManager().setUiManager(uiManager);

        uiManager.addObject(new UIImageButton(200, 200, 128, 64, Assets.btn_start, new ClickListener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUiManager(null);
                State.setState(handler.getGame().gameState);
            }
        }));
    }

    @Override
    public void tick() {
        //if (handler.getMouseManager().isLeftPressed() || handler.getMouseManager().isRightPressed())
            //State.setState(handler.getGame().gameState);
        //System.out.println(handler.getMouseManager().getMouseX() + " " + handler.getMouseManager().getMouseY());
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        //g.setColor(Color.red);
        //g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8,8);
        uiManager.render(g);
        /*g.setColor(Color.blue);
        g.setFont(new Font("Serif", Font.BOLD, 12));
        g.drawString("testing string", 150, 150);*/
    }
}
