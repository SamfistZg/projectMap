import java.io.IOException;
import java.net.ServerSocket;

public class MultiServer {
    int port = 8080;


    /**
     * Metodo che acquisisce richieste dal client.
     * @throws IOException
     */
    void run() throws IOException {
        ServerSocket ss = new ServerSocket(port, port, null);
    }

    /**
     * Costruttore di MultiServer.
     * @param port
     * @throws IOException
     */
     public MultiServer(int port) throws IOException {
        this.port = port;
        run();

    }

    public static void main(String [] args) {
        MultiServer ms = new MultiServer();
    }




}
