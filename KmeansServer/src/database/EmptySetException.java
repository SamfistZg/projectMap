package database;

/**
 * Classe che rappresenta un' eccezione personalizzata che viene lanciata nel caso in cui il ResultSet risulti vuoto.
 */
public class EmptySetException extends Exception{
    /**
     * Costruttore di EmptySetException.
     * @param message   stringa contenente il messaggio da stampare
     */
    EmptySetException(String message) {
        super(message);
    }
}
