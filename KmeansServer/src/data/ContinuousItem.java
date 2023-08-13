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
        /* 
        Qui sto andando a prendere i valori double (value) del continuous item locale e del continuous item 
        passato come parametro.
        Il v2 è il valore del continuous item passato come parametro, Object a è castato a continuous item
        e poi a continuous attribute per poter accedere al metodo getScaledValue().
        */
        double v1 = ((ContinuousAttribute) this.getAttribute()).getScaledValue((double) this.getValue());
        double v2 = ((ContinuousAttribute) ((ContinuousItem) a).getAttribute()).getScaledValue((double) ((ContinuousItem) a).getValue());
        // Ritorna la distanza tra i due valori double in valore assoluto (quello che fa la funzione abs)
        return Math.abs(v1-v2);
    }
}
