package database;
//import java.sql.SQLException;

/**
 * Classe che rappresenta un'eccezione personalizzata che viene sollevata quando il dataset contiene un valore null o non valido.
 */
public class NoValueException extends Exception {
    /* chiedere il perché dell'SQLException, nel frattempo commentato e commentato import
    NoValueException(String message, SQLException e) {
        super(message, e);
    }
    */

    /**
     * Costruttore di NoValueException.
     * @param message   stringa contenente il messaggio da stampare in caso venga sollevata
     */
    NoValueException(String message) {
        super(message);
    }
}
