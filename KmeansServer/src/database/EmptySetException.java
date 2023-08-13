package database;
//import java.sql.ResultSet;
//import java.sql.SQLException;

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

    /* commentato perché sollevata dove opportuno, commentati gli import correlati, commentato il toString perché prediletto il getMessage

    public String toString( ) {
        return "Empty set";
    }
    public static void emptySet(ResultSet res) throws EmptySetException, SQLException {
        if (!res.next()) {
            throw new EmptySetException("Empty set");
        }
    }
    */
}
