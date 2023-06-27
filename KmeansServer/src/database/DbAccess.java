package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAccess {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private final String DBMS = "jdbc:mysql";
    private final String SERVER = "localhost";
    private final String DATABASE = "MapDB";
    private final String PORT= "3306";
    private final String USER_ID = "MapUser";
    private final String PASSWORD = "map";
    private Connection conn;

    public void initConnection( ) throws DatabaseConnectionException {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch(ClassNotFoundException e) {
            throw new DatabaseConnectionException("Driver non trovato", e);
        } 
        String url = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
        try {
            conn = DriverManager.getConnection(url);
        } catch(SQLException s) {
            throw new DatabaseConnectionException("Database non trovato", s);
        }
    }

    public Connection getConnection() {
        return conn;
    }
    public void closeConnection() {
        try {
            conn.close();
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage()); //fatto il print del messaggio stile DBAccess.txt
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
        }
    }
}
