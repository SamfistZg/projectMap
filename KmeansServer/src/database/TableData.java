package database;
import java.sql.ResultSet; //P
import java.sql.SQLException; //P
import java.sql.Statement; //P
import java.util.ArrayList; //P
import java.util.List; //P
import java.util.TreeSet; //P
import database.TableSchema.Column; //P

/**
 * Classe che rappresenta il database sul quale operiamo.
 */
public class TableData {
	DbAccess db;

	/**
	 * Costruttore di TableData.
	 * @param db 	database
	 */
	public TableData(DbAccess db) {
		this.db = db;
	}

	/**
	 * Funzione che ricava lo schema della tabella, va a estrarre tutti i dati contenuti in table e va a inserire in una lista di example solo le tuple distinte.
	 * @param table 	nome della tabella
	 * @return distinctTransazioni 	tuple distinte
	 * @throws SQLException
	 * @throws EmptySetException 	eccezione personalizzata che viene sollevata in caso di ResultSet vuoto
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException {
		TableSchema schema = new TableSchema(db, table);
		List<Example> distinctTransazioni = new ArrayList<Example>();
		Statement stmt = db.getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT DISTINCT * FROM " + table);
		if (!resultSet.next()) { //lancia EmptySetException in caso resultSet sia vuoto
            throw new EmptySetException("Empty set");
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
	 * Funzione che restituisce un TreeSet di valori distinti di una specifica colonna.
	 * @param table 	nome della tabella
	 * @param column	colonna di riferimento
	 * @return distinctColumns 	TreeSet di valori disinti della colonna column
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
	 * @param column 	colonna di riferimento
	 * @param aggregate	query che si vuole applicare alla tabella 
	 * @return aggregateColumnValue 	risultato della query
	 * @throws SQLException
	 * @throws NoValueException 	eccezione personalizzata che viene sollevata nel caso in cui il ResultSet risulti vuoto o contenga un valore null
	 */
	public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) throws SQLException, NoValueException {
		Statement stmt = db.getConnection().createStatement();
		String string = "SELECT " + aggregate + "(" + column.getColumnName() + ") FROM " + table;
		ResultSet resultSet = stmt.executeQuery(string);
		if (!resultSet.next()) { //lancia NoValueException in caso resultSet sia vuoto o null
            throw new NoValueException("ResultSet vuoto!");
        } else if (resultSet.getObject(aggregate + "(" + column.getColumnName() + ")") == null) {
			throw new NoValueException("Trovato valore null!");
		}
		Object aggregateColumnValue = resultSet.getObject(aggregate + "(" + column.getColumnName() + ")");
		resultSet.close();
		stmt.close();
		return aggregateColumnValue;
	}
}
