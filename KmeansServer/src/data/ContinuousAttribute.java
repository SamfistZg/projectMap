package data;

/**
 * Classe che rappresenta un ContinuousAttrubute.
 */
class ContinuousAttribute extends Attribute {
    
    private double max;
    private double min;

    /**
     * Costruttore di ContinuousAttrubute.
     * @param name  nome del attributo
     * @param index indice del attributo
     * @param min valore minimo del continuous attribute
     * @param max valore massimo del continuous attribute
     */
    ContinuousAttribute(String name, int index, double min, double max) {
        super(name, index);
        this.min = min;
        this.max = max;
    }

    /**
     * Funzione che restituisce il valore massimo del ContinuousAttribute.
     * @return double   valore massimo 
     */
    public double getMax() {
        return max;
    }

    /**
     * Funzione che restituisce il valore minimo del ContinuousAttribute.
     *  @return min     valore minimo
     */
    public double getMin() {
        return min;
    }

    /**
     * Funzione che restituisce il valore v, passato in input, scalato.
     * @param v         valore che si intende scalare
     * @return double   valore scalato
     */
    public double getScaledValue(double v) {
        double vScld = (v-min)/(max-min);
        return vScld;
    }
}
