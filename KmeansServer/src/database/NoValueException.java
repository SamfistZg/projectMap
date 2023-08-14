package database;

/**
 * Classe che rappresenta un'eccezione personalizzata che viene sollevata quando il dataset contiene un valore null o non valido.
 */
public class NoValueException extends Exception {
    /**
     * Costruttore di NoValueException.
     * @param message   stringa contenente il messaggio da stampare in caso venga sollevata
     */
    NoValueException(String message) {
        super(message);
    }
}
