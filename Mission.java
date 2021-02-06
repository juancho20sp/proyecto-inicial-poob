import javax.swing.JOptionPane;
/**
 * Initial project for POOB
 * 
 * @author Juan David Murillo - Carlos Orduz
 * @version 0.1
 */
public class Mission
{
    
    private Rectangle[][] board;
    
    private int rows;
    private int cols;
    
    // Matriz de almacenamiento
    private int[][] occupationMap;
    
    
    /**
     * Mission class constructor
     */
    public Mission(int length, int width){
        this.rows = length;
        this.cols = width;
        
        // Creamos el tablero
        this.createBoard(this.rows, this.cols);
    }
    
    /**
     * Method for creating the board
     * @param   rows    The number of rows on the board
     * @param   cols    The number of cols on the boad
     */
    public void createBoard(int rows, int cols){
        // Preparamos el espacio en memoria para traer los rectángulos
        board = new Rectangle[rows][cols];
        
        // Preparamos la matriz que tendrá el registro de las cajas por espacio
        occupationMap = new int[rows][cols];
        
        // Tamaño de cada una de las fichas
        int size = 22;
        
        for (int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                this.board[i][j] = new Rectangle();
                
                // Inicializamos la posición en 0
                this.occupationMap[i][j] = 0;
                
                // Movemos el rectángulo en el tablero
                this.board[i][j].moveHorizontal(size * j);
                this.board[i][j].moveVertical(size * i);
            }            
        }
    }
    
    /**
     * Store a single box on the warehouse, positions start at 1
     * @param   row     int
     * @param   col     int
     */
    public void store(int row, int column){
        // Arreglamos índices
        int newRow = row - 1;
        int newCol = column - 1;
        
        // Guardamos la caja en la casilla seleccionada
        this.occupationMap[newRow][newCol] += 1;
        
        // Dibujamos la caja
        this.board[newRow][newCol].changeColor("black");
    }
    
    /**
     * Store method
     * @param   crate   int[]
     */
    public void store(int [] crate){}
    
    /**
     * Print the actual occupation map
     */
    public void printOccupationMap(){
        String res = "";
        
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                res += this.occupationMap[i][j] + "";
            }
            res += "\n";
        }
        
        JOptionPane.showMessageDialog(null, res);
    }
    
    
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
