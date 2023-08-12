package database;
import java.sql.ResultSet; //P
import java.sql.SQLException; //P
import java.sql.Statement; //P
import java.util.ArrayList; //P
import java.util.List; //P
import java.util.TreeSet; //P
import database.TableSchema.Column; //P

/**
 * Classe che rappresenta la tabella dati acquisita dalla connessione al db.
 */
public class TableData {
	DbAccess db;

	/**
	 * Costruttore di TableData.
	 * @param db 	DbAccess
	 */
	public TableData(DbAccess db) {
		this.db = db;
	}

	/**
	 * Funzione che ricava lo schema di table, va a estrarre tutti i dati contenuti in table 
	 * e va a inserire in una lista di example solo le tuple distinte.
	 * @param table 	nome della tabella
	 * @throws SQLException
	 * @throws EmptySetException 	eccezione che viene sollevata nel caso in cui ResultSet sia vuoto
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException {
		TableSchema schema = new TableSchema(db, table);
		List<Example> distinctTransazioni = new ArrayList<Example>();
		Statement stmt = db.getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT DISTINCT * FROM " + table);
		if (!resultSet.next()) { //lancia EmptySetException 
            throw new EmptySetException("ResultSet vuoto!");
        }
		do {
			Example e = new Example();
			for (int i = 0; i < schema.getNumberOfAttributes(); i++) {
				if (schema.getColumn(i).isNumber()) {
					e.add(resultSet.getFloat(schema.getColumn(i).getColumnName()));
				} else {
					e.add(resultSet.getString(schema.getColumn(i).getColumnName()));
				}
			}
			distinctTransazioni.add(e);
		} while (resultSet.next());
		resultSet.close();
		stmt.close(); 
		return distinctTransazioni;
	}

	/**
	 * Funzione che restituisce un Set di valori distinti di una specifica colonna.
	 * @param table 	nome della tabella
	 * @param column 	colonna di cui si vuole ottenre un set di valori distinti
	 * @throws SQLException 	
	 */
	public TreeSet<String> getDistinctColumnValues(String table, Column column) throws SQLException {
		Statement stmt = db.getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT DISTINCT " + column.getColumnName() + 
		" FROM " + table + " ORDER BY " + column.getColumnName() + " ASC");

		TreeSet<String> distinctColumns = new TreeSet<String>();
		while (resultSet.next()) {
			distinctColumns.add(resultSet.getString(column.getColumnName()));
		}
		resultSet.close();
		stmt.close();
		return distinctColumns;
	}

	/**
	 * Funzione che restituisce il risultato della query_type applicato alla colonna column della tabella table.
	 * @param table 	nome della tabella
	 * @param column 	colonna a cui si vuole applicare la query(aggregate)
	 * @param aggregate 	QUERY_TYPE
	 * @throws SQLException
	 * @throws NoValueException 	eccezione che viene sollevata nel caso in cui ResultSet sia vuoto o null
	 */
	public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) throws SQLException, NoValueException {
		Statement stmt = db.getConnection().createStatement();
		String string = "SELECT " + aggregate + "(" + column.getColumnName() + ") FROM " + table;
		ResultSet resultSet = stmt.executeQuery(string);
		if (!resultSet.next()) { //lancia NoValueException
            throw new NoValueException("ResultSet vuoto!");
        } else if (resultSet.getObject(aggregate + "(" + column.getColumnName() + ")") == null) {
			throw new NoValueException("Trovato valore null!");
		}
		Object aggregateColumnValue = resultSet.getObject(aggregate + "(" + column.getColumnName() + ")");
		resultSet.close();
		stmt.close();
		return aggregateColumnValue; //cambiato il return perchè se restituiamo il ResultSet termina il metodo ma non viene chiuso il ResultSet, sprecando memoria
	}
}
