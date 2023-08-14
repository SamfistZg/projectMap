package database;

/**
 * Classe che rappresenta un'eccezione personalizzata, viene lanciata quando nel dataset è presente un valore null o non valido.
 */
public class NoValueException extends Exception {
    /**
     * Costruttore di NoValueException.
     * @param message   stringa contenente il messaggio da stampare in caso venga sollevata l'eccezione.
     */
    NoValueException(String message) {
        super(message);
    }
}
