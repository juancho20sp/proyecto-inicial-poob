package mission;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class missionCC4test.
 *
 * @author  Juan David Murillo y Carlos Orduz
 * @version (a version number or a date)
 */
public class missionCC4test
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
        Box box = new Box(1,1);
        int totalBoxes = 0;
        
        // Guardamos una caja en una posición válida
        // Usamos la primera versión del método
        mission.store(box);
        
        // Verificamos que se haya guardado
        // Restamos 1 a cada posición porque según el negocio los 
        // índices empiezan en 1
        
        assertEquals(++totalBoxes, mission.warehouse()[row - 1][col - 1]);
        
        
        // Guardamos una caja en una posición válida
        // Usamos la segunda versión del método
        Box box1 = new Box(1,1);
        Box[] boxes = {box1};
        mission.store(boxes);
        
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
        Box box1 =new Box(1,1);
        Box box2 = new Box(2,2);
        Box box3 = new Box(1,1);
        Box box4 = new Box(1,3);
        mission.store(box1);
        mission.store(box2);
        mission.store(box3);
        mission.store(box4);
        
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
        Box[] boxes = {new Box(1,1),new Box(1,1),new Box(1,1),new Box(1,2),new Delicate(2,2)};
        Box box = new Box(2,2);
        mission.store(boxes[0]);
        mission.store(boxes[1]);
        mission.store(boxes[2]);
        mission.store(boxes[3]);
        mission.store(boxes[4]);
        
        // Copiamos la bodega
        mission.copy();
        
        // Tomamos una copia de la zona de planeación para modificarla
        this.copyPlanningZone();
        
        
        // Robamos una caja        
        mission.steal(boxes[0]);
        
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
        
        mission.steal(boxes[1]);        
        mission.steal(boxes[2]);        
        mission.steal(boxes[3]);
        mission.steal(boxes[4]);
        mission.steal(box);
        
        
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
