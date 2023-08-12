package data;

/**
 * Classe che rappresenta un DiscreteItem, estende Item.
 */
class DiscreteItem extends Item {

    /**
     * Costruttore di DiscreteItem.
     * @param attribute     attributo
     * @param value     stringa contenente il valore 
     */
    DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }

    /**
     * Funzione che restituisce la distanza tra un DiscreteItem e un oggetto a.
     * @param a     oggetto da cui calcolare la distanza
     * @return double   distanza
     */
    @Override
    public double distance(Object a) {
        
        if (this.getValue().equals(((DiscreteItem) a).getValue())) {
            return 0;
        } else return 1;
        
    }
}
