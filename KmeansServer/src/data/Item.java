package data;
import java.io.Serializable;
import java.util.Set;

/**
 * Classe astratta che rappresenta un Item.
 */
public abstract class Item implements Serializable {

    private Attribute attribute;
    private Object value;

    /**
     * Costruttore di Item.
     * @param attribute     attributo che si vuole associare al item 
     * @param value     valore che si vuole associare al item 
     */
    Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Funzione che restituisce l'attributo associato al item.
     * @return attribute
     */
    public Attribute getAttribute() {
        return attribute;
    }

    /**
     * Funzione che restituisce il valore associato al item
     * @return value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Funzione che restituisce il valore del attributo.
     * @return String   nome del attributo
     */
    public String toString() {
        return value.toString();
    }

    /**
     * Funzione astratta che verrà definita nelle classi concrete DiscreteItem e ContinuousItem.
     * @param a     oggetto da cui si vuole calcolare la distanza
     * @return 1 o 0    
     */
    abstract double distance(Object a); // implementazione diversa per ogni tipo di attributo

    /**
     * Metodo che modifica il valore di Item con il valore maggiormente ripetuto per un determinato attributo.
     * @param data  dataset
     * @param clusteredData     Set di Integer
     */
    public void update (Data data, Set<Integer> clusteredData) {
        this.value = data.computePrototype(clusteredData, attribute);
    }
}
