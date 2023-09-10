package data;
import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.Example;
import database.NoValueException;
import database.TableData;
import database.TableSchema;
import database.QUERY_TYPE;
import java.util.Random;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

/**
 * Classe che rappresenta il dataset.
 */
public class Data {

	private List<Example> data = new ArrayList<Example>();
	private int numberOfExamples;
	private List<Attribute> attributeSet = new LinkedList<Attribute>();
	private DbAccess db = new DbAccess();

	/**
	 * Costruttore di Data
	 * @param table 	nome della tabella
	 * @throws SQLException
	 * @throws EmptySetException
	 * @throws NoValueException 	eccezione personalizzata che viene lanciata quando il datase risulta vuoto o contiene valori null.
	 * @throws DatabaseConnectionException
	 */
	public Data(String table) throws SQLException, EmptySetException, NoValueException, DatabaseConnectionException{
		db.initConnection();
		TableData td = new TableData(this.db);
		TableSchema tblschm = new TableSchema(this.db, table);
		// qui viene caricata la tabella tutta
		this.data = td.getDistinctTransazioni(table);
		Attribute attr;
		for(int i = 0; i < tblschm.getNumberOfAttributes(); i++) {
			if(tblschm.getColumn(i).isNumber()){
				attr = new ContinuousAttribute(tblschm.getColumn(i).getColumnName(), 
				i, (double)((float)td.getAggregateColumnValue(table, tblschm.getColumn(i), QUERY_TYPE.MIN)), 
				(double)((float)td.getAggregateColumnValue(table, tblschm.getColumn(i), QUERY_TYPE.MAX)));
			}
			else{
				attr = new DiscreteAttribute(tblschm.getColumn(i).getColumnName(), i,
				td.getDistinctColumnValues(table, tblschm.getColumn(i)));
			}
			this.attributeSet.add(i, attr);
		}
		this.numberOfExamples = data.size();
		db.closeConnection();
	}

	/**
	 * Funzione che restiuisce il numberOfExamples(record della tabella).
	 * @return numerOfExamples 	numero di record della tabella.
	 */
	public int getNumberOfExamples() {
		return numberOfExamples;
	}

	/**
	 * Funzione che restituisce il numero di attibuti.
	 * @return int 	numero di attributi
	 */
	public int getNumberOfAttributes() {
		return attributeSet.size();
	}

	/**
	 * Funzione che restituisce il valore del attributo.
	 * @param exampleIndex 	indice di riga
	 * @param attributeIndex 	indice di colonna
	 * @return Object 	oggetto corrispondente
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex) {
		return data.get(exampleIndex).get(attributeIndex);
	}
	
	/**
	 * Funzione che restituisce l'attibuto in posizione index.
	 * @param index indice
	 * @return Attribute attributo corrispondente
	 */
	public Attribute getAttribute(int index) {
		return attributeSet.get(index);
	}
	
	/**
	 * Funzione che restituisce una stringa contenente i valori della tabella.
	 * @return table 	stringa contenente tutta la tabella
	 */
	public String toString() {

		String table = new String();
		for (int i = 0; i<attributeSet.size(); i++){
			table = table + attributeSet.get(i).getName() + ", ";
		}
		table = table + "\n";

		for (int i = 0; i<numberOfExamples; i++){
			table = table + (i+1) + ": ";
			for (int j = 0; j<attributeSet.size(); j++) {
				table = table + data.get(i).get(j).toString() + ",\t";
			}
			table = table + "\n";
		}
		return table;

	}

	/**
	 * Funzione che restituisce una tupla in base al tipo di attributo che contiene la lista attributeSet.
	 * @param index 	indice
	 * @return tupla 		tupla corrispondente
	 */
	public Tuple getItemSet (int index) {

		Tuple tupla = new Tuple(attributeSet.size());
		for (int i = 0; i<attributeSet.size(); i++) {
			if(attributeSet.get(i) instanceof DiscreteAttribute){
				tupla.add(new DiscreteItem((DiscreteAttribute)attributeSet.get(i), (String)data.get(index).get(i)), i);
			}
			else {
				tupla.add(new ContinuousItem((ContinuousAttribute)attributeSet.get(i), (double)((float)data.get(index).get(i))), i);
			}
		}

		return tupla;
	}

