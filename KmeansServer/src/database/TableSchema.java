package database;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe principale che rappresenta lo schema della tabella del db.
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
		 * @param name	nome della colonna
		 * @param type nome del tipo 
		 */
		Column(String name, String type) {
			this.name = name;
			this.type = type;
		}

		/**
		 * Funzione che restituisce il nome della colonna.
		 * @return name 	nome della colonna 
		 */
		public String getColumnName() {
			return name;
		}

		/**
		 * Funzione che restituisce true se il tipo è un number, false altrimenti.
		 * @return true o false
		 */
		public boolean isNumber() {
			return type.equals("number");
		}

		/**
		 * Funzione che restituisce il nome e tipo.
		 * @return String
		 */
		public String toString() {
			return name + ": " + type;
		}
	}

	List<Column> tableSchema = new ArrayList<Column>();
	
	/**
	 * Funzione che riempie tableSchema.
	 * @param db 	database di riferimento
	 * @param tableName 	nome della tabella di riferimento
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
		 * Funzione che restituisce la dimensione di tableSchema nonchè il numero di attributi presenti (new getNumberOfAttributes).
		 */
		public int getNumberOfAttributes() {
			return tableSchema.size();
		}
		
		/**
		 * Funzione che restituisce la colonna al indice index.
		 * @param index 	indice in cui si vuole restituire la colonna
		 * @return Column 	colonna al indice index
		 */
		public Column getColumn(int index) {
			return tableSchema.get(index);
		}
	}
	