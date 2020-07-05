package input;
import javax.swing.text.Keymap;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys;
    public boolean up,down,right,left,space,q,w,e,a,t;

    public KeyManager(){
        keys = new boolean[256];
    }

    public void tick(){
        //up = keys[KeyEvent.VK_W];
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        space = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_S];
        q = keys[KeyEvent.VK_Q];
        w = keys[KeyEvent.VK_W];
        e = keys[KeyEvent.VK_E];
        a = keys[KeyEvent.VK_A];
        t = keys[KeyEvent.VK_T];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        //System.out.println("pressed");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}
