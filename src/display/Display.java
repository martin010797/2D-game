package display;
import javax.swing.*;
import java.awt.*;
import java.util.StringJoiner;

public class Display {

    private JFrame frame;
    //canvas is able to allow as to draw graphics
    private Canvas canvas;

    private String title;
    private int width;
    private int height;

    public Display(String pTitle, int pWidth, int pHeight){
        title = pTitle;
        width = pWidth;
        height = pHeight;
        createDisplay();
    }

    private void createDisplay(){
        frame = new JFrame();
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //optional
        frame.setResizable(false);
        //appearing on center of screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //graphics
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width,height));
        canvas.setMaximumSize(new Dimension(width,height));
        canvas.setMinimumSize(new Dimension(width,height));
        canvas.setFocusable(false);

        //adding graphics to frame
        frame.add(canvas);
        //resize window to see all canvas fully
        frame.pack();
    }

    public Canvas getCanvas(){
        return canvas;
    }

    public JFrame getJFrame(){
        return frame;
    }
}
