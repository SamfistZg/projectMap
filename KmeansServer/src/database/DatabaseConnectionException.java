package database;
import java.sql.SQLException;

/**
 * Classe che rappresenta un'eccezione personalizzata, chiaramente estende Exception.
 */
public class DatabaseConnectionException extends Exception {
    /**
     * Costruttore di DatabaseConnectionException.
     * @param message   stringa contenente il messaggio da stampare in cui venga sollevata
     * @param e     ClassNotFoundException
     */
    DatabaseConnectionException(String message, ClassNotFoundException e) {
        super(message, e);
    }

    /**
     * Costruttore di DatabaseConnectionException.
     * @param message   stringa contenente il messaggio da stampare in cui venga sollevata 
     * @param e     SQLException
     */
    DatabaseConnectionException(String message, SQLException e) {
        super(message, e);
    }
}
