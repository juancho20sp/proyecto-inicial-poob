    import javax.swing.JOptionPane;
    import java.util.ArrayList;
    import java.util.Arrays;
    
    /**
    * Initial project for POOB
    * 
    * @author Juan David Murillo - Carlos Orduz
    * @version 0.1
    */
    public class Mission
    {
    
    private Rectangle[][] warehouse;
    private Rectangle[][] warehouseFront;
    private Rectangle[][] warehouseSide;
    
    private Rectangle[][] planningZone;
    private Rectangle[][] planningZoneFront;
    private Rectangle[][] planningZoneSide;
    
    // Dimensiones de las bodegas
    private int rows;
    private int cols;
    
    // Matriz de almacenamiento
    private int[][] warehouseValues;
    private int[][] planningZoneValues;
    
    // Tamaño de cada una de las fichas
    private int size = 22;
    
    // Cantidad de cajas robadas
    private int stolenBoxes = 0;
    
    // Historial de posiciones de cajas robadas
    private ArrayList<String> stealHistorial = new ArrayList<String>();
    
    // Cajas pendientes por organizar
    private int boxesToArrange = 0;
    
    // ¿El simulador es visible?
    private boolean isVisible; 
    
    // ¿Ya se creó la zona de planeación?
    private boolean isPlanningCreated = false;
    
    // ¿Las bodegas son iguales?
    private boolean areEqual = true;
    
    // ¿Acción válida?
    private boolean isOk = false;
  
    // Colores de las zonas
    private String wareHouseColor = "green";
    private String warehouseBoxColor = "black";
    
    private String planningZoneColor = "magenta";
    private String planningZoneBoxColor = "blue";
    private String planningZoneDangerColor = "red";
    
    
    
    /**
    * Mission class constructor
    */
    public Mission(int length, int width){
        this.rows = length;
        this.cols = width;
        
        // Creamos el tablero
        this.createBoards(this.rows, this.cols);
        
        // Hacemos visible el tablero
        this.isVisible = true;
    }
    
    /**
     * Mission class constructor for creating the warehouse 
     * with a blue print
     */
    public Mission(int length, int width, int[][] heights){
    }
    
    
    /**
    * Method for creating the boards
    * @param   rows    The number of rows on the board
    * @param   cols    The number of cols on the boad
    */
    private void createBoards(int rows, int cols){
        // Preparamos el espacio en memoria para traer los rectángulos
        warehouse = new Rectangle[rows][cols];
        warehouseFront = new Rectangle[rows][cols];
        warehouseSide = new Rectangle[rows][cols];
        
        planningZone = new Rectangle[rows][cols]; 
        planningZoneFront = new Rectangle[rows][cols]; 
        planningZoneSide = new Rectangle[rows][cols]; 
        
        // Creamos las matrices que contendrán los valores de cada espacio
        this.prepareMatrix(rows, cols);
    
        for (int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                // Creamos las casillas de la bodega
                this.warehouse[i][j] = new Rectangle();
                this.warehouseFront[i][j] = new Rectangle();
                this.warehouseSide[i][j] = new Rectangle();
                
                // Creamos las casillas de la zona de plan de robo
                this.planningZone[i][j] = new Rectangle();
                this.planningZone[i][j].changeColor(this.planningZoneColor);
                
                this.planningZoneFront[i][j] = new Rectangle();
                this.planningZoneFront[i][j].changeColor(this.planningZoneColor);
                
                this.planningZoneSide[i][j] = new Rectangle();
                this.planningZoneSide[i][j].changeColor(this.planningZoneColor);
                
                
                // Apagamos las casillas de la zona de plan de robo
                this.planningZone[i][j].makeInvisible();
                this.planningZoneFront[i][j].makeInvisible();
                this.planningZoneSide[i][j].makeInvisible();
                
                // Inicializamos la posición en 0
                this.warehouseValues[i][j] = 0;
                
                // Movemos el tablero de la vista frontal de la bodega
                this.warehouseFront[i][j].moveHorizontal(this.size * j);
                this.warehouseFront[i][j].moveVertical(this.size * i);
                
                // Movemos el tablero de la vista lateral de la bodega
                this.warehouseSide[i][j].moveHorizontal((this.size * this.cols) +
                    this.size * j + 25);
                this.warehouseSide[i][j].moveVertical(this.size * i);
                
                // Movemos el tablero de la vista superior de la bodega
                this.warehouse[i][j].moveHorizontal((this.size * this.cols * 2) +
                    this.size * j + 50);
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
        
        if(this.isValidPosition(newRow, newCol)){
            // Guardamos la caja en la casilla seleccionada
            this.warehouseValues[newRow][newCol] += 1;
            
            // Dibujamos la caja
            this.warehouse[newRow][newCol].changeColor(this.warehouseBoxColor);
            
            // Dibujamos la vista frontal
            this.colorWarehouseFront();
            
            // Dibujamos la vista lateral
            this.colorWarehouseSide();
        } else {
            JOptionPane.showMessageDialog(null, "La posición ingresada no es válida");
        }
    }
    
    /**
    * Method for storing a new box on the warehouse board
    * @param   crate   Array[row, col]
    */
    public void store(int [] crate){
        // Tomamos los valores del arreglo
        int newRow = crate[0];
        int newCol = crate[1];
        
        // Guardamos la caja
        this.store(newRow, newCol);
    
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
        
        // Pintamos la zona de planeación
        this.repaintPlanningZone();
       
         
    }
    
    /**
    * Draw planning zone spaces
    */
    private void createPlanningZone(){
        if (this.isPlanningCreated == false){   
            for(int i = 0; i < this.rows; i++){
                for(int j = 0; j < this.cols; j++){
                    // Hacemos visibles las vistas tablero de la zona de planeación
                    this.planningZone[i][j].makeVisible();
                    this.planningZoneFront[i][j].makeVisible(); 
                    this.planningZoneSide[i][j].makeVisible(); 
                    
                    // Movemos el tablero de la vista frontal de la bodega
                    this.planningZoneFront[i][j].moveHorizontal(this.size * j);
                    this.planningZoneFront[i][j].moveVertical((this.size * this.rows) +
                    this.size * i + 25);
                    
                    // Movemos el tablero de la vista lateral de la bodega
                    this.planningZoneSide[i][j].moveHorizontal((this.size * this.cols) +
                        this.size * j + 25);
                    this.planningZoneSide[i][j].moveVertical((this.size * this.rows) +
                    this.size * i + 25);
                    
                    // Movemos el tablero de la vista superior de la bodega
                    this.planningZone[i][j].moveHorizontal((this.size * this.cols * 2) +
                        this.size * j + 50);
                    this.planningZone[i][j].moveVertical((this.size * this.rows) +
                    this.size * i + 25);
                }
            } 
            
            // Decimos que fue creada la zona de planeación
            this.isPlanningCreated = true;
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
        
        // Imprimimos valores máximo por fila y columna
        //this.maxValuePerColumn(this.warehouseValues);
        //this.maxValuePerRow(this.warehouseValues);
    }
    
    /**
    * Color the planning zone boxes
    */
    private void colorPlanningZone(){
        String color = this.planningZoneColor;
        
        // Verificamos la igualdad de las bodegas
        if(!this.areEqual){
            color = this.planningZoneDangerColor;
        }
        
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){                
                // Si hay una o más cajas, las dibujamos
                if(0 < this.planningZoneValues[i][j]){
                this.planningZone[i][j].changeColor(this.planningZoneBoxColor);
                } else {
                this.planningZone[i][j].changeColor(color);
                }
            }
        }
    }
    
    /**
     * Test method for entering the matrix as a parameter
     */
    private void setWarehouseValues(int[][] values){
        this.warehouseValues = values;
        
            // Valores
            // {{1,4,0,5,2}, {2,1,2,0,1}, {0,2,3,4,4}, {0,3,0,3,1}, {1,2,2,1,1}}
        
            this.colorWarehouse();
            
            // Dibujamos la vista frontal
            this.colorWarehouseFront();
            
            // Dibujamos la vista lateral
            this.colorWarehouseSide();
    }
    
    /**
    * Color the warehouse top view boxes
    */
    private void colorWarehouse(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){                
                // Si hay una o más cajas, las dibujamos
                if(0 < this.warehouseValues[i][j]){
                this.warehouse[i][j].changeColor(this.warehouseBoxColor);
                } else {
                this.warehouse[i][j].changeColor(this.wareHouseColor);
                }
            }
        }
    }
    
    /**
    * Color the warehouse front boxes
    */
    private void colorWarehouseFront(){
        // Traemos los máximos por columna
        int[] valuesFront = this.maxValuePerColumn(this.warehouseValues);
        
        // Corregimos los valores para evitar errores al dibujar
        for(int i = 0; i < this.rows; i++){
            if(valuesFront[i] > this.rows){
                valuesFront[i] = this.rows - 1;
            }
        }
        
        // Dibujamos las cajas en la vista frontal
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < valuesFront[i]; j++){
                this.warehouseFront[this.rows - j - 1][i].changeColor(this.warehouseBoxColor);            
            }
        }
    }
    
    /**
    * Color the warehouse side boxes
    */
    private void colorWarehouseSide(){
        // Traemos los máximos por fila
        int[] valuesSide = this.maxValuePerRow(this.warehouseValues);
        
        // Corregimos los valores para evitar errores al dibujar
        for(int i = 0; i < this.rows; i++){
            if(valuesSide[i] > this.rows){
                valuesSide[i] = this.rows - 1;
            }
        }
        
        // Dibujamos las cajas en la vista frontal
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < valuesSide[i]; j++){
                this.warehouseSide[this.rows - j - 1][i].changeColor(this.warehouseBoxColor);            
            }
        }
    }
    
    /**
    * Color the planning zone front boxes
    */
    private void colorPlanningZoneFront(){       
        
        // Traemos los máximos por columna
        int[] valuesFront = this.maxValuePerColumn(this.planningZoneValues);
        
        // Corregimos los valores para evitar errores al dibujar
        for(int i = 0; i < this.rows; i++){
            if(valuesFront[i] > this.rows){
                valuesFront[i] = this.rows - 1;
            }
        }
        
        // Dibujamos las cajas en la vista frontal
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < valuesFront[i]; j++){
                this.planningZoneFront[this.rows - j - 1][i].changeColor(this.planningZoneBoxColor);            
            }
        }
    }
    
    /**
    * Color the planning zone side boxes
    */
    private void colorPlanningZoneSide(){
        // Traemos los máximos por fila
        int[] valuesSide = this.maxValuePerRow(this.planningZoneValues);
        
        // Corregimos los valores para evitar errores al dibujar
        for(int i = 0; i < this.rows; i++){
            if(valuesSide[i] > this.rows){
                valuesSide[i] = this.rows - 1;
            }
        }
        
        // Dibujamos las cajas en la vista frontal
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < valuesSide[i]; j++){
                this.planningZoneSide[this.rows - j - 1][i].changeColor(this.planningZoneBoxColor);            
            }
        }
    }
    
    /**
     * Verify max values per column for the locations
     * @param   int     matrix with the values of the boxes per index of the location
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
     * @param   int[][]     matrix with the values of the boxes per index of the location
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
     * Method for resetting the original color of the planning zone
     */
    private void resetPlanningZoneColor(){ 
        String color = this.planningZoneColor;
        
        if(this.areEqual == false){
            color = this.planningZoneDangerColor;
        }
        
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){                
                // Vista frontal
                this.planningZoneFront[i][j].changeColor(color);
                
                // Vista lateral
                this.planningZoneSide[i][j].changeColor(color);
            }
        }
    }   
    
    
    /**
     * Method for verifying if the warehouse and the planning zone have the same views on the cameras
     */
    private void verifyEquality(){
        boolean areEqual = true;
        
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                // Si en la zona de planeación no hay cajas pero si hay en la bodega
                // esto indica que las bodegas son diferentes, así que se le comunica esta diferencia al usuario
                // cambiando el color de fondo de la zona de planeación               
                
                // Igualdad de la vista principal
                if(this.planningZoneValues[i][j] == 0 && this.warehouseValues[i][j] > 0){                    
                    areEqual &= false;
                }
                
                // Igualdad de la vista lateral 
                if(this.warehouseSide[i][j].getColor() == this.warehouseBoxColor 
                    && this.planningZoneSide[i][j].getColor() != this.planningZoneBoxColor){
                    areEqual &= false;
                    
                    //JOptionPane.showMessageDialog(null, "Error en vista lateral");
                }
                
                // Igualdad de la vista frontal 
                if(this.warehouseFront[i][j].getColor() == this.warehouseBoxColor 
                    && this.planningZoneFront[i][j].getColor() != this.planningZoneBoxColor){
                    areEqual &= false;
                    
                    //JOptionPane.showMessageDialog(null, "Error en vista frontal");
                }
                    
            }
        }
        
        JOptionPane.showMessageDialog(null, "Equal: " + areEqual);
        
        this.areEqual = areEqual;
    }
    
    /**
    * Method for stealing a box from the planning zone
    * @param   row     int
    * @param   column  int
    */
    public void steal(int row, int col){
        // Ajustamos los indices
        int newRow = row - 1;
        int newCol = col - 1;
        
        
        if(this.isValidPosition(newRow, newCol)){
            // Verificamos si hay cajas para robar en la posición
            if (this.planningZoneValues[newRow][newCol] > 0){                
                // Sacamos una caja de la posición objetivo
                this.planningZoneValues[newRow][newCol]--;
            
                // Sumamos uno a la cantidad de cajas robadas
                this.stolenBoxes++;
                
                // Re pintamos la zona de planeación
                this.repaintPlanningZone();
                
                // Le damos formato a la tupla
                String tuple = newRow + "-" + newCol;
    
                // Guardamos la tupla
                this.stealHistorial.add(tuple);
            } else {
                JOptionPane.showMessageDialog(null, "¡No hay nada para robar en esa posición!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Posición no existente, inténtelo nuevamente");     
        }
    }
    
    /**
    * Steal method
    * @param   crate   Array having the position of the crate: [row, col]
    */
    public void steal(int[] crate){
        // Tomamos los índices
        int newRow = crate[0];
        int newCol = crate[1];
        
        // Robamos la caja en dicha posición
        this.steal(newRow, newCol);
    }
    
    /**
     * Consults the position of the stolen crates
     */
    public int[][] toSteal(){
        return null;
    }
    
    /**
    * Undo the last movement (steal) on the planning zone
    * 
    */
    public void returnBox(){      
        // Si hay movimientos, podemos deshacerlos
        if(this.stealHistorial.size() > 0){
            // Tomamos el movimiento
            String lastMove = this.stealHistorial.get(this.stealHistorial.size() - 1);
            String pos[] = lastMove.split("-");
            
            int newRow = Integer.parseInt(pos[0]);
            int newCol = Integer.parseInt(pos[1]);
            
            // Aumentamos el contador de cajas en esa posición
            this.planningZoneValues[newRow][newCol]++;           
                       
            // Re pintamos la devolución de la caja
            this.repaintPlanningZone();           
             
            
            // Repintamos con el color adecuado
            this.repaintPlanningZone();           
            
            
            // Modificamos la cantidad de cajas a arreglar
            this.stolenBoxes--;
            
            // Eliminamos el movimiento del historial
            this.stealHistorial.remove(this.stealHistorial.size() - 1);
        } else {
            JOptionPane.showMessageDialog(null, 
                            "¡No hay cajas para devolver!");
        }      
    }
    
    /**
     * Undo the last valid action 
     */
    public void undo(){
    }
    
    /**
     * Redo the last undone action
     */
    public void redo(){
    }
    
    /**
     * Method for repainting the planning zone
     */
    private void repaintPlanningZone(){
        // Re seteamos el color
        this.resetPlanningZoneColor();
        
        // Re coloreamos la vista frontal de la zona de planeación
        this.colorPlanningZoneFront();
        
        // Re coloreamos la vista lateral de la zona de planeación
        this.colorPlanningZoneSide();
        
        // Re coloreamos la vista superior de la zona de planeación
        this.colorPlanningZone();
        
        // Verificamos si son iguales las bodegas
        this.verifyEquality();
        
        // Re seteamos el color
        this.resetPlanningZoneColor();
        
        // Re coloreamos la vista frontal de la zona de planeación
        this.colorPlanningZoneFront();
        
        // Re coloreamos la vista lateral de la zona de planeación
        this.colorPlanningZoneSide();
        
        // Re coloreamos la vista superior de la zona de planeación
        this.colorPlanningZone();
        
    }
    
    /**
     * Method for zooming in the warehouse
     * @param z     Char -> '+': 10% zoom in, '-': 10% zoom out
     */
    public void zoom(char z){
    }
    
    /**
    * Method for arranging the planning zone
    * @param   from    Array with two values [row, col]
    * @param   to      Array with two values [row, col]
    */
    public void arrange(int[] from, int[] to){
        // Guardamos los valores, restamos uno porque nuestros índices inician desde 0
        // y el usuario ingresa valores desde 1      
        int oldRow = from[0] - 1;
        int oldCol = from[1] - 1;
        
        int newRow = to[0] - 1;
        int newCol = to[1] - 1;
        
        // Verificamos si la posición anterior es válida
        if(this.isValidPosition(oldRow, oldCol)){
            // Verificamos si la posición nueva es válida
            if(this.isValidPosition(newRow, newCol)){
                // Verificamos si en esa posición hay una caja    
                if (this.planningZoneValues[oldRow][oldCol] > 0){
                    // Retiramos una caja de esa posición
                    this.planningZoneValues[oldRow][oldCol]--;
                    
                    // Agregamos la caja a la nueva posición
                    this.planningZoneValues[newRow][newCol]++;
                    
                    // Re pintamos la zona de planeación
                    this.repaintPlanningZone();
                    
                    
                } else {
                    JOptionPane.showMessageDialog(null, "¡No hay cajas para ubicar en esa posición!");
                }  
            } else {
                JOptionPane.showMessageDialog(null, "La posición 'to' no es válida");
            }
        } else {
            JOptionPane.showMessageDialog(null, "La posición 'from' no es válida");
        } 
        
        
    }
    
    /**
     * Verify if the position is valid 
     * @return True if it is a reachable position, false otherwise
     */
    private boolean isValidPosition(int row, int col){
        boolean res = false;
        //if(row < 0 || col < 0){
          //  res = false;
        //}
        
        if(row < this.rows && col < this.cols){
            res = true;
        }
        
        return res;
    }
    
    /**
    * Return total of stolen boxes
    * @return  Total of stolen boxed
    */
    public int stolen(){
        if(this.stolenBoxes == 1){
            JOptionPane.showMessageDialog(null, "Se ha robado 1 caja");
        } else {
            JOptionPane.showMessageDialog(null, "Se han robado " + this.stolenBoxes + " cajas");
        }
        
        return this.stolenBoxes;
    }
    
    /**
    * Checks the warehouse matrix
    * @return  the warehouse matrix
    */
    public int[][] warehouse(){    
        // Contamos la cantidad de cajas en la bodega
        int numBoxes = this.countBoxes(this.warehouseValues);
        
        // Mostramos el resultado en pantalla
        if(numBoxes == 1){
            JOptionPane.showMessageDialog(null, "En la bodega hay 1 caja");
        } else {
            JOptionPane.showMessageDialog(null, "En la bodega hay " + numBoxes + " cajas");
        }        
        
        return this.warehouseValues;
    }
    
    /**
    * Checks the amount of boxes in the planning zone
    * @return  The matrix with the values of the planning zone
    */
    public int[][] layout(){ 
        // Contamos la cantidad de cajas en la zona de planeación
        int numBoxes = this.countBoxes(this.planningZoneValues);
        
        // Mostramos el resultado en pantalla
        if(numBoxes == 1){
            JOptionPane.showMessageDialog(null, "En la zona de planeación hay 1 caja");
        } else {
            JOptionPane.showMessageDialog(null, "En la zona de planeación hay " + numBoxes + " cajas");
        } 
       
        return this.planningZoneValues;
    }
    
    /**
     * Method for counting the boxes of the given location
     * @return  int     number of boxes of the given location
     */
    private int countBoxes(int[][] location){
        int totalBoxes = 0;
        
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                totalBoxes += location[i][j];
            }
        }
        
        return totalBoxes;
    }
    
    
    /**
    * Method for making invisible the board
    */
    public void makeVisible(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                // Prendemos la bodega
                this.warehouse[i][j].makeVisible();
                this.warehouseFront[i][j].makeVisible();
                this.warehouseSide[i][j].makeVisible();
                
                // Prendemos la zona de planeación
                this.planningZone[i][j].makeVisible();
                this.planningZoneFront[i][j].makeVisible();
                this.planningZoneSide[i][j].makeVisible();                               
            }
        }
    }
    
    /**
    * Method for making invisible the board
    */
    public void makeInvisible(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                // Apagamos la bodega
                this.warehouse[i][j].makeInvisible();
                this.warehouseFront[i][j].makeInvisible();
                this.warehouseSide[i][j].makeInvisible();
                
                // Apagamos la zona de planeación
                this.planningZone[i][j].makeInvisible();
                this.planningZoneFront[i][j].makeInvisible();
                this.planningZoneSide[i][j].makeInvisible();
            }
        }
    }
    
    /**
    * Method for finishing the simulator
    */
    public void finish(){
        JOptionPane.showMessageDialog(null, "Terminando simulador...");        
        System.exit(0);
    }  
   
    
   /**
     * Getter for the 'isOk' attribute
     * @return The value of 'isOk'
     */
    public boolean ok(){
        return this.isOk;
    }
    
    /**
     * Setter for the 'isOk' attribute
     * @param The new attribute for 'isOk'
     */
    public void setIsOk(boolean isOk){
        this.isOk = isOk;
    }
    
    
    
    }
