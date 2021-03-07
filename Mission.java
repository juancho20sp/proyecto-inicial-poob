    import javax.swing.JOptionPane;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.Stack;
    
    /**
    * Initial project for POOB
    * 
    * @author Juan David Murillo - Carlos Orduz
    * @version 0.1
    */
    public class Mission
    {
    
    // Dimensiones de las bodegas
    private int rows;
    private int cols;
    
    // Tamaño de cada una de las fichas
    private int size = 22;
    
    // Historial de posiciones de cajas robadas
    private ArrayList<String> stealHistorial = new ArrayList<String>();
        
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
    
    // ¿La entrada de datos del usuario está habilitada?
    static boolean inputsAndNotificationsEnabled = false;
    
    // -----------------------------------------
    // Referencias a los tableros
    Board warehouse;
    Board planningZone;
    
    // ¿Está copiada la bodega?
    private boolean isCopied = false;
    
    // Stack de acciones para realizar undo
    // nombre-POS1,POS2 -> store-1,1    
    Stack<String> undoStack;
    
    // Stack de acciones para realizar redo
    Stack<String> redoStack;
    
    /**
    * Mission class constructor
    */
    public Mission(int length, int width){
        this.rows = length;
        this.cols = width;
        
        this.warehouse = new Board(this.rows, this.cols);
        this.planningZone = new Board(this.rows, this.cols);
        
        // Le cambiamos el color a la zona de planeación
        this.planningZone.changeColor(this.planningZoneColor);
        
        // Movemos la zona de planeación verticalmente
        this.planningZone.moveVertical((this.size * this.rows) + this.size); 
        
        // Creamos los stacks para el manejo de acciones
        this.redoStack = new Stack<String>();
        this.undoStack = new Stack<String>();
        
        // La creación fue exitosa
        this.setIsOk(true);
    }
    
    /**
     * Mission class constructor for creating the warehouse 
     * with a blue print
     */
    public Mission(int length, int width, int[][] heights){
    } 
    
    /**
     * Method for refreshing the boards
     */
    public void refreshBoards(){
        // Pintamos las cajas de la bodega
        this.warehouse.refreshBoard(this.warehouseBoxColor, this.wareHouseColor);
        
        // Le cambiamos el color a la zona de planeación
        this.planningZone.changeColor(this.planningZoneColor);
        
        // Movemos la zona de planeación verticalmente
        this.planningZone.moveVertical((this.size * this.rows) + this.size); 
        
        // Si la zona de planeación fue copiada antes de hacer zoom, 
        // actualizamos las cajas
        if (this.isCopied){
            // Pintamos únicamente las cajas copiadas
            this.planningZone.refreshBoard(this.planningZoneBoxColor, this.planningZoneColor);
            
            // Si las vistas de la zona de planeación son diferentes a las de la bodega,
            // pintamos la zona de planeación de color rojo
            this.repaintPlanningZone();
        }
    }
    
    /**
    * Store a single box on the warehouse, positions start at 1
    * @param   row     The row where we want to put the box
    * @param   col     The col where we want to put the box
    */
    public void store(int row, int column){
        // Arreglamos índices
        int newRow = row - 1;
        int newCol = column - 1;
        
        if(this.isValidPosition(newRow, newCol)){
            // Guardamos la caja en la casilla seleccionada
            this.warehouse.insertBox(newRow, newCol, this.warehouseBoxColor, this.wareHouseColor);            
            
            // Preparamos la cadena para el stack
            String pos = "store-"+ newRow + "," + newCol;
            
            // Agregamos la información al stack
            undoStack.push(pos);
            
            // Reset a la zona de planeación
            this.planningZone.resetBoard(this.planningZoneBoxColor, this.planningZoneColor);
            this.resetPlanningZoneColor();
            
            // La operación 'store' fue exitosa
            this.setIsOk(true);
        } else {
            // Imprimimos el output
            this.printOutput("La posición ingresada no es válida");      
            
            // La operación 'store' fue no exitosa
            this.setIsOk(false);
            
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
            warehouseRes += this.warehouse.getValues()[i][j];
            planningZoneRes += this.planningZone.getValues()[i][j];
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
        //this.createPlanningZone();
        
        // Actualizamos la bandera
        this.isCopied = true;
        
        // Actualizamos los valores de la zona de planeación
        this.copyValues();
        
        // Pintamos la zona de planeación
        this.repaintPlanningZone();         
    }
    
    
    /**
    * Copy values per position into planning zone
    */
    private void copyValues(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                // Agregamos los valores de las cajas en bodega
                //this.planningZoneValues[i][j] = this.warehouseValues[i][j]; 
                this.planningZone.copyValues(this.warehouse);
            }
        }
        
        // La operación 'store' fue exitosa
        this.setIsOk(true);
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
                if(0 < this.planningZone.getValues()[i][j]){
                    this.planningZone.paintBox(i, j, 't', this.planningZoneBoxColor);
                    //this.planningZone[i][j].changeColor(this.planningZoneBoxColor);
                } else {
                    this.planningZone.paintBox(i, j, 't', color);
                    //this.planningZone[i][j].changeColor(color);
                }
            }
        }
    }
    
    /**
     * Test method for entering the matrix as a parameter
     */
    private void setWarehouseValues(int[][] values){
        //this.warehouseValues = values;
        
            // Valores
            // {{1,4,0,5,2}, {2,1,2,0,1}, {0,2,3,4,4}, {0,3,0,3,1}, {1,2,2,1,1}}
        
            //this.colorWarehouse();
            
            // Dibujamos la vista frontal
            //this.colorWarehouseFront();
            
            // Dibujamos la vista lateral
            //this.colorWarehouseSide();
    }    
    
    /**
     * Method for resetting the original color of the planning zone
     */
    private void resetPlanningZoneColor(){ 
        String color = this.planningZoneColor;
        
        if(this.areEqual == false){
            color = this.planningZoneDangerColor;
        }
        
        this.planningZone.changeColor(color); 
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
                if(this.planningZone.getValues()[i][j] == 0 && this.warehouse.getValues()[i][j] > 0){                    
                    areEqual &= false;
                }
                
                                
                // Igualdad de la vista lateral 
                if(this.warehouse.getSideView()[i][j].getColor() == this.warehouseBoxColor 
                    && this.planningZone.getSideView()[i][j].getColor() != this.planningZoneBoxColor){
                    areEqual &= false;
                    
                    //JOptionPane.showMessageDialog(null, "Error en vista lateral");
                }
                
                // Igualdad de la vista frontal                 
                if(this.warehouse.getFrontView()[i][j].getColor() == this.warehouseBoxColor 
                    && this.planningZone.getFrontView()[i][j].getColor() != this.planningZoneBoxColor){
                    areEqual &= false;
                    
                    //JOptionPane.showMessageDialog(null, "Error en vista frontal");
                }
                    
            }
        }
        
        //JOptionPane.showMessageDialog(null, "Equal: " + areEqual);
        
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
            if (this.planningZone.getValues()[newRow][newCol] > 0){                
                // Sacamos una caja de la posición objetivo
                this.planningZone.removeBox(newRow, newCol);
                
                // Re pintamos la zona de planeación
                this.repaintPlanningZone();
                
                // Agregamos la acción de robo al stack de cosas por deshacer
                String res = "steal-" + newRow + "," + newCol;
                this.undoStack.push(res);
                
                // Le damos formato a la tupla
                String tuple = newRow + "-" + newCol;
    
                // Guardamos la tupla
                this.stealHistorial.add(tuple);
                
                // La operación 'steal' fue exitosa
                this.setIsOk(true);
            } else {
                // Imprimimos el output
                this.printOutput("¡No hay nada para robar en esa posición!");
                
                // La operación 'steal' no fue exitosa
                this.setIsOk(false);
            }
        } else {
            // Imprimimos el output
            this.printOutput("Posición no existente, inténtelo nuevamente");   
            
            // La operación 'steal' no fue exitosa
            this.setIsOk(false);
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
        int[][] stolenCrates = new int[this.stealHistorial.size()][2];
        for(int i=0; i<stealHistorial.size();i++){
            String tuple = this.stealHistorial.get(i);
            int[] tup = {Character.getNumericValue(tuple.charAt(0)),Character.getNumericValue(tuple.charAt(2))};
            stolenCrates[i] = tup;

        }

        return stolenCrates;
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
            this.planningZone.insertBox(newRow, newCol, this.planningZoneBoxColor, this.planningZoneColor);
            //this.planningZoneValues[newRow][newCol]++;           
                       
            // Re pintamos la devolución de la caja
            this.repaintPlanningZone();
            
            // Eliminamos el movimiento del historial
            this.stealHistorial.remove(this.stealHistorial.size() - 1);
            
            // La operación 'returnBox' fue exitosa
            this.setIsOk(true);
        } else {
            // Imprimimos el output
            this.printOutput("No hay cajas para devolver");
            
            // La operación 'returnBox' no fue exitosa
            this.setIsOk(false);
        }      
    }
    
    /**
     * Undo the last valid action 
     */
    public void undo(){
        // nombre-POS1,POS2
        // nombre-POS1,POS2|POS1,POS2
        // Tomamos la última acción
        String action = "";
        if (this.undoStack.size() > 0){
            action = undoStack.pop();
        } else {
            printOutput("No hay nada por deshacer");
        }
        
        // Separamos la acción por componentes
        String[] actionParts = action.split("-");   
        
        System.out.println("Action parts: " + Arrays.toString(actionParts));
        
        String name = actionParts[0];
        String positions = actionParts[1];
        
        String[] positionParts;
        String[] fromParts;
        String[] toParts;
        
        int[] from = new int[2];
        int[] to = new int[2];
        
        // Separamos las coordenadas
        if(positions.contains(";")){
            positionParts = positions.split(";");
            System.out.println("Position parts: " + Arrays.toString(positionParts));
            
            fromParts = positionParts[0].split(",");
            toParts = positionParts[1].split(",");
            
            System.out.println("fromParts parts: " + Arrays.toString(fromParts));
            System.out.println("toParts parts: " + Arrays.toString(toParts));
            
            from[0] = Integer.parseInt(fromParts[0]);
            from[1] = Integer.parseInt(fromParts[1]);
            
            to[0] = Integer.parseInt(toParts[0]);
            to[1] = Integer.parseInt(toParts[1]);
            
        } else {
            fromParts = positions.split(",");
            System.out.println("from parts: " + Arrays.toString(fromParts));
            
            from[0] = Integer.parseInt(fromParts[0]);
            from[1] = Integer.parseInt(fromParts[1]);
        } 
        
        switch(name){
            case "store":                
                this.warehouse.uncolorRefresh(from[0], from[1], this.wareHouseColor);
                this.planningZone.resetBoard(this.planningZoneBoxColor, this.planningZoneColor);
                break;
            case "steal":
                this.returnBox();
                break;
            case "arrange":
                int[] myFrom = {to[0] + 1, to[1] + 1};
                int[] myTo = {from[0] + 1, from[1] + 1};
                this.arrange(myFrom, myTo);
                System.out.println("UNDO DONE!");
                break;
        }
        
        
        
        
        // La agregamos al stack de cosas por rehacer
        redoStack.add(action);
    }
    
    /**
     * Redo the last undone action
     */
    public void redo(){
        // nombre-POS1,POS2
        // nombre-POS1,POS2|POS1,POS2
        // Tomamos la última acción
        String action = "";
        if (this.redoStack.size() > 0){
            action = redoStack.pop();
        } else {
            printOutput("No hay nada por rehacer");
        }
        
        
        // Separamos la acción por componentes
        String[] actionParts = action.split("-");   
        
        System.out.println("Action parts: " + Arrays.toString(actionParts));
        
        String name = actionParts[0];
        String positions = actionParts[1];
        
        String[] positionParts;
        String[] fromParts;
        String[] toParts;
        
        int[] from = new int[2];
        int[] to = new int[2];
        
        // Separamos las coordenadas
        if(positions.contains(";")){
            positionParts = positions.split(";");
            System.out.println("Position parts: " + Arrays.toString(positionParts));
            
            fromParts = positionParts[0].split(",");
            toParts = positionParts[1].split(",");
            
            from[0] = Integer.parseInt(fromParts[0]);
            from[1] = Integer.parseInt(fromParts[1]);
            
            to[0] = Integer.parseInt(toParts[0]);
            to[1] = Integer.parseInt(toParts[1]);
            
        } else {
            fromParts = positions.split(",");
            System.out.println("from parts: " + Arrays.toString(fromParts));
            
            from[0] = Integer.parseInt(fromParts[0]);
            from[1] = Integer.parseInt(fromParts[1]);
        } 
        
        switch(name){
            case "store":
                //this.warehouse.removeBox(from[0], from[1], true);
                this.store(from[0] + 1, from[1] + 1);
                this.planningZone.resetBoard(this.planningZoneBoxColor, this.planningZoneColor);
                //this.warehouse.refreshBoard(this.wareHouseColor, this.wareHouseColor);
                break;
            case "steal":
                this.steal(from[0] + 1, from[1] + 1);                
                break;
            case "arrange":
                int[] myFrom = {from[0] + 1, from[1] + 1};
                int[] myTo = {to[0] + 1, to[1] + 1};
                this.arrange(myFrom, myTo);
        }
        
        // La agregamos al stack de cosas por deshacer
        undoStack.add(action);
    }
    
    /**
     * Method for repainting the planning zone
     */
    private void repaintPlanningZone(){
        // Re seteamos el color
        this.resetPlanningZoneColor();       
         
        // Re coloreamos la vista frontal de la zona de planeación
        this.planningZone.colorFrontView(this.planningZoneBoxColor, this.planningZoneColor);
        
        // Re coloreamos la vista lateral de la zona de planeación
        this.planningZone.colorSideView(this.planningZoneBoxColor, this.planningZoneColor);
        
        // Re coloreamos la vista superior de la zona de planeación
        this.planningZone.colorTopView(this.planningZoneBoxColor, this.planningZoneColor);
        
        // Verificamos si son iguales las bodegas
        this.verifyEquality();
        
        // Re seteamos el color
        this.resetPlanningZoneColor();       
         
        // Re coloreamos la vista frontal de la zona de planeación
        this.planningZone.colorFrontView(this.planningZoneBoxColor, this.planningZoneColor);
        
        // Re coloreamos la vista lateral de la zona de planeación
        this.planningZone.colorSideView(this.planningZoneBoxColor, this.planningZoneColor);
        
        // Re coloreamos la vista superior de la zona de planeación
        this.planningZone.colorTopView(this.planningZoneBoxColor, this.planningZoneColor);
    }
    
    /**
     * Method for zooming in the warehouse
     * @param z     Char -> '+': 10% zoom in, '-': 10% zoom out
     */
    public void zoom(char z){
        switch(z){
            case '+':
                // Aumentamos el tamaño
                this.size += 2;
                
                this.warehouse.zoom(z);
                this.planningZone.zoom(z);
                
                this.refreshBoards();
                break;
            case '-':
                // Aumentamos el tamaño
                this.size -= 2;
                
                this.warehouse.zoom(z);
                this.planningZone.zoom(z);
                
                this.refreshBoards();
                break;
            default:
                this.printOutput("¡Ingrese una opción válida para el zoom! ('+', '-')");
                break;
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
                if (this.planningZone.getValues()[oldRow][oldCol] > 0){
                    // Retiramos una caja de esa posición
                    this.planningZone.removeBox(oldRow, oldCol);
                    //this.planningZoneValues[oldRow][oldCol]--;
                    
                    // Agregamos la caja a la nueva posición
                    this.planningZone.insertBox(newRow, newCol, this.planningZoneBoxColor, this.planningZoneColor);
                    //this.planningZoneValues[newRow][newCol]++;
                    
                    // Guardamos la información para undo/redo
                    // nombre-POS1,POS2;POS1,POS2
                    String res = "arrange-" + oldRow + "," + oldCol + ";" + newRow + "," + newCol;
                    this.undoStack.push(res);
                    
                    // Re pintamos la zona de planeación
                    this.repaintPlanningZone();
                    
                    // La operación 'arrange' fue exitosa
                    this.setIsOk(true);
                    
                } else {
                    // Imprimimos el output
                    this.printOutput("¡No hay cajas para ubicar en esa posición!");
                    
                    // La operación 'arrange' no fue exitosa
                    this.setIsOk(false);
                }  
            } else {
                // Imprimimos el output
                this.printOutput("La posición 'to' no es válida");
                
                // La operación 'arrange' no fue exitosa
                this.setIsOk(false);
            }
        } else {
            // Imprimimos el output
            this.printOutput("La posición 'from' no es válida"); 
            
            // La operación 'arrange' no fue exitosa
            this.setIsOk(false);
        } 
        
        
    }
    
    /**
     * Verify if the position is valid 
     * @return True if it is a reachable position, false otherwise
     */
    private boolean isValidPosition(int row, int col){
        boolean res = false;        
        
        if(((0 <= row) && (row < this.rows)) && ((0 <= col) && (col < this.cols))){
            res = true;
        }
        
        return res;
    }
    
    /**
    * Return total of stolen boxes
    * @return  Total of stolen boxed
    */
    public int stolen(){
        if(this.planningZone.getStolenBoxes() == 1){
            // Imprimimos el output
            this.printOutput("Se ha robado 1 caja");
        } else {
            // Imprimimos el output
            this.printOutput("Se han robado " + this.planningZone.getStolenBoxes() + " cajas");            
        }
        
        return this.planningZone.getStolenBoxes();
    }
    
    /**
    * Checks the warehouse matrix
    * @return  the warehouse values matrix
    */
    public int[][] warehouse(){    
        // Contamos la cantidad de cajas en la bodega
        int numBoxes = this.countBoxes(this.warehouse.getValues());
        
        // Mostramos el resultado en pantalla
        if(numBoxes == 1){
            // Imprimimos el output
            this.printOutput("En la bodega hay 1 caja");            
        } else {
            // Imprimimos el output
            this.printOutput("En la bodega hay " + numBoxes + " cajas");            
        }        
        
        //System.out.println("Warehouse values: " + Arrays.toString(this.warehouse.getValues()));       
        
        
        return this.warehouse.getValues();
    }
    
    /**
    * Checks the amount of boxes in the planning zone
    * @return  The matrix with the values of the planning zone
    */
    public int[][] layout(){ 
        // Contamos la cantidad de cajas en la zona de planeación
        int numBoxes = this.countBoxes(this.planningZone.getValues());
        
        // Mostramos el resultado en pantalla
        if(numBoxes == 1){
            // Imprimimos el output
            this.printOutput("En la zona de planeación hay 1 caja");
        } else {
            // Imprimimos el output
            this.printOutput("En la zona de planeación hay " + numBoxes + " cajas");
        } 
       
        return this.planningZone.getValues();
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
                // Apagamos la bodega
                this.warehouse.makeVisible();
                
                // Apagamos la zona de planeación
                this.planningZone.makeVisible();                              
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
                this.warehouse.makeInvisible();
                
                // Apagamos la zona de planeación
                this.planningZone.makeInvisible();               
            }
        }
    }
    
    /**
    * Method for finishing the simulator
    */
    public void finish(){
        this.printOutput("Terminando simulador...");         
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
     * @param The new value for 'isOk'
     */
    public void setIsOk(boolean isOk){
        this.isOk = isOk;
    }
    
    /**
     * Getter for the 'inputsAndNotificationsEnabled' attribute
     * @return True if the inputs and notifications are enabled, false otherwise
     */
    private boolean getInputsAndNotificationsEnabled(){
        return this.inputsAndNotificationsEnabled;
    }
    
    
    /**
     * Toggle notification pop-ups ON/OFF (default is OFF)
     */
    public void toggleInputsAndNotifications(){
        this.inputsAndNotificationsEnabled = !this.inputsAndNotificationsEnabled;
    }
    
    /**
     * Method for printing messages for the user
     * @param The message to be displayed
     */
    static void printOutput(String message){
        // Si las notificaciones están habilitadas, las mostramos
        if (Mission.inputsAndNotificationsEnabled){
            JOptionPane.showMessageDialog(null, message); 
        } else {
            System.out.println(message);
        }  
    }
    
    /**
     * Getter for the 'size' attribute
     */
    public int getSize(){
        return this.size;
    }    
    
}
