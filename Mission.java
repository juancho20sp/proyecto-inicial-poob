import javax.swing.JOptionPane;
/**
 * Initial project for POOB
 * 
 * @author Juan David Murillo - Carlos Orduz
 * @version 0.1
 */
public class Mission
{
    
    private Rectangle[][] warehouse;
    private Rectangle[][] planningZone;

    // Dimensiones de las bodegas
    private int rows;
    private int cols;
    
    // Matriz de almacenamiento
    private int[][] warehouseValues;
    private int[][] planningZoneValues;
    
    // Tamaño de cada una de las fichas
    private int size = 22;


/**
 * Mission class constructor
 */
public Mission(int length, int width){
    this.rows = length;
    this.cols = width;
    
    // Creamos el tablero
    this.createBoards(this.rows, this.cols);
}

/**
 * Method for creating the board
 * @param   rows    The number of rows on the board
 * @param   cols    The number of cols on the boad
 */
private void createBoards(int rows, int cols){
    // Preparamos el espacio en memoria para traer los rectángulos
    warehouse = new Rectangle[rows][cols];
    planningZone = new Rectangle[rows][cols];        
    
    // Creamos las matrices que contendrán los valores de cada espacio
    this.prepareMatrix(rows, cols);
    
    for (int i = 0; i < rows; i++){
        for(int j = 0; j < cols; j++){
            // Creamos las casillas de la bodega
            this.warehouse[i][j] = new Rectangle();
            
            // Creamos las casillas de la zona de plan de robo
            this.planningZone[i][j] = new Rectangle();
            
            // Apagamos las casillas de la zona de plan de robo
            this.planningZone[i][j].makeInvisible();
            
            // Inicializamos la posición en 0
            this.warehouseValues[i][j] = 0;
            
            // Movemos el rectángulo en el tablero
            this.warehouse[i][j].moveHorizontal(this.size * j);
            this.warehouse[i][j].moveVertical(this.size * i);
        }            
    }
}

/**
 * Create the matrixes that will have the number of boxes in each zone
 * @param   rows    number of rows
 * @param   cols    number of columns
 */
private void prepareMatrix(int rows, int cols){
    // Preparamos la matriz que tendrá el registro de las cajas por espacio en bodega
    warehouseValues = new int[rows][cols];        
    
    // Preparamos la matriz que tendrá el registro de las cajas por espacio en bodega
    planningZoneValues = new int[rows][cols]; 
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
    
    if(newRow <= this.rows && newCol <= this.cols){
        // Guardamos la caja en la casilla seleccionada
        this.warehouseValues[newRow][newCol] += 1;
    
        // Dibujamos la caja
        this.warehouse[newRow][newCol].changeColor("black");
    } else {
        JOptionPane.showMessageDialog(null, "La posición ingresada no es válida");
    }
}

/**
 * Store method
 * @param   crate   int[]
 */
public void store(int [] crate){
    
}

/**
 * Print the actual occupation map
 */
public void printOccupationMap(){
    String warehouseRes = "";
    String planningZoneRes = "";    
    
    for(int i = 0; i < this.rows; i++){
        for(int j = 0; j < this.cols; j++){
            warehouseRes += this.warehouseValues[i][j];
            planningZoneRes += this.planningZoneValues[i][j];
        }
        warehouseRes += "\n";
        planningZoneRes += "\n";
    }
    
    // Imprimimos la matriz de valores de la bodega
    System.out.println("Warehouse values:");
    System.out.println(warehouseRes);
    
    // Imprimimos la matriz de valores de la zona de planeación
    System.out.println("Planning zone values:");
    System.out.println(planningZoneRes);
       
}


/**
 * Copy actual state of the warehouse into the planning zone
 */
public void copy(){
    // Dibujamos la zona de planeación
    this.createPlanningZone();
    
    // Actualizamos los valores de la zona de planeación
    this.copyValues();
    
    // Coloreamos la zona de planeación
    this.colorPlanningZone();
}

/**
 * Draw planning zone spaces
 */
private void createPlanningZone(){
if (this.planningZone[0][0].isVisible() == false){   
    for(int i = 0; i < this.rows; i++){
        for(int j = 0; j < this.cols; j++){
        // Hacemos visible el tablero de la zona de planeación
        this.planningZone[i][j].makeVisible();               

        // Los ubicamos en el canvas
        this.planningZone[i][j].moveVertical((this.size * this.rows) +
        this.size * i + 25);
        this.planningZone[i][j].moveHorizontal(this.size * j);
       }
    }   
}


}

/**
* Copy values per position into planning zone
*/
private void copyValues(){
for(int i = 0; i < this.rows; i++){
    for(int j = 0; j < this.cols; j++){
        // Agregamos los valores de las cajas en bodega
        this.planningZoneValues[i][j] = this.warehouseValues[i][j]; 
    }
}
}

/**
* Color the planning zone boxes
*/
private void colorPlanningZone(){
for(int i = 0; i < this.rows; i++){
    for(int j = 0; j < this.cols; j++){                
        // Si hay una o más cajas, las dibujamos
        if(0 < this.planningZoneValues[i][j]){
            this.planningZone[i][j].changeColor("black");
        } else {
            this.planningZone[i][j].changeColor("green");
        }
    }
}
}


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
