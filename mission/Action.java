package mission;


/**
 * A class for storing all the actions done in the board
 * 
 * @author Juan David Murillo - Carlos Javier Orduz
 * @version 07/03/2021
 */
public class Action
{
    private String action;
    private Box initialBox;
    private Box finalBox;
    
    
    /**
     * Constructor for objects of class Action
     * @param The name of the action
     * @param The initial row of the crate we are modifying
     * @param The initial column of the crate we are modifying
     */
    public Action(String action, Box initialBox)
    {
        this.action = action;
        this.initialBox = initialBox;
        
        
    }
    
    
    /**
     * Constructor for objects of class Action
     * @param The name of the action
     * @param The initial row of the crate we are modifying
     * @param The initial column of the crate we are modifying
     * @param The target row for the crate we are modifying
     * @param The target col for the crate we are modifying
     */
    public Action(String action, Box initialBox, Box finalBox)
    {
        this.action = action;
        this.initialBox = initialBox;

        this.finalBox = finalBox;
        
    }
    
    /**
     * Getter for the action name
     * @returns The name of the action
     */
    public String getAction(){
        return this.action;
    }

    public Box getInitialBox(){
        return this.initialBox;
    }
    public Box getFinalBox(){
        return this.finalBox;
    }
   

    
}
