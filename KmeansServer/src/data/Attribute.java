package data;
import java.io.Serializable;

/**
 * Classe astratta che rappresenta un attributo.
 */
abstract class Attribute implements Serializable {
    
    private String name;
    private int index;

    /**
     * Costruttore di Attribute.
     * @param name  nome del attributo 
     * @param index     indice
     */
    Attribute(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Funzione che restituisce il nome del attributo.
     * @return name     nome del attributo
     */
    public String getName() {
        return name;
    }

    /**
     * Funzione che restituisce l'index del attributo.
     * @return index    indice del attributo
     */
    public int getIndex () {
        return index;
    }

    /**
     * Funzione che stampa il nome del attributo, è un override.
     * @return name     nome del attributo
     */
    public String toString() {
        return name;
    }
}
