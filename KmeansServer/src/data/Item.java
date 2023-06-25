package data;
import java.io.Serializable;
import java.util.Set;

public abstract class Item implements Serializable {

    private Attribute attribute;
    private Object value;

    Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public Object getValue() {
        return value;
    }

    public String toString() {
        return value.toString();
    }

    abstract double distance(Object a); // implementazione diversa per ogni tipo di attributo

    /*
     * Modifica value di Item con il valore più ripetuto per un determinato attributo
     */
    public void update (Data data, Set<Integer> clusteredData) {
        this.value = data.computePrototype(clusteredData, attribute);
    }
}
