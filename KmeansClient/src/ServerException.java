/**
 * Classe che rappresenta un'eccezione personalizzata che viene sollevata quando la connessione al server fallisce.
 */
public class ServerException extends Exception {
    /**
     * Costruttore di ServeException
     * @param m stringa che si vuole stampare nel caso venga sollevata.
     */
    ServerException(String m) {
        super(m);
    }
}
