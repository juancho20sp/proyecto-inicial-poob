package mission;


/**
 * Class for creating all the exceptions of the package
 * 
 * @author Juan David Murillo - Carlos Orduz
 * @version 1.0
 */
public class MissionException extends Exception
{
    public static final String NADA_PARA_ROBAR = "¡No hay nada para robar en esa posición!";
    
    /**
     * Constructor for the class
     */
    public MissionException(String msg){
        super(msg);
    }
}
