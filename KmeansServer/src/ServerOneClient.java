import mining.KMeansMiner;
import java.io.*;
import java.net.*;

public class ServerOneClient extends Thread {
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    KMeansMiner kmeans;

    /**
     * Costruttore di ServerOneClient.
     * @param s
     * @throws IOException
     */
    public ServeOneClient(Socket s) throws IOException {

    }

    /**
     * Metodo che riscrive il metodo run della superclasse Thread.
     */
    public void run() {
        
    }
}