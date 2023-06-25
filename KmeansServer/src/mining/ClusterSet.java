package mining;
import java.io.Serializable;
import data.Data;
import data.Tuple;
import data.OutOfRangeSampleSize;

public class ClusterSet implements Serializable {

    private Cluster[] C;

    ClusterSet(int k) {
        C = new Cluster[k];
    }

    public void add(Cluster clust, int i) {
        C[i] = clust; 
    }

    public Cluster get(int i) {
        return C[i];
    }

    // Inserisce gli indici dei centroidi dentro l'array centroidIndexes e crea i cluster in base a quello.
    public void inizializeCentroids(Data data) throws OutOfRangeSampleSize {
        int[] centroidIndexes = data.sampling(C.length);
        for(int i = 0; i<centroidIndexes.length; i++) {
            Tuple centroidI = data.getItemSet(centroidIndexes[i]);
            add(new Cluster(centroidI), i);
        }
    }

    public Cluster nearestCluster(Tuple tuple) {

        Cluster nearest = C[0];
        for(int i = 1; i<C.length; i++){
            if(tuple.getDistance(nearest.getCentroid()) > tuple.getDistance(C[i].getCentroid()))
                nearest = C[i];
        }
        return nearest;
    }

    /*
     * restituisce il cluster dove è presenta la tupla id
     */
    public Cluster currentCluster(int id) {

        for(int i = 0; i<C.length; i++){
            if(C[i].contain(id))
                return C[i];
        }

        return null;
    }

    public void updateCentroids(Data data) {
        for(int i = 0; i<C.length; i++){
            C[i].computeCentroid(data);
        }
    }

    public String toString() {

        String str = "";
        for(int i = 0; i<C.length; i++){
            if(C[i] != null){
                str += "Cluster "+i+": "+C[i]+"\n";
            }
        }
        return str;

    }

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
