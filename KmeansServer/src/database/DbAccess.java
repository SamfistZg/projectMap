package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe che rappresenta tutte le informazioni necessaria per la connessione al database.
 */
public class DbAccess {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private final String DBMS = "jdbc:mysql";
    private final String SERVER = "localhost";
    private final String DATABASE = "MapDB";
    private final String PORT= "3306";
    private final String USER_ID = "MapUser";
    private final String PASSWORD = "map";
    private Connection conn;

    /**
     * Metodo che inizializza la connessione al db.
     * @throws DatabaseConnectionException  eccezione personalizzata che viene sollevata nel caso in cui la connessione al db fallisca
     */
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

    /**
     * Funzione che restituisce la connessione.
     * @return conn     Connection
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * Metodo che chiude la connessione al db.
     */
    public void closeConnection() {
        try {
            conn.close();
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
        }
    }
}
