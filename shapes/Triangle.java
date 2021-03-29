package shapes; 

import java.awt.*;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0  (15 July 2000)
 */

public class Triangle extends myShape{
    
    public static int VERTICES=3;
    
    private int height;
    private int width;

    /**
     * Create a new triangle at default position with default color.
     */
    public Triangle(){
        super("green", false, 140, 15);
        height = 30;
        width = 40;
    }

    /**
     * Make this triangle visible. If it was already visible, do nothing.
     */
    @Override
    public void makeVisible(){
        super.setVisible(true);
        draw();
    }
    
    /**
     * Make this triangle invisible. If it was already invisible, do nothing.
     */
    @Override
    public void makeInvisible(){
        erase();
        super.setVisible(false);
    }
        
    /**
     * Move the circle vertically.
     * @param distance the desired distance in pixels
     */
    @Override
    public void moveVertical(int distance){
        erase();
        super.setYPosition(super.getYPosition() + distance);
        draw();
    }
    
    /**
     * Move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    @Override
    public void moveHorizontal(int distance){
        erase();
        super.setXPosition(super.getXPosition() + distance);
        draw();
    }

    /**
     * Slowly move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    @Override
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            super.setXPosition(super.getXPosition() + delta);
            draw();
        }
    }

    /**
     * Slowly move the circle vertically
     * @param distance the desired distance in pixels
     */
    @Override
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        }else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            super.setYPosition(super.getYPosition() + delta);
            draw();
        }
    }

    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidht must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
    
    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    @Override
    public void changeColor(String newColor){
        super.setColor(newColor);
        draw();
    }

    /**
     * Draw the triangle with current specifications on screen.
     */
    @Override
    protected void draw(){
        if(super.isVisible()) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { super.getXPosition(), super.getXPosition() + (width/2), super.getXPosition() - (width/2) };
            int[] ypoints = { super.getYPosition(), super.getYPosition() + height, super.getYPosition() + height };
            canvas.draw(this, super.getColor(), new Polygon(xpoints, ypoints, 3));
            canvas.wait(10);
        }
    }

    /**
     * Erase the triangle on screen.
     */
    @Override
    protected void erase(){        
        if(super.isVisible()) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
