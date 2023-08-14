package data;

/**
 * Classe che rappresenta un ContinuousItem, estende Item
 */
public class ContinuousItem extends Item {

    /**
     * Cotruttore di ContinuousItem.
     * @param attribute ContinuousAttribute
     * @param value valore che si vuole dare al ContinuousAttrubute
     */
    ContinuousItem(ContinuousAttribute attribute, double value) {
        super(attribute, value);
    }

    /**
     * Funzione calcola la distanza tra due oggetti di tipo ContinuousItem (implementa il metodo distance della superclasse).
     * @param a     oggetto da cui si vuiole calcolare la distanza
     * @return double   distanza 
     */
    @Override
    double distance(Object a) {
        double v1 = ((ContinuousAttribute) this.getAttribute()).getScaledValue((double) this.getValue());
        double v2 = ((ContinuousAttribute) ((ContinuousItem) a).getAttribute()).getScaledValue((double) ((ContinuousItem) a).getValue());
        return Math.abs(v1-v2);
    }
}
