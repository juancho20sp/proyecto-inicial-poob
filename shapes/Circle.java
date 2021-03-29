package shapes; 

import java.awt.*;
import java.awt.geom.*;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Juan David Murillo - Carlos Orduz
 * @version 1.0.  (15 July 2000) 
 */

public class Circle extends myShape{

    public static final double PI=3.1416;
    
    private int diameter;
    
    /**
     * Constructor for the circle class
     */
    public Circle(){
        super("blue", false, 20, 15);
        diameter = 30;
    }
     
    /**
     * Makes the circle visible
     */
    @Override
    public void makeVisible(){
        super.setVisible(true);
        draw();
    }
    
    /**
     * Makes the circle invisible
     */
    @Override
    public void makeInvisible(){
        erase();
        super.setVisible(false);
    }

    /**
     * Draw the circle
     */
    @Override
    protected void draw(){
        if(super.isVisible()) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, super.getColor(), 
                new Ellipse2D.Double(super.getXPosition(), super.getYPosition(), 
                diameter, diameter));
            canvas.wait(10);
        }
    }
    
    /**
     * Erase the circle
     */
    @Override
    protected void erase(){
        if(super.isVisible()) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
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
     * Change the size.
     * @param newDiameter the new size (in pixels). Size must be >=0.
     */
    public void changeSize(int newDiameter){
        erase();
        diameter = newDiameter;
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
}
