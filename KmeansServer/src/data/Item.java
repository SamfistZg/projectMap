package data;
import java.io.Serializable;
import java.util.Set;

/**
 * Classe astratta che rappresenta l'Item.
 */
public abstract class Item implements Serializable {

    private Attribute attribute;
    private Object value;

    /**
     * Costruttore di Item.
     * @param attribute     attributo
     * @param value     valore
     */
    Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Funzione che restiuisce l'Attribute di un Item.
     * @return attribute    attributo del Item
     */
    public Attribute getAttribute() {
        return attribute;
    }

    /**
     * Funzione che restiuisce il value di un Item.
     * @return value   value del Item
     */
    public Object getValue() {
        return value;
    }

    /**
     * Funzione che stampa il valore del value di un Item.
     * @return String   valore del value
     */
    public String toString() {
        return value.toString();
    }

    abstract double distance(Object a); // implementazione diversa per ogni tipo di attributo

    /**
     * Metodo che modifica il value di Item con il valore maggiormente ripetuto per un determinato attributo.
     * @param data  dataset
     * @param clusteredData     vettore di int contenente i valori clusterizzati
     */
    public void update (Data data, Set<Integer> clusteredData) {
        this.value = data.computePrototype(clusteredData, attribute);
    }
}
