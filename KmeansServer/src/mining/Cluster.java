package mining;
import data.Data;
import data.Tuple;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

class Cluster implements Serializable{
	
	private Tuple centroid;
	private Set<Integer> clusteredData; 

	Cluster(Tuple centroid) {

		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();
		
	} 
		
	public Tuple getCentroid() {
		return centroid;
	}
	
	/*
	 * Aggiorna ogni Item di Tuple centroid con il valore mpiù ripetuto per ogni attribute
	 */
	public void computeCentroid(Data data) {
	 	
		for(int i = 0; i<centroid.getLength(); i++) {
			centroid.get(i).update(data, clusteredData);
		}
		
	}
	/*
	 * return true if the tuple (id) is changing cluster
	 */
	public boolean addData(int id) {
		return clusteredData.add(id);
	}
	
	/*
	 * verifica se una tupla (id) é clusterizzata nell'array corrente
	 */
	public boolean contain(int id) { // ho cambiato il senso della funzione mettendo il valore (prima era l'indice dell'array) ho rischiato la morte >.<
		Iterator<Integer> it = clusteredData.iterator();
		boolean res = false;
		while(it.hasNext()) {
			if(it.next() == id)
				res = true;
		}
		return res;
	}
	
	/*
	 * remove the tuple that has changed the cluster
	 */
	public void removeTuple(int id) {
		clusteredData.remove(id);
	}
	
	/* stampa solo la tupla del centroide del cluster */
	public String toString() {

		String str="Centroid = (";
		for(int i = 0; i<centroid.getLength(); i++)
			str += centroid.get(i).getValue(); // aggiunto getValue();
		str += ")";

		return str;
	}
	
	/*stampa la tupla del centroide del cluster e i valori di data del cluster e la distanza di ogni tupla dal centroide e infine la media della distanza */
	public String toString(Data data) {

		String str="Centroid = (";
		for(int i = 0; i<centroid.getLength(); i++)
			str += centroid.get(i).getValue()+ " "; //aggiunto getValue();
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
