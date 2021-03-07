
/**
 * This is a class for the coordinates of the board
 * 
 * @author Juan David Murillo - Carlos Javier Orduz 
 * @version 1.0
 */
public class Coordinate
{
    private int row;
    private int col;

    /**
     * Constructor for objects of class Coordinate
     * @param row  The row of the coordinate
     * @param col  The column of the coordinate
     */
    public Coordinate(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    /**
     * Getter of the row attribute
     * @return The integer value of the row
     */
    public int getRow(){
        return this.row;
    }
    
    /**
     * Getter of the col attribute
     * @return The integer value of the column
     */
    public int getCol(){
        return this.col;
    }
    
    /**
     * The toString() method for the Coordinate class
     */
    public String toString(){
        return this.row + "-" + this.col;
    }
}
