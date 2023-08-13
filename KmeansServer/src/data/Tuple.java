package data;
import java.io.Serializable;

/**
 * Classe che rappresenta una tupla.
 */
public class Tuple implements Serializable {
    
    private Item[] tuple;

    /**
     * Costruttore di Tuple.
     * @param size  dimensione del vettore di Item
     */
    Tuple(int size) {
        tuple = new Item[size];
    }

    /***
     * Funzione che restituisce la dimensione di tuple.
     * @return int  dimensione di tuple
     */
    public int getLength() {
        return tuple.length;
    }

    /**
     * Funzione che restituisce l'Item in posizione i.
     * @param i     indice
     * @return tuple[i]     Item in posizione i 
     */
    public Item get(int i) {
        return tuple[i];
    }

    /**
     * Funzione che aggiunge un Item in tuple.
     * @param c     Item che si vuole aggiungere
     * @param i     posizione in cui si vuole aggiungere l'Item
     */
    public void add(Item c, int i) {
        tuple[i] = c;
    }

    /**
     * Funzione che resituisce la distanza tra due tuple.
     * @param obj   tupla di riferimento 
     * @return distance     distanza tra le due tuple
     */
    public double getDistance(Tuple obj) { 

        double distance = 0.0;
        for (int i = 0; i<this.tuple.length; i++) {
            distance+= this.tuple[i].distance(obj.get(i));
        }

        return distance;
    }

    /**
     * Funzione che restituisce la media delle distanze 
     * @param data  dataset
     * @param clusteredData     vettore di valori clusterizzati
     * @return p    media 
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
