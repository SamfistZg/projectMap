package database;
import java.sql.ResultSet; //P
import java.sql.SQLException; //P
import java.sql.Statement; //P
import java.util.ArrayList; //P
import java.util.List; //P
import java.util.TreeSet; //P
import database.TableSchema.Column; //P

public class TableData {
	DbAccess db;

	public TableData(DbAccess db) {
		this.db = db;
	}

	/**
	 * Ricava lo schema di "table", va a estrarre tutti i dati contenuti in table e va a inserire in una lista di example solo le tuple distinte.
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException {
		TableSchema schema = new TableSchema(db, table);
		List<Example> distinctTransazioni = new ArrayList<Example>();
		Statement stmt = db.getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT DISTINCT * FROM " + table);
		if (!resultSet.next()) { //lancia EmptySetException in caso resultSet sia vuoto
            throw new EmptySetException("Empty set"); // messaggio più specifico?
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
	 * Restituisce un Set di valori distinti di una specifica colonna.
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
	 * Restituisce il risultato della query_type applicato alla colonna column della tabella table.
	 */
	public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) throws SQLException, NoValueException {
		Statement stmt = db.getConnection().createStatement();
		String string = "SELECT " + aggregate + "(" + column.getColumnName() + ") FROM " + table;
		ResultSet resultSet = stmt.executeQuery(string);
		if (!resultSet.next()) { //lancia NoValueException in caso resultSet sia vuoto o null
            throw new NoValueException("Valore non valido"); // messaggio più specifico?
        } else if (resultSet.getObject(aggregate + "(" + column.getColumnName() + ")") == null) {
			throw new NoValueException("Valore non valido");
		}
		Object aggregateColumnValue = resultSet.getObject(aggregate + "(" + column.getColumnName() + ")");
		resultSet.close();
		stmt.close();
		return aggregateColumnValue; //cambiato il return perchè se restituiamo il ResultSet termina il metodo ma non viene chiuso il ResultSet, sprecando memoria
	}
}
