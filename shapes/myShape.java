package shapes;

/**
 * Abstract class Shape - write a description of the class here
 * 
 * @author: Juan David Murillo - Carlos Orduz 
 * Date: 27/03/2021
 */
public abstract class myShape
{
    private String color;
    private boolean isVisible;    
    private int xPosition;
    private int yPosition;
    
    /**
     * Constructor for the Shape class
     * @param The color of the shape
     * @param The visibility of the shape
     */
    public myShape(String color, boolean isVisible, int xPosition, int yPosition){
        this.color = color;
        this.isVisible = isVisible;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    
    /**
     * Method for making visible the shape
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Method for making invisible the shape
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Move the shape a few pixels to the right
     */
    public void moveRight(){
        moveHorizontal(20);
    }
    
    /**
     * Move the shape a few pixels to the left
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }
    
    /**
     * Move the shape a few pixels up
     */
    public void moveUp(){
        moveVertical(-20);
    }
    
    /**
     * Move the shape a few pixels down
     */
    public void moveDown(){
        moveVertical(20);
    }
    
    /**
     * Move the shape horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        this.xPosition += distance;
        draw();
    }
    
    /**
     * Move the shape vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        this.yPosition += distance;
        draw();
    }
    
    /**
     * Slowly move the shape horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            this.xPosition += delta;
            draw();
        }
    }
    
    /**
     * Slowly move the shape vertically
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            this.yPosition += delta;
            draw();
        }
    }
    
    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public abstract void changeColor(String newColor);
    
    /**
     * Method for drawing the shape
     */
    protected abstract void draw();
    
    /**
     * Method for erasing the shape
     */
    protected abstract void erase();
    
    /**
     * Getter for the isVisible attribute
     */
    public boolean isVisible(){
        return this.isVisible;
    }
    
    /**
     * Setter for the isVisible attribute
     * @param The value for the isVisible attribute
     */
    public void setVisible(boolean isVisible){
        this.isVisible = isVisible;
    }
    
    /**
     * Getter for the color attribute
     */
    public String getColor(){
        return this.color;
    }
    
    /**
     * Setter for the color attribute
     * @param The value for the color attribute
     */
    public void setColor(String color){
        this.color = color;
    }
    
    /**
     * Getter for the xPosition attribute
     */
    public int getXPosition(){
        return this.xPosition;
    }
    
    /**
     * Setter for the xPosition attribute
     * @param The value for the xPosition attribute
     */
    public void setXPosition(int xPosition){
        this.xPosition = xPosition;
    }
    
    /**
     * Getter for the yPosition attribute
     */
    public int getYPosition(){
        return this.yPosition;
    }
    
    /**
     * Setter for the yPosition attribute
     * @param The value for the yPosition attribute
     */
    public void setYPosition(int yPosition){
        this.yPosition = yPosition;
    }
    
    
}
