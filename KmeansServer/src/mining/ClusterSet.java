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
     * Costruttore di ClusterSet, crea un vettore di Cluster di dimensione k.
     * @param k     numero di cluster 
     */
    ClusterSet(int k) {
        C = new Cluster[k];
    }

    /**
     * Metodo che aggiunge un Cluster al ClusterSet.
     * @param clust     Cluster che si vuole aggiungere al ClusterSet
     * @param i     indice in cui si vuole aggiungere il Cluster
     */
    public void add(Cluster clust, int i) {
        C[i] = clust; 
    }

    /**
     * Funzione che restituisce un Cluster ad una specifica posizione passata in input.
     * @param i     indice del Cluster che si vuole ricevere in output
     * @return C[i]  Cluster in posizione i 
     */
    public Cluster get(int i) {
        return C[i];
    }

    /**
     * Inserisce gli indici dei centroidi dentro l'array centroidIndexes e crea i cluster in base a quelli.
     * @param data  dataset
     * @throws OutOfRangeSampleSize     eccezione sollevata nel caso in cui si inserisce un numero di cluster superiore al numero di Examples
     */
    public void inizializeCentroids(Data data) throws OutOfRangeSampleSize {
        int[] centroidIndexes = data.sampling(C.length);
        for(int i = 0; i<centroidIndexes.length; i++) {
            Tuple centroidI = data.getItemSet(centroidIndexes[i]);
            add(new Cluster(centroidI), i);
        }
    }

   /**
    * Funzione che restiuisce il cluster più vicino alla tupla passata in input.
    * @param tuple  tupla di riferimento
    * @return nearest   Cluster più vicino a tuple passata in input
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
    * Funzione che restituisce il cluster dove è presenta la tupla id passata in input.
    * @param id     indice dove è presente il Cluster che si vuole ricevere in output
    * @return C[i] Cluster in posizione id
    */
    public Cluster currentCluster(int id) {

        for(int i = 0; i<C.length; i++){
            if(C[i].contain(id))
                return C[i];
        }

        return null;
    }

    /**
     * Metodo che aggiorna i centroidi in base al Data passato in input.
     * @param data  dataset
     */
    public void updateCentroids(Data data) {
        for(int i = 0; i<C.length; i++){
            C[i].computeCentroid(data);
        }
    }

    /**
     * Funzione che stampa i cluster, è un override.
     * @return str   stringa contenente i valori di un Cluster
     */
    public String toString() {

        String str = "";
        for(int i = 0; i<C.length; i++){
            if(C[i] != null) {
                str += "Cluster "+i+": "+C[i]+"\n";
            }
        }
        return str;

    }

    /**
     * Funzione che stampa i cluster grazie al Data ricevuto in input.
     * @param data  dataset
     * @return str   stringa contenente i valori di un Cluster 
     */
    public String toString(Data data) {

        String str = "<html>";
        for(int i = 0; i<C.length; i++){
            if(C[i] != null) {
                str += "<br/>Cluster "+i+": "+C[i].toString(data)+"<br/>";
            }
        }
        str += "</html>";
        return str;
    }

}
