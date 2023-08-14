package data;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Set;

/**
 * Classe che rappresenta un DiscreteAttribute.
 */
class DiscreteAttribute extends Attribute implements Iterable<String> {
    private TreeSet<String> values;

    /**
     * Costruttore di DiscreteAttribute.
     * @param name nome del attributo
     * @param index indice del attributo 
     * @param v     TreeSet<String> contiene valori distinti
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
     * Funzione che restituisce il numero di valori distinti di values.
     * @return int  numero di valori distinti di values.
     */
    public int getNumberOfDistinctValues() {
        return values.size();
    }

    /**
     * Funzione che restituisce l'iterator per scorrere il TreeSet<String>.
     * @return iterator     iteratore
     */
    public Iterator<String> iterator() {
        Iterator<String> iterator = values.iterator();
        return iterator;    
    }

    /**
     * Funzione che restituisce la frequenza di uno specifico attributo.
     * @param data  dataset
     * @param idList    Set<Integer>
     * @param v     stringa contenente il valore del attributo 
     * @return count    frequenza
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
