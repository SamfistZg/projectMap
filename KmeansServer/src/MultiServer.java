import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
    private static final int DEFAULT_PORT = 8080;
    private int port;


    /**
     * Metodo che acquisisce richieste dal client.
     * @throws IOException
     */
    void run() throws IOException {
        ServerSocket sS = new ServerSocket(port);
        try{
            Socket s = sS.accept();
            try {
                ServerOneClient sOneC = new ServerOneClient(s);
            } finally {
                s.close();
            }
        } finally {
            sS.close();
        }

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
        MultiServer ms = null;
        try{
            ms = new MultiServer(DEFAULT_PORT);
            ms.run();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        
    }




}
