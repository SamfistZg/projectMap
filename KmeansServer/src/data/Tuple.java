package data;
import java.io.Serializable;

/**
 * Classe che rappresenta una tupla.
 */
public class Tuple implements Serializable {
    
    private Item[] tuple;

    /**
     * Costruttore di Tuple
     * @param size  dimensione dell'array di Item
     */
    Tuple(int size) {
        tuple = new Item[size];
    }

    /**
     * Funzione che restituisce la lunghezza di tuple.
     * @return int  dimensione
     */
    public int getLength() {
        return tuple.length;
    }

    /**
     * Funzione che restituisce l'Item in posizione i.
     * @param i     indice del array
     * @return Item
     */
    public Item get(int i) {
        return tuple[i];
    }

    /**
     * Metodo che aggiunge un Item alla posizione i.
     * @param c     Item da aggiungere
     * @param i     indice dove si vuole aggiungere l'Item
     */
    public void add(Item c, int i) {
        tuple[i] = c;
    }

    /**
     * Funzione che restituisce la distanza di una tupla da un 'altra.'
     * @param obj   tupla da cui si vuole calcolare la distanza
     * @return double   distanza 
     */
    public double getDistance(Tuple obj) { 

        double distance = 0.0;
        for (int i = 0; i<this.tuple.length; i++) {
            distance+= this.tuple[i].distance(obj.get(i));
        }

        return distance;
    }

    /**
     * Funzione che restituisce la media delle distanze tra i valori di data e quelli di clusteredData.
     * @param data  dataset
     * @param clusteredData array di int contenente i valori clusterizzati.
     * @return double   media
     */
    public double avgDistance(Data data, int clusteredData[]) {
        double p = 0.0 , sumD = 0.0;
        for(int i=0;i<clusteredData.length;i++){
            double d = getDistance(data.getItemSet(clusteredData[i]));
            sumD+=d;
        }
        p=sumD/clusteredData.length;
        return p;
    }
}
