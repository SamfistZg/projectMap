package data;

/**
 * Classe che rappresenta un DiscreteItem.
 */
class DiscreteItem extends Item {

    /**
     * Costruttore di DiscreteItem.
     * @param attribute     attributo che si vuole associare al DiscreteItem
     * @param value     valore che si vuole associare al DiscreteItem
     */
    DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }

    /**
     * Funzione che calcola la distanza tra un oggetto a e il DiscreteItem su cui viene chiamata, è un overrride.
     * @param a oggetto da cui si vuole calcolare la distanza
     * @return 1 se gli oggetti sono diversi, 0 altrimenti
     */
    @Override
    public double distance(Object a) {
        
        if (this.getValue().equals(((DiscreteItem) a).getValue())) {
            return 0;
        } else return 1;
        
    }
}
