import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
    
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
        
    // ¿Acción válida?
    private boolean isOk = false;
    
    // Stack front
    Stack<Coordinate> stackFront = new Stack<Coordinate>();
    
    // Stack side
    Stack<Coordinate> stackSide = new Stack<Coordinate>();
    
    
    
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
                this.topView[i][j] = new Rectangle(this.size - 2);
                this.frontView[i][j] = new Rectangle(this.size - 2);
                this.sideView[i][j] = new Rectangle(this.size - 2);
                
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
     * Method for painting and repainting the board
     * @param True if it is a new board, false if it is a repaint
     */
    private void paintBoard(boolean newBoard){
        // Inicializamos cada posición de los tableros
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.topView[i][j].makeInvisible();
                this.frontView[i][j].makeInvisible();
                this.sideView[i][j].makeInvisible();
                
                // Agregamos un rectángulo a cada posición
                this.topView[i][j] = new Rectangle(this.size - 2);
                this.frontView[i][j] = new Rectangle(this.size - 2);
                this.sideView[i][j] = new Rectangle(this.size - 2);               
                
                
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
     * Method for refreshing the board
     * @param   The color of the boxes
     * @param   The background color
     */
    public void refreshBoard(String color, String bgColor){
        // Dibujamos la caja
        this.colorTopView(color, bgColor);
        //this.topView[row][col].changeColor(color);
        
        // Dibujamos la vista frontal
        this.colorFrontView(color, bgColor);
        
        // Dibujamos la vista lateral
        this.colorSideView(color, bgColor);
    }
    
    /**
     * Method for adding a box to the board
     * @param   row     The row where we want to insert the box
     * @param   col     The column where we want to insert the box
     * @param   color   The color of the boxes. ie 'black', 'red', 'blue'
     * @param   bgColor The background color
     */
    public void insertBox(int row, int col, String color, String bgColor){
        // Actualizamos la cantidad de cajas en la posición dada
        this.values[row][col]++;
        
        // Dibujamos la caja
        this.colorTopView(color, bgColor);
        //this.topView[row][col].changeColor(color);
        
        // Dibujamos la vista frontal
        this.colorFrontView(color, bgColor);
        
        // Dibujamos la vista lateral
        this.colorSideView(color, bgColor);
    }  
    
    /**
     * Method for removing a box from the board
     * @param   row     The row where we want to insert the box
     * @param   col     The column where we want to insert the box 
     */
    public void removeBox(int row, int col){
        // Actualizamos la cantidad de cajas en la posición dada
        //this.values[row][col]--;
        
        //if(!isStore){
            // Sumamos uno a la cantidad de cajas robadas
            this.values[row][col]--;
            this.stolenBoxes++;
        //}        
    }   
    
    
    /**
     * Method for coloring an specific box
     * @param   row     The row where the box is placed
     * @param   col     The column where the box is placed
     * @param   view    't': top, 'f': front, 's': side
     * @param   color   The color which the box will be painted with
     */
    public void paintBox(int row, int col, char view, String color){
        switch(view){
            case 'f':
                    this.frontView[row][col].changeColor(color);
                    break;
            case 't':
                    this.topView[row][col].changeColor(color);
                    break;
            case 's':
                    this.sideView[row][col].changeColor(color);
                    break;
            default:
                Mission.printOutput("El caracter ingresado no es válido, ingrese 't' o 's' o 'f' ");
                break;
        }
        
    }
    
    
    
    /**
     * Method for coloring the boxes of the front view
     * @param The color of the boxes. ie 'black', 'red', 'blue'
     * @param The background color
     */
    public void colorFrontView(String boxColor, String bgColor){
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
                this.paintBox(this.rows - j - 1, i, 'f', boxColor);
                
                int row = this.rows - j - 1;
                int col = i;
                
                this.stackFront.push(new Coordinate(row, col));           
            }
        }
    }
    
    
    /**
     * Method for coloring the boxes of the side view
     * @param The color of the boxes. ie 'black', 'red', 'blue'
     * @param The background color
     */
    public void colorSideView(String boxColor, String bgColor){
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
                this.paintBox(this.rows - j - 1, i, 's', boxColor);
                //this.sideView[this.rows - j - 1][i].changeColor(boxColor); 
                
                int row = this.rows - j - 1;
                int col = i;
                
                this.stackSide.push(new Coordinate(row, col));
            }
        }
    }
    
    /**
     * Method for coloring the boxes of the top view
     * @param The color of the boxes. ie 'black', 'red', 'blue'
     * @param The background color
     */
    public void colorTopView(String boxColor, String bgColor){
        // Dibujamos las cajas en la vista frontal
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                if (this.getValues()[i][j] > 0){
                    this.paintBox(i, j, 't', boxColor);
                }else {
                    //this.paintBox(i, j, 't', bgColor);
                }
                
                //this.sideView[this.rows - j - 1][i].changeColor(boxColor);            
            }
        }
    }
    
    // -------------------
    /**
     * Method for uncoloring the board
     * @param The color of the boxes. ie 'black', 'red', 'blue'
     * @param The background color
     */
    public void uncolorRefresh(int row, int col, String bgColor){
        System.out.println("UNCOLOR REFRESH!");
        
        this.uncolorSideView(bgColor);
        this.uncolorFrontView(bgColor);
        
        this.values[row][col]--;
        
        this.uncolorTopView(bgColor);
    }
    
    public void uncolorTopView(String bgColor){
        System.out.println("UNCOLOR TOP!");
        // Dibujamos las cajas en la vista frontal
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                if (this.getValues()[i][j] == 0){
                    this.paintBox(i, j, 't', bgColor);
                }
            }
        }
    }
    
    public void uncolorFrontView(String bgColor){
        System.out.println("UNCOLOR FRONT!");
        
        // Eliminamos la caja más alta de la vista frontal
        Coordinate toDelete = this.stackFront.pop();        
        this.paintBox(toDelete.getRow(), toDelete.getCol(), 'f', bgColor);
    }
    
    
    /**
     * Method for coloring the boxes of the side view
     * @param The color of the boxes. ie 'black', 'red', 'blue'
     * @param The background color
     */
    public void uncolorSideView(String bgColor){
        System.out.println("UNCOLOR SIDE!");
        
        // Eliminamos la caja más alta de la vista lateral
        Coordinate toDelete = this.stackSide.pop();     
        
        System.out.println("Lateral: " + toDelete.getRow() + "->" + toDelete.getCol());
        
        this.paintBox(toDelete.getRow(), toDelete.getCol(), 's', bgColor);
        
        
        
        
        // Corregimos los valores para evitar errores al dibujar
        /*for(int i = 0; i < this.rows; i++){
            if(valuesSide[i] > this.rows){
                valuesSide[i] = this.rows - 1;
            }
        }
        
        // Dibujamos las cajas en la vista frontal
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < valuesSide[i]; j++){
                System.out.println("Se debe eliminar la posición [" + i + "][" + j +"]" + " de la vista lateral");
                this.paintBox(this.rows - j - 1, i, 's', bgColor);
                break;
                //this.sideView[this.rows - j - 1][i].changeColor(boxColor);            
            }
        }*/
    }   
    
    
    // -------------------
    
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
     * Method for resetting the board
     * @param boxColor -> The color of the boxes
     * @param bgColor -> The background color
     */
    public void resetBoard(String boxColor, String bgColor){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                if (this.values[i][j] > 0){
                    this.uncolorRefresh(i, j, bgColor);
                }                
                this.values[i][j] = 0;                
            }
        }
        
        
        this.refreshBoard(boxColor, bgColor);
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
     * Method for zooming in / zooming out the board
     * @param '+' for zooming in, '-' for zooming out
     */
    public void zoom(char z){
        switch(z){
            case '+':
                this.size += 2;
                break;
            case '-':
                this.size -= 2;
                break;
        } 
        
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.topView[i][j].zoom(z);
                this.frontView[i][j].zoom(z);
                this.sideView[i][j].zoom(z);
            }
        }
        
        // Repintamos el tablero
        this.paintBoard(false);
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
     * Getter for the 'values' attribute
     * @return An array with all the values of the actual board
     */
    public int[][] getValues(){
        return this.values;
    }

    /**
     * Method for copying the values of the given matrix into the actual one
     * @param   board   An array containing all the values of a board
     */
    public void copyValues(Board board){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.values[i][j] = board.getValues()[i][j];
            }
        }
    }
    
    /**
     * Getter for the 'stolenBoxes' attribute
     * @return The amount of stolen boxes
     */
    public int getStolenBoxes(){        
        return this.stolenBoxes;
    }
    
    /**
     * Getter for the front view
     * @return The array of rectangles corresponding to the front view
     */
    public Rectangle[][] getFrontView(){        
        return this.frontView;
    }
    
    /**
     * Getter for the side view
     * @return The array of rectangles corresponding to the side view
     */
    public Rectangle[][] getSideView(){        
        return this.sideView;
    }   
        
}
