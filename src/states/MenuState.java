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
    private int button_width = 262, button_height = 95;

    public MenuState(Handler pHandler){

        super(pHandler);
        uiManager = new UIManager(pHandler);
        handler.getMouseManager().setUiManager(uiManager);

        uiManager.addObject(new UIImageButton(500, 350, button_width, button_height, Assets.btn_start, new ClickListener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUiManager(null);
                State.setState(handler.getGame().gameState);
            }
        }));

        uiManager.addObject(new UIImageButton(500, 450, button_width, button_height, Assets.btn_tutorial, new ClickListener() {
            @Override
            public void onClick() {
                //change later
                //handler.getMouseManager().setUiManager(null);
                //State.setState(handler.getGame().gameState);
            }
        }));

        uiManager.addObject(new UIImageButton(500, 550, button_width, button_height, Assets.btn_exit, new ClickListener() {
            @Override
            public void onClick() {
                System.exit(0);
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
        g.drawImage(Assets.menu_backgrounnd,0,0, null);
        uiManager.render(g);
        /*g.setColor(Color.blue);
        g.setFont(new Font("Serif", Font.BOLD, 12));
        g.drawString("testing string", 150, 150);*/
    }
}
