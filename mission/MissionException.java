package mission;


/**
 * Class for creating all the exceptions of the package
 * 
 * @author Juan David Murillo - Carlos Orduz
 * @version 1.0
 */
public class MissionException extends Exception
{
    public static final String NOTHING_TO_STEAL = "¡No hay nada para robar en esa posición!";
    public static final String INVALID_POSITION = "Posición no existente, inténtelo nuevamente";
    public static final String NOTHING_TO_RETURN = "No hay cajas para devolver";
    
    /**
     * Constructor for the class
     */
    public MissionException(String msg){
        super(msg);
    }
}
