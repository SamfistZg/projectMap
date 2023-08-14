package mining;
import java.io.Serializable;
import data.Data;
import data.Tuple;
import data.OutOfRangeSampleSize;

/**
 * Classe che rappresenta il ClusterSet.
 */
public class ClusterSet implements Serializable {

    private Cluster[] C;

    /**
     * Costruttore di ClusterSet
     * @param k     dimensione del vettore di Cluster.
     */
    ClusterSet(int k) {
        C = new Cluster[k];
    }

    /**
     * Metodo che aggiunge un Cluster ad una posizione specifica passata in input.
     * @param clust     cluster che si vuole aggiungere
     * @param i     indice
     */
    public void add(Cluster clust, int i) {
        C[i] = clust; 
    }

    /**
     * Funzione che restituisce il Cluster alla posizione i passata in input.
     * @param i     indice 
     * @return C[i]     cluster in posizione i 
     */
    public Cluster get(int i) {
        return C[i];
    }

    /**
     * Inserisce gli indici dei centroidi dentro l'array centroidIndexes e crea i cluster in base a quello.
     * @param data
     * @throws OutOfRangeSampleSize eccezione personalizzata che viene lanciata in caso venga inserito un numero di cluster minore di 0 o maggiore del numero di Examples
     */
    public void inizializeCentroids(Data data) throws OutOfRangeSampleSize {
        int[] centroidIndexes = data.sampling(C.length);
        for(int i = 0; i<centroidIndexes.length; i++) {
            Tuple centroidI = data.getItemSet(centroidIndexes[i]);
            add(new Cluster(centroidI), i);
        }
    }

    /**
     * Funzione che restituisce il cluster più vicino alla tupla passata in input.
     * @param tuple     tupla di riferimento
     * @return nearest  cluster più vicino
     */
    public Cluster nearestCluster(Tuple tuple) {

        Cluster nearest = C[0];
        for(int i = 1; i<C.length; i++){
            if(tuple.getDistance(nearest.getCentroid()) > tuple.getDistance(C[i].getCentroid()))
                nearest = C[i];
        }
        return nearest;
    }

    /**
     * Funzione che restituisc e il cluster alla posizione id.
     * @param id    indice
     * @return C[i]     cluster alla posizione id
     */
    public Cluster currentCluster(int id) {

        for(int i = 0; i<C.length; i++){
            if(C[i].contain(id))
                return C[i];
        }

        return null;
    }

    /**
     * Metodo che aggiorna i centroidi.
     * @param data  dataset
     */
    public void updateCentroids(Data data) {
        for(int i = 0; i<C.length; i++) {
            C[i].computeCentroid(data);
        }
    }

    /**
     * Funzione che resituisce la stringa contenente tutti i cluster, è un override.
     * @return str  stringa contenente i cluster 
     */
    public String toString() {

        String str = "";
        for(int i = 0; i<C.length; i++){
            if(C[i] != null){
                str += "Cluster "+i+": "+C[i]+"\n";
            }
        }
        return str;

    }

    /**
     * Funzione che resituisce la stringa contenente tutti i cluster, è un override.
     * @param data      dataset
     * @return str  stringa contenente i cluster 
     */
    public String toString(Data data) {

        String str = "";
        for(int i = 0; i<C.length; i++){
            if(C[i] != null) {
                str += "\nCluster "+i+": "+C[i].toString(data)+"\n";
            }
        }

        return str;
    }

}
