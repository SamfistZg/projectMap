package data;

/**
 * Classe che rappresenta un ContinuousItem.
 */
public class ContinuousItem extends Item {

    /**
     * Costruttore di ContinuousItem.
     * @param attribute     attributo che si vuole associare al Item
     * @param value     valore del attributo che si vuole associare al Item
     */
    ContinuousItem(ContinuousAttribute attribute, double value) {
        super(attribute, value);
    }

    /*
     * Funzione che calcola la distanza tra due oggetti di tipo ContinuousItem (implementa il metodo distance della superclasse).
     * @param a     oggetto da cui si vuole calcolare la distanza.
     * @return double   distanza
     */
    @Override
    double distance(Object a) {
        double v1 = ((ContinuousAttribute) this.getAttribute()).getScaledValue((double) this.getValue());
        double v2 = ((ContinuousAttribute) ((ContinuousItem) a).getAttribute()).getScaledValue((double) ((ContinuousItem) a).getValue());
        return Math.abs(v1-v2);
    }
}
