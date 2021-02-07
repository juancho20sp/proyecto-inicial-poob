    import javax.swing.JOptionPane;
    import java.util.ArrayList;
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
        
        if(this.isValidPosition(newRow, newCol)){
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
        
        // Coloreamos la zona de planeación
        this.colorPlanningZone();
    }
    
    /**
    * Draw planning zone spaces
    */
    private void createPlanningZone(){
        if (this.isPlanningCreated == false){   
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
    * Color the planning zone boxes
    */
    private void colorWarehouse(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){                
                // Si hay una o más cajas, las dibujamos
                if(0 < this.warehouseValues[i][j]){
                this.warehouse[i][j].changeColor("black");
                } else {
                this.warehouse[i][j].changeColor("green");
                }
            }
        }
    }
    
    
    /**
    * Steal method
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
                
                // Re coloreamos la zona de planeación
                this.colorPlanningZone();
                
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
    * @param   crate   int[]
    */
    public void steal(int[] crate){
        // Tomamos los índices
        int newRow = crate[0];
        int newCol = crate[1];
        
        // Robamos la caja en dicha posición
        this.steal(newRow, newCol);
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
            
            // Pintamos la caja
            this.colorPlanningZone();
            
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
                    
                    // Pintamos los elementos del tablero
                    this.colorPlanningZone();
                    
                    
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
        if(row < 0 || col < 0){
            return false;
        }
        
        if(row < this.rows && col < this.cols){
            return true;
        }
        
        return false;
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
    * Warehouse
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
    * Layout method
    * @return  the layout method
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
                
                // Prendemos la zona de planeación
                this.planningZone[i][j].makeVisible();
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
                
                // Apagamos la zona de planeación
                this.planningZone[i][j].makeInvisible();
            }
        }
    }
    
    /**
    * Method for finishing the simulator
    */
    public void finish(){
        JOptionPane.showMessageDialog(null, "Terminando simulador...");        
    }  
   
    
    /**
    * Method for checking if the last action was valid
    * @return  true if the last movement was valid
    */
    public boolean ok(){
    return true;
    }
    
    }
