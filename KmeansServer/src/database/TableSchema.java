package database;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe che rappresenta il database.
 */
public class TableSchema {
	DbAccess db;

	/**
	 * Inner class che rappresenta una colonna di una tabella.
	 */
	public class Column {
		private String name;
		private String type;

		/**
		 * Costruttore di Column.
		 * @param name 	nome della colonna
		 * @param type 	tipo della colonna
		 */
		Column(String name, String type) {
			this.name = name;
			this.type = type;
		}

		/**
		 * Funzione che restituisce il nome della colonna.
		 * @return
		 */
		public String getColumnName() {
			return name;
		}

		/**
		 * Funzione che restituisce true se il tipo della colonna è un number, false altrimenti.
		 * @return
		 */
		public boolean isNumber() {
			return type.equals("number");
		}

		/**
		 * Funzione che stampa nome e tipo della colonna, è un override.
		 */
		public String toString() {
			return name + ": " + type;
		}
	}

	List<Column> tableSchema = new ArrayList<Column>();
	
	/**
	 * Funzione che inserisce in tableSchema i tipi.
	 * @param db 	database a cui ci si vuole connettere.
	 * @param tableName 	nome della tabella che si vuole riempire
	 * @throws SQLException
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException {

		this.db = db;
		HashMap<String,String> mapSQL_JAVATypes = new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");
		
		Connection con = db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);
		
		while (res.next()) {
			if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
					tableSchema.add(new Column(
							res.getString("COLUMN_NAME"),
							mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
							);
			}
			res.close();
	    }
	
	/**
	* Funzione che resituisce il numero di Attributes.
	* @return int 	numero di attributes 
	*/
	public int getNumberOfAttributes() {
		return tableSchema.size();
	}
		
	/**
	* Funzione che restituisce la colonna di tableShema al indice index passato in input.
	* @param index 	indice
	* @return Column 	colonna al indice index 
	*/
	public Column getColumn(int index) {
		return tableSchema.get(index);
	}
}
