package database;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un Example.
 */
public class Example implements Comparable<Example> {
	private List<Object> example = new ArrayList<Object>();

	/**
	 * Metodo che aggiunge un oggetto o al example.
	 * @param o oggetto da aggiungere
	 */
	public void add(Object o) {
		example.add(o);
	}
	
	/**
	 * Funzione che resittuisce un Object in posizione i passato in input.
	 * @param i 	indice in cui trovare l'Object
	 * @return Object 	Object corrispondente
	 */
	public Object get(int i) {
		return example.get(i);
	}

	/**
	 * Funzione che restituisce 1 se gli Object presenti in ex sono tutti diversi dagli Object presenti nel Example su cui viene chiamata, 0 altrimenti.
	 * @param ex 	Example da scorrere per il confronto
	 * @return int 	1 o 0
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
	 * Funzione che stampa tutti gli Object presenti in Example.
	 * @return str 	stringa contenente gli oggetti di Example
	 */
	public String toString() {
		String str = "";
		for(Object o : example)
			str+=o.toString() + " ";
		return str;
	}
}
