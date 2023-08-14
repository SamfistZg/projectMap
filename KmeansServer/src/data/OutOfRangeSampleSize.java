package data;

/**
 * Classe che rappresenta un'eccezione personalizzata che viene lanciata quando si inserisce un numero di cluster minore di 0 o maggiore del numero di numberOfExamples.
 */
public class OutOfRangeSampleSize extends Exception {
    /**
     * Costruttore di OutOfRangeSampleSize.
     * @param message   stringa contenente il messaggio da stampare nel caso l'eccezione venga sollevata.
     */
    OutOfRangeSampleSize(String message) {
        super(message);
    }
}
