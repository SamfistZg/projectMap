package database;
import java.sql.SQLException;

/**
 * Classe che rappresenta un'eccezione personalizzat che viene sollevata quando la connessione al db fallisce.
 */
public class DatabaseConnectionException extends Exception {
    /**
     * Costruttore di DatabaseConnectionException.
     * @param message   stringa contenente il messaggio da stampare
     * @param e     ClassNotFoundException
     */
    DatabaseConnectionException(String message, ClassNotFoundException e) {
        super(message, e);
    }

    /**
     * Costruttore di DatabaseConnectionException.
     * @param message   striga contenente il messaggio da stampare 
     * @param e     SQLException
     */
    DatabaseConnectionException(String message, SQLException e) {
        super(message, e);
    }
}
