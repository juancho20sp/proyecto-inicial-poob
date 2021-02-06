/**
 * Write a description of class Mission here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mission
{
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    
    /**
     * Mission class constructor
     */
    public void Mission(int lendth, int width){
    }
    
    /**
     * Store method
     * @param   row     int
     * @param   col     int
     */
    public void store(int row, int column){}
    
    /**
     * Store method
     * @param   crate   int[]
     */
    public void store(int [] crate){}
    
    /**
     * Copy method
     */
    public void copy(){}
    
    /**
     * Steal method
     * @param   row     int
     * @param   column  int
     */
    public void steal(){}
    
    /**
     * Steal method
     * @param   crate   int[]
     */
    public void steal(int crate){}
    
    /**
     * Return method
     * 
     */
    public void returnBox(){}
    
    /**
     * Arrange method
     * @param   from    int[]
     * @param   to      int[]
     */
    public void arrange(int from, int to){}
    
    /**
     * Return total of stolen boxes
     * @return  Total of stolen boxed
     */
    public int stolen(){
        return 0;
    }
    
    /**
     * Warehouse
     * @return  the warehouse matrix
     */
    public int[][] warehouse(){
        int[][] temp = new int[1][1];
        return temp;
    }
    
    /**
     * Layout method
     * @return  the layout method
     */
    public int[][] layout(){
        int[][] temp = new int[1][1];
        
        return temp;
    }
    
    
    /**
     * Method for making invisible the board
     */
    public void makeVisible(){}
    
    /**
     * Method for making invisible the board
     */
    public void makeInvisible(){}
    
    /**
     * Finish
     */
    public void finish(){}
    
    /**
     * Method for checking if the last action was valid
     * @return  true if the last movement was valid
     */
    public boolean ok(){
        return true;
    }
    
}
