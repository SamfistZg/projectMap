package database;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un Example.
 */
public class Example implements Comparable<Example> {

	private List<Object> example = new ArrayList<Object>();

	/**
	 * Metodo che aggiunge un oggetto ad example.
	 * @param o 	oggetto che si vuole aggiungere
	 */
	public void add(Object o) {
		example.add(o);
	}
	
	/**
	 * Funzione che restituisce l'oggetto in posizione i.
	 * @param i 	indice
	 * @return Object 	oggetto in posizione i
	 */
	public Object get(int i) {
		return example.get(i);
	}

	/**
	 * Funzione che restituisce 1 se l'example passato in input contiene almeno un oggetto diverso rispetto al example su cui viene chiamato, 0 altrimenti (example uguali).
	 * @param ex 	Example che si vuole confrontare
	 * @return 0 o 1 	1 se gli example sono diversi, 0 altrimenti
	 */
	public int compareTo(Example ex) {
		
		int i = 0;
		for(Object o : ex.example) {
			if(!o.equals(this.example.get(i)))
				return ((Comparable)o).compareTo(example.get(i));
			i++;
		}
		return 0;
	}

	/**
	 * Funzione che restituisce una stringa contenente tutti i valori del example su cui viene chiamato.
	 * @return str 	stringa contenente tutti i valori degli object contenuti nel example
	 */
	public String toString() {
		String str = "";
		for(Object o : example)
			str+=o.toString() + " ";
		return str;
	}
}
