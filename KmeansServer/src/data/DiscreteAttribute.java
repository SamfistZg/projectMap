package data;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Set;

/**
 * Classe che rappresenta un DiscreteAttribute, estende Attribute.
 */
class DiscreteAttribute extends Attribute implements Iterable<String> {

    //private String[] values;
    private TreeSet<String> values; /* TreeSet è una collezione di elementi ordinati e non duplicati, struttura ad albero */

    /**
     * Costruttore di DiscreteAttribute.
     * @param name  nome del Attribute
     * @param index     indice del Attribute
     * @param v TreeSet<String>
     */
    DiscreteAttribute(String name, int index, TreeSet<String> v) {

        super(name, index);
        //this.values = new String[values.length];
        this.values = new TreeSet<String>();
        Iterator<String> it = v.iterator();
        while (it.hasNext()) {
            this.values.add(it.next());
        }
        
    }

    /**
     * Funzione che restituisce il numero di valori distinti al interno del TreeSet(ci sono solo valori distinti).
     * @return int  dimensione
     */
    public int getNumberOfDistinctValues() {
        return values.size();
    }

    /**
     * Iteratore per scorrere una collection.
     * @return Iterator
     */
    public Iterator<String> iterator() {
        Iterator<String> iterator = values.iterator();
        return iterator;    
    }

    /**
     * Funzione che restituisce quante volte si ripete uno specifico attributo.
     * @param data  dataset
     * @param idList    Set<Integer>
     * @param v     stringa che rappresenta l'attributo di cui si vuole sapere la frequenza.
     * @return int  frequenza
     */
    public int frequency(Data data, Set<Integer> idList, String v) {

        int count = 0;
        Iterator<Integer> it = idList.iterator();
        for (int j = 0; j<idList.size(); j++) {
                if (it.hasNext()) {
                    if (data.getAttributeValue(it.next(), getIndex()).equals(v)) { 
                        count++;
                    }
                }
            }
        return count; 
    }

}
