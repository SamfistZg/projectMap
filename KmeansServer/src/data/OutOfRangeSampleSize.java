package data;

/**
 * Classe che rappresenta un'eccezione personalizzata che viene sollevate quando si inserisce un numero di cluster minore di 0 o maggiore del numero di numberOfExamples.
 */
public class OutOfRangeSampleSize extends Exception {
    /**
     * Costruttore di OutOfRangeSampleSize.
     * @param message   stringa contenente il messaggio da stampare nel caso venga sollevata.
     */
    OutOfRangeSampleSize(String message) {
        super(message);
    }
    /* eccezione lanciata nella funzione corrispondente, toString rimosso in favore del getMessage
    public String toString() {
        return "Sample size out of range";
    }
    public static void wrongRange(int k, int distinctValue) throws OutOfRangeSampleSize {
        if (k <= 0 || k > distinctValue) {
            throw new OutOfRangeSampleSize("k is out of range");
        }
    }
    */
}
