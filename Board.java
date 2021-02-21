import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Arrays;
    
/**
 * Here will be all the information regarding to the boards (warehouse and planning zone)
 * 
 * @author Juan David Murillo - Carlos Orduz
 * @version 0.1
 */
public class Board
{
    private Rectangle[][] topView;
    private Rectangle[][] frontView;
    private Rectangle[][] sideView;

    // Dimensiones del tablero
    private int rows;
    private int cols;
    
    // Matriz de almacenamiento
    private int[][] values;
    
    // Tamaño de cada una de las fichas
    private int size = 22;
    
    // Cantidad de cajas robadas
    private int stolenBoxes = 0;
    
    // Historial de posiciones de cajas robadas
    private ArrayList<String> stealHistorial = new ArrayList<String>();
    
    // Cajas pendientes por organizar
    private int boxesToArrange = 0;
    
    // ¿El simulador es visible?
    private boolean isVisible = false; 
    
    // ¿Acción válida?
    private boolean isOk = false;
    
    /**
     * Constructor for objects of class Board
     * @param   rows    Number of rows of the board
     * @param   cols    Number of columns of the board    
     */
    public Board(int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
        
        // Creamos las vistas del tablero
        this.topView = new Rectangle[rows][cols];
        this.frontView = new Rectangle[rows][cols];
        this.sideView = new Rectangle[rows][cols];
        
        // Preparamos la matriz de valores
        this.values = new int[rows][cols];
        
        // Inicializamos cada posición de los tableros
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                // Agregamos un rectángulo a cada posición
                this.topView[i][j] = new Rectangle();
                this.frontView[i][j] = new Rectangle();
                this.sideView[i][j] = new Rectangle();
                
                // Inicializamos los valores
                this.values[i][j] = 0;
                
                // Movemos la vista frontal a la posición adecuada
                this.frontView[i][j].moveHorizontal(this.size * j);
                this.frontView[i][j].moveVertical(this.size * i);
                
                // Movemos la vista lateral a la posición adecuada
                this.sideView[i][j].moveHorizontal((this.size * this.cols) +
                    this.size * j + 25);
                this.sideView[i][j].moveVertical(this.size * i);
                
                // Movemos la vista superior a la posición adecuada
                this.topView[i][j].moveHorizontal((this.size * this.cols * 2) +
                    this.size * j + 50);
                this.topView[i][j].moveVertical(this.size * i); 
            }
        }
    }
    
    /**
     * Method for adding a box to the board
     * @param   row     The row where we want to insert the box
     * @param   col     The column where we want to insert the box
     * @param   color   The color of the boxes. ie 'black', 'red', 'blue'
     */
    public void insertBox(int row, int col, String color){
        // Actualizamos la cantidad de cajas en la posición dada
        this.values[row][col]++;
        
        // Dibujamos la caja
        this.topView[row][col].changeColor(color);
        
        // Dibujamos la vista frontal
        this.colorFrontView(color);
        
        // Dibujamos la vista lateral
        this.colorSideView(color);
    }   
    
    
    /**
     * Method for coloring the boxes of the front view
     * @param The color of the boxes. ie 'black', 'red', 'blue'
     */
    private void colorFrontView(String boxColor){
        // Traemos los máximos por columna
        int[] valuesFront = this.maxValuePerColumn(this.values);
        
        // Corregimos los valores para evitar errores al dibujar
        for(int i = 0; i < this.rows; i++){
            if(valuesFront[i] > this.rows){
                valuesFront[i] = this.rows - 1;
            }
        }
        
        // Dibujamos las cajas en la vista frontal
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < valuesFront[i]; j++){
                this.frontView[this.rows - j - 1][i].changeColor(boxColor);            
            }
        }
    }
    
    
    /**
     * Method for coloring the boxes of the side view
     * @param The color of the boxes. ie 'black', 'red', 'blue'
     */
    private void colorSideView(String boxColor){
        // Traemos los máximos por fila
        int[] valuesSide = this.maxValuePerRow(this.values);
        
        // Corregimos los valores para evitar errores al dibujar
        for(int i = 0; i < this.rows; i++){
            if(valuesSide[i] > this.rows){
                valuesSide[i] = this.rows - 1;
            }
        }
        
        // Dibujamos las cajas en la vista frontal
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < valuesSide[i]; j++){
                this.sideView[this.rows - j - 1][i].changeColor(boxColor);            
            }
        }
    }
    
    
    
    /**
     * Verify max values per column for the locations
     * @param  location A matrix with the values of the boxes per index of the location
     */
    private int[] maxValuePerColumn(int[][] location){
        int[] res = new int[this.cols];
        
        // Inicializamos los valores del arreglo respuesta
        for(int i = 0; i < this.cols; i++){
            res[i] = 0;
        }
        
        // Buscamos mayores valores por columna
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                if(location[i][j] > res[j]){
                    res[j] = location[i][j];
                }
            }
        }        
               
        //System.out.println("Column: " +Arrays.toString(res));        
        
        return res;
    }
    
    
    /**
     * Verify max values per row for the locations
     * @param   location   A matrix with the values of the boxes per index of the location
     */
    private int[] maxValuePerRow(int[][] location){
        int[] res = new int[this.rows];
        
        // Inicializamos los valores del arreglo respuesta
        for(int i = 0; i < this.rows; i++){
            res[i] = 0;
        }
        
        // Buscamos mayores valores por fila
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                if(location[i][j] > res[i]){
                    res[i] = location[i][j];
                }
            }
        }
        
        //System.out.println("Row: " + Arrays.toString(res));
        
        return res;
    }
    
    /**
     * Method for changing the board color
     * @param The new color. ie 'black', 'blue', 'red'
     */
    public void changeColor(String color){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.topView[i][j].changeColor(color);
                this.frontView[i][j].changeColor(color);
                this.sideView[i][j].changeColor(color);
            }
        }
    }
    
    /**
     * Method for making the board invisible   
     */
    public void makeInvisible(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.topView[i][j].makeInvisible();
                this.frontView[i][j].makeInvisible();
                this.sideView[i][j].makeInvisible();
            }
        }
    }   
    
    /**
     * Method for making the board visible
     */
    public void makeVisible(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.topView[i][j].makeVisible();
                this.frontView[i][j].makeVisible();
                this.sideView[i][j].makeVisible();
            }
        }
    }
    
    /**
     * Method for moving the board vertically
     * @param   The number of units the board will move vertically
     */
    public void moveVertical(int units){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.topView[i][j].moveVertical(units);
                this.frontView[i][j].moveVertical(units);
                this.sideView[i][j].moveVertical(units);
            }
        }
    }
    
    /**
     * Method for moving the board horizontally
     * @param   The number of units the board will move horizontally
     */
    public void moveHorizontal(int units){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.topView[i][j].moveHorizontal(units);
                this.frontView[i][j].moveHorizontal(units);
                this.sideView[i][j].moveHorizontal(units);
            }
        }
    }
    
    /**
     * Getter for the 'isVisible' attribute
     * @return True if the board is visible, false otherwise
     */
    public boolean getIsVisible(){
        return this.isVisible;
    }
    
    /**
     * Setter for the 'isVisible' attribute
     * @param The new value for the 'isVisible' attribute
     */
    public void setIsVisible(boolean isVisible){
        this.isVisible = isVisible;
    }
    
    /**
     * Getter for the 'values' attribute
     * @return An array with all the values of the actual board
     */
    public int[][] getValues(){
        int[][] res = new  int[this.rows][this.cols];
        
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                res[i][j] = this.values[i][j];
            }
        }
        
        
        
        //System.out.println("any value: " +this.values[0][0]);
        //System.out.println("getValues(): " + Arrays.toString(res));
        return this.values;
    }

    
}
