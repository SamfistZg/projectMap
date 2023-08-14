package mining;
import data.Data;
import data.Tuple;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

/**
 * Classe che rappresenta un cluster.
 */
class Cluster implements Serializable{
	
	private Tuple centroid;
	private Set<Integer> clusteredData; 

	/**
	 * Costruttore di Cluster.
	 * @param centroid 	tupla di centroidi
	 */
	Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();
	} 
		
	/**
	 * Funzione che restituisce la tupla di centroidi.
	 * @return centroid 	tupla di centroidi
	 */
	public Tuple getCentroid() {
		return centroid;
	}
	
	/**
	 * Metodo che aggiorna ogni Item di Tuple centroid con il valore maggiormente ripetuto per ogni attribute.
	 * @param data 	dataset
	 */
	public void computeCentroid(Data data) {
	 	
		for(int i = 0; i<centroid.getLength(); i++) {
			centroid.get(i).update(data, clusteredData);
		}
		
	}

	/**
	 * Funzione che restituisce true se la tupla in posizone id cambia il cluster.
	 * @param id 	indice
	 * @return true o false
	 */
	public boolean addData(int id) {
		return clusteredData.add(id);
	}
	
	/**
	 * Funzione che verifica se una tupla (id) é clusterizzata nell'array corrente.
	 * @param id 	indice
	 * @return res 	true se la tupla è clusterizzata, false altrimenti
	 */
	public boolean contain(int id) {
		Iterator<Integer> it = clusteredData.iterator();
		boolean res = false;
		while(it.hasNext()) {
			if(it.next() == id)
				res = true;
		}
		return res;
	}
	
	/**
	 * Metodo che rimuove la tupla che ha cambiato il cluster corrente.
	 * @param id 	indice
	 */
	public void removeTuple(int id) {
		clusteredData.remove(id);
	}
	
	/**
	 * Funzione che stampa i centroidi, è un override.
	 * @return str 	stringa contenente i valori della tupla di centroidi.
	 */
	public String toString() {

		String str="Centroid = (";
		for(int i = 0; i<centroid.getLength(); i++)
			str += centroid.get(i).getValue();
		str += ")";

		return str;
	}
	
	/**
	 * Funzione che stampa la tupla del centroide del cluster e i valori di data del cluster e la distanza di ogni tupla dal centroide e infine la media della distanza
	 * @param data 	dataset
	 * @return str 	striga contenente i valori del centroide e i valori clusterizzata, la relativa distanza e la media
	 */
	public String toString(Data data) {

		String str="Centroid = (";
		for(int i = 0; i<centroid.getLength(); i++)
			str += centroid.get(i).getValue()+ " ";
		str += ")\nExamples:\n";

		int[] array= new int[clusteredData.size()];
		Iterator<Integer> it = clusteredData.iterator();
		while (it.hasNext()) {
			for (int i = 0; i<array.length; i++) {
				array[i] = it.next().intValue();
			}
		} 
		
		for(int i = 0; i<array.length; i++) {
			str += "[";
			for(int j = 0; j<data.getNumberOfAttributes(); j++)
				str += data.getAttributeValue(array[i], j)+" ";
			str += "] dist = "+getCentroid().getDistance(data.getItemSet(array[i])) + "\n";
		}
		str += "AvgDistance = "+getCentroid().avgDistance(data, array) + "\n";
		return str;
	}

}
