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
	 * @param centroid	tupla di centroidi
	 */
	Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();
	}

	/**
	 * Funzione che restituisce la tupla di centroidi.
	 * @return centroid	tupla di centroidi
	 */
	public Tuple getCentroid() {
		return centroid;
	}

	/**
	 * Metodo che aggiorna ogni Item di Tuple centroid con il valore maggiormente ripetuto per ogni Attribute.
	 * @param data	dataset
	 */
	public void computeCentroid(Data data) {
	 	
		for(int i = 0; i<centroid.getLength(); i++) {
			centroid.get(i).update(data, clusteredData);
		}
		
	}

	/**
	 * Funzione che restituisce true se una tupla(id) cambia il cluster, false altrimenti.
	 * @param id	indice in cui aggiungere la tupla nel ClusteredData
	 * @return boolean 	true o false
	 */
	public boolean addData(int id) {
		return clusteredData.add(id);
	}
	
	/**
	 * Funzione che verifica se una tupla (id) é clusterizzata nell'array corrente.
	 * @param id	indice in cui trovare la tupla nel ClusteredData
	 * @return res	true o false
	 */
	public boolean contain(int id) { // ho cambiato il senso della funzione mettendo il valore (prima era l'indice dell'array), ho rischiato la morte >.<
		Iterator<Integer> it = clusteredData.iterator();
		boolean res = false;
		while(it.hasNext()) {
			if(it.next() == id)
				res = true;
		}
		return res;
	}
	
	/**
	 * Metodo che rimuove la tuple che ha cambiato il cluster.
	 * @param id	indice di ClusteredData in cui rimuovere la tupla
	 */
	public void removeTuple(int id) {
		clusteredData.remove(id);
	}
	
	/**
	 * Funzione che stampa solo la tupla del centroide che cambia il cluster.
	 * @return str	stringa che contiene i centroidi.
	 */
	public String toString() {

		String str="Centroid = (";
		for(int i = 0; i<centroid.getLength(); i++)
			str += centroid.get(i).getValue();
		str += ")";

		return str;
	}
	
	/**
	 * Metodo che stampa la tupla del centroide del cluster e i valori di data del cluster e la distanza di ogni tupla dal centroide e, infine, la media della distanza.
	 * @param data	dataset
	 * @return String	stringa che contiene i valori contenuti nel Cluster, la distanza di ogni tuolqa dal centroide e la media della distanza
	 */
	public String toString(Data data) {

		String str="Centroid = (";
		for(int i = 0; i<centroid.getLength(); i++)
			str += centroid.get(i).getValue()+ " "; //aggiunto getValue();
		str += ")<br/>Examples:<br/>";

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
			str += "] dist = "+getCentroid().getDistance(data.getItemSet(array[i])) + "<br/>";
		}
		str += "AvgDistance = "+getCentroid().avgDistance(data, array) + "<br/>";
		return str;
}
}


