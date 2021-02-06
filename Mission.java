/**
 * Write a description of class Mission here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mission
{
    
    private Rectangle[][] board;
    private int length;
    private int width;
    
    
    
    /**
     * Mission class constructor
     */
    public Mission(int length, int width){
        this.length = length;
        this.width = width;
        
        // Creamos el tablero
        this.createBoard(this.length, this.width);
    }
    
    /**
     * Method for creating the board
     * @param   rows    The number of rows on the board
     * @param   cols    The number of cols on the boad
     */
    public void createBoard(int rows, int cols){
        // Preparamos el espacio en memoria para traer los rectángulos
        board = new Rectangle[rows][cols];
        
        // Tamaño de cada una de las fichas
        int size = 22;
        
        for (int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                board[i][j] = new Rectangle();
                
                // Movemos el rectángulo en el tablero
                board[i][j].moveHorizontal(size * j);
                board[i][j].moveVertical(size * i);
            }            
        }
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