	/**
	 * Funzione che restituisce il vettore di centroidi.
	 * @param k numero di cluster desiderati dal utente
	 * @return centroidIndexes 	vettore di centroidi 
	 * @throws OutOfRangeSampleSize 	eccezione personalizzata che viene lanciata nel caso in cui venga inserito un numero di cluster minore di 0 o maggiore di numberOfExamples
	 */
	public int [] sampling(int k) throws OutOfRangeSampleSize{
		if (k <= 0 || k > numberOfExamples) {
            throw new OutOfRangeSampleSize("Numero di cluster fuori dal range possibile");
        }
		int centroidIndexes[] = new int[k];
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		for (int i = 0; i<k; i++) {
			boolean found = false;
			int c;
			do {
				found = false;
				c = rand.nextInt(numberOfExamples); // numero casuale tra 0 e numberOfExamples-1
				for (int j = 0; j<i; j++) {
					if (compare(centroidIndexes[j], c)) { // se c è già stato scelto come centroide
						found = true;					  // esci dal ciclo e scegli un altro centroide
						break;
					}
				}
			} while (found);
			centroidIndexes[i] = c;
		}
		return centroidIndexes;
	}

	/**
	 * Funzione che confronta due righe e restituisce true se uguali, false se diverse.
	 * @param i 	indice della prima riga 
	 * @param k 	indice della seconda riga 
	 * @return res 	true se uguali, false altrimenti
	 */
	private boolean compare(int i, int k) {

		boolean res = true;
		for (int j = 0; j<attributeSet.size(); j++) {
			if (data.get(i).get(j)!= data.get(k).get(j)) {
				res = false;
			}
		}

		return res;

	}
	
	/**
	 * Funzione che cambia chiamata di funzione a seconda del tipo di attribute passato in input.
	 * @param idList 	Set di Integer
	 * @param attribute 	attributo 	
	 * @return Object 	risultato di computePrototype(...)
	 */
	public Object computePrototype(Set<Integer> idList, Attribute attribute) {
		if(attribute instanceof ContinuousAttribute) {
			return computePrototype(idList, (ContinuousAttribute)attribute);
		}
		else {
			return computePrototype(idList, (DiscreteAttribute)attribute);
		}
	}

	/**
	 * Funzione che restituisce il valore (String) che si ripete più volte utilizzando un vettore freq e la funzione frequency.
	 * @param idList 	Set di Integer
	 * @param attribute 	attributo 
	 * @return att[maxIndex] 	valore ripetuto più volte
	 */
	public String computePrototype(Set<Integer> idList, DiscreteAttribute attribute) {
		
		int freq[] = new int[attribute.getNumberOfDistinctValues()];
		Arrays.fill(freq, 0);

		Iterator<String> it = attribute.iterator();
		while (it.hasNext()) { // funziona
		for (int i = 0; i<attribute.getNumberOfDistinctValues(); i++) {
				freq[i] = attribute.frequency(this, idList, it.next());
			}
		}

		int max = freq[0];
		int maxIndex = 0;
		for (int i = 1; i<freq.length; i++) {
			if (max < freq[i]) { // e se abbiamo due indici con valori uguali?
				maxIndex = i;
			}
		}

		int dim = attribute.getNumberOfDistinctValues();
		String att[] = new String[dim];
		
		int i = 0;
		for (String s : attribute) {
			att[i++] = s;
		}

		return att[maxIndex];
	}

	/**
	 * Funzione che calcola il valore medio per un Continuous Attribute.
	 * @param idList Set di Integer
	 * @param attribute 	attributo
	 * @return Double 	valore medio 
	 */
	public Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
		double tot = 0;
		Iterator<Integer> it = idList.iterator();
		while(it.hasNext()) {
			tot += (double)((float)getAttributeValue(it.next(), attribute.getIndex()));
		}
		return tot / idList.size();
	}
}
