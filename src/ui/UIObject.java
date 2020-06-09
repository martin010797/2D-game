package ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class UIObject {

    protected float x, y;
    protected int width, height;
    protected Rectangle bounds;
    protected boolean hovering = false;

    public UIObject(float pX, float pY, int pWidth, int pHeight){
        x = pX;
        y = pY;
        width = pWidth;
        height = pHeight;
        bounds = new Rectangle((int) x, (int) y, width, height);
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract void onClick();

    //detecting if users mouse is over this UIObject, if yes we'll set hovering to true
    public void onMouseMove(MouseEvent e){
        if (bounds.contains(e.getX(), e.getY()))
            hovering = true;
        else
            hovering = false;
    }

    public void onMouseRelease(MouseEvent e){
        if (hovering)
            onClick();
    }

    //getters and setters

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isHovering() {
        return hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }
}
