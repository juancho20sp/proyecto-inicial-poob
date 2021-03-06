import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

/**
 * The test class MissionTestC2.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class MissionTestC2
{
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    private int rows = 3;
    private int cols = 3;
    
    private Mission mission;
    private int[][] planningZone;
    
    /**
     * Creates a copy of the mission's planning zone
     */
    private void copyPlanningZone() {
        for(int i = 0; i < mission.layout().length; i++){
            for(int j = 0; j < mission.layout()[0].length; j++){
                planningZone[i][j] = mission.layout()[i][j];
            }
        }
    }
    
    @Before
    public void setUp(){
        // Creamos la instancia de la clase
        mission = new Mission(this.rows, this.cols);
        
        // Creamos el croquis de la zona de planeación
        planningZone = new int[this.rows][this.cols];
        
        mission.makeInvisible();
    }
    
    
    /**
     * Test for creating boards
     * @result The boards were created correctly.
     */
    @Test    
    public void shouldCreateBoardsMG(){
        // Verificamos los tamaños de los tableros de valores
        // Warehouse values
        assertEquals(rows, mission.warehouse().length);
        assertEquals(cols, mission.warehouse()[0].length);
        
        // Warehouse Front
        assertEquals(rows, mission.layout().length);
        assertEquals(cols, mission.layout()[0].length);       
        
        // Verificamos que el tablero de warehouse esté lleno de 0
        assertEquals(0, mission.warehouse()[0][0]);
        assertEquals(0, mission.warehouse()[rows - 1][cols - 1]);
         
    }
    
    /**
     * Test for storing boxes on the warehouse
     * @result The box was stored correctly.
     */
    @Test
    public void shouldStoreMG() {
        int row = 1;
        int col = 1;
        
        int totalBoxes = 0;
        
        // Guardamos una caja en una posición válida
        // Usamos la primera versión del método
        mission.store(row, col);
        
        // Verificamos que se haya guardado
        // Restamos 1 a cada posición porque según el negocio los 
        // índices empiezan en 1
        assertEquals(++totalBoxes, mission.warehouse()[row - 1][col - 1]);
        
        
        // Guardamos una caja en una posición válida
        // Usamos la segunda versión del método
        int[] position = {row, col};
        mission.store(position);
        
        // Verificamos que se haya guardado
        // Restamos 1 a cada posición porque según el negocio los 
        // índices empiezan en 1
        assertEquals(++totalBoxes, mission.warehouse()[row - 1][col - 1]);
    }
    
    /**
     * Test for copying the warehouse
     * @result The boards were copied correctly.
     */
    @Test
    public void shouldCopyMG(){
        int[][] warehouse = mission.warehouse();
        
        // Copiamos el estado del warehouse
        mission.copy();        
              
        // Verificamos la igualdad componente a componente
        assertArrayEquals(warehouse, mission.layout());
        
        // Agregamos 4 cajas a la bodega 
        mission.store(1,1);
        mission.store(1,2);
        mission.store(1,1);
        mission.store(1,3);
        
        // Copiamos el estado del warehouse
        mission.copy();
        
        // Verificamos la igualdad componente a componente
        assertArrayEquals(warehouse, mission.layout());
    }
    
    /**
     * Test for stealing a box of the planning zone.
     * @result The box was stealed successfully.
     */
    @Test
    public void shouldStealMG() {
        int stolenBoxes = 0;
        
        // Agregamos 5 cajas a la bodega
        mission.store(1,1);
        mission.store(1,1);
        mission.store(1,1);
        mission.store(1,2);
        mission.store(2,2);
        
        // Copiamos la bodega
        mission.copy();
        
        // Tomamos una copia de la zona de planeación para modificarla
        this.copyPlanningZone();
        
        
        // Robamos una caja
        mission.steal(1, 1);
        
        // Restamos 1 a nuestra copia
        this.planningZone[0][0] -= 1;
        
        // Agregamos 1 a la cantidad de cajas robadas
        stolenBoxes += 1;
        
        // Verificamos que sean iguales
        assertArrayEquals(this.planningZone,  mission.layout());
        
        // Verificamos que el contador de cajas robadas haya aumentado
        assertEquals(stolenBoxes, mission.stolen());
        
        
        // Robamos más cajas de las posibles
        int[] position = {1,1};
        mission.steal(position);        
        mission.steal(1, 1);        
        mission.steal(position);
        mission.steal(1, 1);
        mission.steal(position);
        
        // Seteamos el valor en 0 en esa posición
        this.planningZone[0][0] = 0;
        
        // Sumamos el total de cajas robadas
        stolenBoxes += 2;
        
        // Verificamos que sean iguales
        assertArrayEquals(this.planningZone,  mission.layout());
        
        // Verificamos que el contador de cajas robadas haya aumentado        
        assertEquals(stolenBoxes, mission.stolen()); 
        
    }
    
    /**
     * Test for returning the last stolen box.
     * @result The box was returned successfully.
     */
    @Test
    public void shouldReturnBoxMG(){
        int row = 1;
        int col = 1;
        
        // Agregamos una caja a la bodega
        mission.store(row, col);
        
        // Copiamos la bodega a la zona de planeación
        mission.copy();
        
        // Creamos una copia local de la zona de planeación
        this.copyPlanningZone();
        
        // Robamos la caja
        mission.steal(row, col);
        
        // La eliminamos de la copia local
        this.planningZone[row - 1][col - 1]--;
        
        // Verificamos que la zona de planeación de mission y la zona de planeación local sean iguales
        assertArrayEquals(this.planningZone, mission.layout());
        
        // Devolvemos la caja en mission
        mission.returnBox();
        
        // Devolvemos la caja en la copia local
        this.planningZone[row - 1][col - 1]++;

        // Verificamos que la zona de planeación de mission y la zona de planeación local sean iguales
        assertArrayEquals(this.planningZone, mission.layout());                
    }
    
    /**
     * Test for arranging the planning zone.
     * @result The box was arranged successfully.
     */
    @Test
    public void shouldArrangeMG(){
        int row = 1;
        int col = 1;
        
        // Agregamos una caja a la bodega
        mission.store(row, col);
        
        // Copiamos la bodega
        mission.copy();
        
        // Creamos una copia local de la zona de planeación
        this.copyPlanningZone();
        
        // Movemos la caja en mission
        int[] from = {1,1};
        int[] to = {2,2};
        
        mission.arrange(from, to);
        
        // Movemos la caja en local
        this.planningZone[from[0] - 1][from[1] - 1]--;
        
        this.planningZone[to[0] - 1][to[0] - 1]++;
        
        // Verificamos que ambas zonas sean iguales        
        assertArrayEquals(this.planningZone, mission.layout());
        
    }
    
    /**
     * Test for verifying the positions of the stolen boxes
     * @result the positions of the stolen boxes are valid
     */    
    @Test
    public void shouldConsultPositionOT(){
        mission.store(1, 1);
        mission.store(1, 2);

        mission.copy();
        this.copyPlanningZone();

        mission.steal(1, 1);
        mission.steal(1, 2);

        this.planningZone[0][0] -= 1;
        this.planningZone[0][1] -= 1;

        int[][] answer = {{0,0},{0,1}};

        assertArrayEquals(answer, mission.toSteal());

    }
    
    /**
     * Verifies if the undo/redo method works for the store method
     * @result True if works, false otherwise
     */
    @Test
    public void shouldUndoRedoStoreMG(){
        int row = 1;
        int col = 1;
                
        // Agregamos una caja a la bodega
        mission.store(row, col);        
        
        // Eliminamos la caja de la bodega
        mission.undo();   
        
        // Al editar la bodega debemos hacer un reset de la zona de planeación
        int[][] res = {{0,0,0},{0,0,0},{0,0,0}};         
        assertArrayEquals(res, mission.warehouse());
                
        // Al hacer redo volvemos la caja a la posición (1,1) de la bodega
        mission.redo();
        
        int[][] res2 = {{1,0,0},{0,0,0},{0,0,0}};
        assertArrayEquals(res2, mission.warehouse());
        
        // La función copy debe dejar la zona de planeación con una caja en la posición (1,1)
        mission.copy();        
        assertArrayEquals(res2, mission.layout());     
    }
    
    /**
     * Verifies if the undo/redo method works for the steal method
     * @result True if works, false otherwise
     */
    @Test
    public void shouldUndoRedoStealMG(){
        int row = 1;
        int col = 1;
        
        // Agregamos una caja a la bodega
        mission.store(row, col);
        
        // Copiamos la bodega
        mission.copy();
        
        // Eliminamos la caja de la bodega
        mission.steal(row, col);        
        
        // Al editar la bodega debemos hacer un reset de la zona de planeación
        int[][] res = {{0,0,0},{0,0,0},{0,0,0}};    
        
        assertArrayEquals(res, mission.layout());
        
        // Al hacer undo volvemos la caja a la posición (1,1) de la bodega
        mission.undo();
        
        int[][] res2 = {{1,0,0},{0,0,0},{0,0,0}};
        assertArrayEquals(res2, mission.layout());
        
        // Al hacer redo volvemos a robar la caja de la posición (1,1) de la zona de planeación
        mission.redo();        
        assertArrayEquals(res, mission.layout()); 
    }
    
    /**
     * Verifies if the undo/redo method works for the arrange method
     * @result True if works, false otherwise
     */
    @Test
    public void shouldUndoRedoArrangeMG(){
        int row = 1;
        int col = 1;
        
        // Agregamos una caja a la bodega
        mission.store(row, col);
        
        // Copiamos la bodega
        mission.copy();
        
        // Eliminamos la caja de la bodega
        int[] from = {row, col};
        int[] to = {row+1, col+1};
        
        mission.arrange(from, to);        
        
        int[][] res = {{0,0,0},{0,1,0},{0,0,0}};
        assertArrayEquals(res, mission.layout());
        
        // Al hacer undo volvemos la caja a la posición (1,1) de la bodega
        mission.undo();
        
        int[][] res2 = {{1,0,0},{0,0,0},{0,0,0}};
        assertArrayEquals(res2, mission.layout());
        
        // Al hacer redo volvemos a dejar la caja en la posición (2,2) de la zona de planeación
        mission.redo();        
        assertArrayEquals(res, mission.layout()); 
    }
    
    /**
     * Verifies if the undo/redo method works for the copy method
     * @result True if works, false otherwise
     */
    @Test
    public void shouldUndoRedoCopyMG(){
        int row = 1;
        int col = 1;
        
        // Agregamos una caja a la bodega
        mission.store(row, col);
        
        // La zona de planeación inicia vacía
        int[][] res = {{0,0,0},{0,0,0},{0,0,0}};
        assertArrayEquals(res, mission.layout());
        
        // Copiamos la bodega
        mission.copy();
                   
        // Luego de copiar debemos tener la posición (1,1) llena
        int[][] res2 = {{1,0,0},{0,0,0},{0,0,0}};
        assertArrayEquals(res2, mission.layout());
                
        
        // Al hacer undo la zona de planeación queda vacía
        mission.undo();
        assertArrayEquals(res, mission.layout());
        
        // Al hacer redo volvemos a tener una caja en la posición (1,1)
        mission.redo();        
        assertArrayEquals(res2, mission.layout()); 
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        mission.makeInvisible();
    }
}
