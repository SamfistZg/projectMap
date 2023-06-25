package database;
//import java.sql.ResultSet;
//import java.sql.SQLException;

public class EmptySetException extends Exception{
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
