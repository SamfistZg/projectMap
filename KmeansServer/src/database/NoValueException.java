package database;
//import java.sql.SQLException;

public class NoValueException extends Exception {
    /* chiedere il perché dell'SQLException, nel frattempo commentato e commentato import
    NoValueException(String message, SQLException e) {
        super(message, e);
    }
    */
    NoValueException(String message) {
        super(message);
    }
}
