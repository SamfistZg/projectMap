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
    public ServerOneClient(Socket s) throws IOException {
        this.socket = s;
        out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
        start();  //start
    }

    /**
     * Metodo che riscrive il metodo run della superclasse Thread.
     */
    public void run() {
        try {
            // yeah, dovrei comprare un maglione di guci
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}