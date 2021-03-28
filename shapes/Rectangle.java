package shapes;

import java.awt.*;

/**
 * A rectangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes (Modified), Juan David Murillo, Carlos Orduz
 * @version 1.0  (15 July 2000)()
 */
public class Rectangle extends Shape{
    public static int EDGES = 4;
    
    private int height;
    private int width;

    /**
     * Create a new rectangle at default position with default color.
     */
    public Rectangle(){
        super("green", true, 5, 5);
        height = 20;
        width = 20;
    }
    
    /**
     * Create a new rectangle at default position with an specific size
     */
    public Rectangle(int size){
        super("green", true, 5, 5);
        this.height = size;
        this.width = size;
    }

    /**
     * Make this rectangle visible. If it was already visible, do nothing.
     */
    @Override
    public void makeVisible(){
        super.setVisible(true);
        draw();
    }
    
    /**
     * Make this rectangle invisible. If it was already invisible, do nothing.
     */
    @Override
    public void makeInvisible(){
        erase();
        super.setVisible(false);
    }
    
    /**
     * Method for zooming in / out 10% 
     * @param '+' for zooming in, '-' for zooming out
     */
    public void zoom(char z){
        // Eliminamos el cuadro
        this.erase();
        
        float newHeight = this.height * 0.1f;
        float newWidth = this.width * 0.1f;
        
        switch(z){
            case '+':
                this.height = this.height + (int)newHeight;
                this.width = this.width + (int)newWidth;   
                break;
            case '-':
                this.height = this.height - (int)newHeight;
                this.width = this.width - (int)newWidth;
                break;
        }
        
        // Dibujamos el cuadro
        this.draw();
    }   
    
    /**
     * Method for resetting the position of the rectangle
     */
    public void resetPosition(){
        this.erase();
        
        super.setXPosition(5);
        super.setYPosition(5);
        
        this.draw();
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
     * @param newWidht the new width in pixels. newWidth must be >=0.
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
    public void changeColor(String newColor){
        super.setColor(newColor);
        draw();
    }
     
    /**
     * Draw the rectangle with current specifications on screen.
     */
    @Override
    protected void draw() {
        if(super.isVisible()) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, super.getColor(),
                new java.awt.Rectangle(super.getXPosition(), super.getYPosition(), 
                                       width, height));
            canvas.wait(10);
        }
    }

    /**
     * Erase the rectangle on screen.
     */
    @Override
    protected void erase(){
        if(super.isVisible()) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}

