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
     * Verifies if two arrays are equal.
     * @param   The first array.
     * @param   The second array
     */
    private boolean areEqualArrays(int[][] array1, int[][] array2){
        boolean areEqual = true;
        
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                if (array1[i][j] != array2[i][j]){
                    areEqual = false;
                    break;
                }                
            }
        }
        
        return areEqual;
    }
    
    
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
        
        // Mantenemos el tablero invisible
        //mission.makeInvisible();
    }
    
    
    /**
     * Test for creating boards
     * @result The boards were created correctly.
     */
    @Test    
    public void shouldCreateBoards(){
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
    public void shouldStore() {
        int row = 1;
        int col = 1;
        
        int totalBoxes = 0;
        
        // Guardamos una caja en una posición válida
        // Usamos la primera versión del método
        mission.store(row, col);
        
        // Verificamos que se haya guardado
        // Restamos 1 a cada posición porque según el negocio los 
        // índices empiezan en 1
        System.out.println("test 1: " + mission.warehouse()[row - 1][col - 1]);
        assertEquals(++totalBoxes, mission.warehouse()[row - 1][col - 1]);
        
        
        // Guardamos una caja en una posición válida
        // Usamos la segunda versión del método
        int[] position = {row, col};
        mission.store(position);
        
        System.out.println("HOliw" + mission.warehouse()[row - 1][col - 1]);
        
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
    public void shouldCopy(){
        int[][] warehouse = mission.warehouse();
        
        // Copiamos el estado del warehouse
        mission.copy();        
              
        // Verificamos la igualdad componente a componente
        assertEquals(true, areEqualArrays(warehouse, mission.layout()));
        
        
        // Agregamos 4 cajas a la bodega 
        mission.store(1,1);
        mission.store(1,2);
        mission.store(1,1);
        mission.store(1,3);
        
        // Copiamos el estado del warehouse
        mission.copy();
        
        // Verificamos la igualdad componente a componente
        assertEquals(true, areEqualArrays(warehouse, mission.layout()));
                
    }
    
    /**
     * Test for stealing a box of the planning zone.
     * @result The box was stealed successfully.
     */
    @Test
    public void shouldSteal() {
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
        assertEquals(true, this.areEqualArrays(this.planningZone,  mission.layout()));
        
        
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
        assertEquals(true, this.areEqualArrays(this.planningZone,  mission.layout()));
        
        
        // Verificamos que el contador de cajas robadas haya aumentado        
        assertEquals(stolenBoxes, mission.stolen()); 
        
    }
    
    /**
     * Test for returning the last stolen box.
     * @result The box was returned successfully.
     */
    @Test
    public void shouldReturnBox(){
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
        assertEquals(true, this.areEqualArrays(this.planningZone, mission.layout()));
        
        // Devolvemos la caja en mission
        mission.returnBox();
        
        // Devolvemos la caja en la copia local
        this.planningZone[row - 1][col - 1]++;

        // Verificamos que la zona de planeación de mission y la zona de planeación local sean iguales
        assertEquals(true, this.areEqualArrays(this.planningZone, mission.layout()));
        
        
    }
    
    /**
     * Test for arranging the planning zone.
     * @result The box was arranged successfully.
     */
    @Test
    public void shouldArrange(){
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
        assertEquals(true, this.areEqualArrays(this.planningZone, mission.layout()));
        
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        //mission.makeInvisible();
    }
}
