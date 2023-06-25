package database;
import java.sql.SQLException;

public class DatabaseConnectionException extends Exception {
    DatabaseConnectionException(String message, ClassNotFoundException e) {
        super(message, e);
    }

    DatabaseConnectionException(String message, SQLException e) {
        super(message, e);
    }
}
