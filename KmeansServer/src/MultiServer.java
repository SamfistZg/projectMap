import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe principale, rappresenta un server in grado di ascoltare richieste da diversi client.
 */
public class MultiServer {
    private static final int DEFAULT_PORT = 8080;
    private static final String DEFAULT_IP_ADDRESS = "127.0.0.1";
    private int port;
    private String ip;

    /**
     * Costruttore di MultiServer.
     * @param port  numero di port
     * @throws IOException
     */
     public MultiServer(int port) throws IOException {
        this.port = port;
        this.ip = DEFAULT_IP_ADDRESS;
        run();
    }

    /**
     * Metodo che acquisisce richieste dal client.
     * @throws IOException
     */
    void run() throws IOException {
        InetAddress newAddress = InetAddress.getByName(ip);
        ServerSocket sS = new ServerSocket(port, 50, newAddress);
        try {
            while(true) {
                Socket s = sS.accept();
                try {
                    ServerOneClient sOneC = new ServerOneClient(s);
                } finally {
                    s.close();
                    }
                }
            } finally {
            sS.close();
            }
    } 

    public static void main(String [] args) {
        try {
            MultiServer ms = new MultiServer(DEFAULT_PORT); // il run è già nel costruttore
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
