package database;

/**
 * Classe che rappresenta un eccezione personalizzata che viene sollevata quando il Resultset risulta vuoto.
 */
public class EmptySetException extends Exception {
    /**
     * Costruttore di EmptySetException.
     * @param message   stringa contenente il messaggio di errore da stampare in caso venga sollevata
     */
    EmptySetException(String message) {
        super(message);
    }
}
