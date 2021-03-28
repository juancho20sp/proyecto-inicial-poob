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
    public static final String EMPTY_WAREHOUSE = "Debe ingresar valores al tablero";
    public static final String INVALID_POS = "La posición ingresada no es válida";
    public static final String NOTHING_TO_UNDO = "No hay nada por deshacer";
    public static final String NOTHING_TO_REDO = "No hay nada por rehacer";
    public static final String INVALID_ZOOM_OPTION = "¡Ingrese una opción válida para el zoom! ('+', '-')";
    public static final String NO_BOXES_TO_PLACE = "¡No hay cajas para ubicar en esa posición!";
    
    public static final String INVALID_TO_POSITION = "La posición 'to' no es válida";
    public static final String INVALID_FROM_POSITION = "La posición 'from' no es válida";
    
    public static final String INVALID_CHARACTER = "El caracter ingresado no es válido, ingrese 't' o 's' o 'f' ";
    
    /**
     * Constructor for the class
     */
    public MissionException(String msg){
        super(msg);
    }
}
