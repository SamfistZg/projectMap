package data;

/**
 * Classe che rappresenta iL ContinuousAttrbute.
 */
class ContinuousAttribute extends Attribute {
    
    private double max;
    private double min;

    /**
     * Costruttore di ContinuousAttribute.
     * @param name  nome del attributo 
     * @param index     indice del attributo 
     * @param min   valore minimo che può assumere
     * @param max   valore massimo che può assumere
     */
    ContinuousAttribute(String name, int index, double min, double max) {
        super(name, index);
        this.min = min;
        this.max = max;
    }

    /**
     * Funzione che restituisce il valore massimo del attributo.
     * @return max  valore massimo 
     */
    public double getMax() {
        return max;
    }

    /**
     * Funzione che restituisce il valore minimo del attributo.
     * @return min  valore minimo
     */
    public double getMin() {
        return min;
    }

    /**
     * Funzione che restituisce il valore v scalato.
     * @param v     valore che si vuole scalare
     * @return vScld valore scalato
     */
    public double getScaledValue(double v) {
        double vScld = (v-min)/(max-min);
        return vScld;
    }
}
