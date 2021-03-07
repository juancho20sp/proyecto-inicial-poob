
/**
 * A class for storing all the actions done in the board
 * 
 * @author Juan David Murillo - Carlos Javier Orduz
 * @version 07/03/2021
 */
public class Action
{
    private String action;
    private int initialRow;
    private int initialCol;
    private int finalRow;
    private int finalCol;
    
    
    /**
     * Constructor for objects of class Action
     * @param The name of the action
     * @param The initial row of the crate we are modifying
     * @param The initial column of the crate we are modifying
     */
    public Action(String action, int initialRow, int initialCol)
    {
        this.action = action;
        this.initialRow = initialRow;
        this.initialCol = initialCol;
    }
    
    
    /**
     * Constructor for objects of class Action
     * @param The name of the action
     * @param The initial row of the crate we are modifying
     * @param The initial column of the crate we are modifying
     * @param The target row for the crate we are modifying
     * @param The target col for the crate we are modifying
     */
    public Action(String action, int initialRow, int initialCol, int finalRow, int finalCol)
    {
        this.action = action;
        this.initialRow = initialRow;
        this.initialCol = initialCol;
        this.finalRow = finalRow;
        this.finalCol = finalCol;
    }
    
    /**
     * Getter for the action name
     * @returns The name of the action
     */
    public String getAction(){
        return this.action;
    }
    
    /**
     * Getter for the initial row
     * @returns The initial row
     */
    public int getInitialRow(){
        return this.initialRow;
    }
    
    /**
     * Getter for the initial col
     * @returns The initial col
     */
    public int getInitialCol(){
        return this.initialCol;
    }
    
    /**
     * Getter for the final row
     * @returns The final row
     */
    public int getFinalRow(){
        return this.finalRow;
    }
    
    /**
     * Getter for the final col
     * @returns The final col
     */
    public int getFinalCol(){
        return this.finalCol;
    }

    
}
