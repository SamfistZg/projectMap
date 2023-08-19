import mining.KMeansMiner;
import java.io.*;
import java.net.*;
import java.sql.SQLException;

import data.Data;
import data.OutOfRangeSampleSize;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;

/**
 * Classe che rappresenta il ServerOneClient, stabilisce la connessione tra il server e il client.
 */
public class ServerOneClient extends Thread {
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    /**
     * Costruttore di ServerOneClient.
     * @param s     socket 
     * @throws IOException
     */
    public ServerOneClient(Socket s) throws IOException {
        this.socket = s;
        out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
        run();
    }

    /**
     * Metodo che riscrive il metodo run della superclasse Thread.
     */
    public void run() {
        try {
            boolean next = true;
            KMeansMiner kmeans = null;
            Data data = null;
            String nameTable = null;
            int iterations = 0;
            do {
                int str = (Integer)in.readObject();
                switch(str){
                    case 0:
                        nameTable = (String)in.readObject();
                        data = new Data(nameTable);
                        out.writeObject("OK");   
                        break;
                    case 1:
                        int numeroCluster = (Integer)in.readObject();
                        out.writeObject("OK");
                        kmeans = new KMeansMiner(numeroCluster);
                        iterations = kmeans.kmeans(data);
                        out.writeObject(iterations + " iterations");
                        out.writeObject(kmeans.getC().toString(data));
                        break;
                    case 2:
                        kmeans.salva(nameTable + "_" + iterations + ".dat");
                        out.writeObject("OK");
                        next = (Boolean)in.readObject();
                        break;
                    case 3:
                        String nameFile = (String)in.readObject();
                        data = new Data(nameFile);
                        nameFile += "_" + (Integer)in.readObject() + ".dat";
                        out.writeObject("OK");
                        kmeans = new KMeansMiner(nameFile);
                        out.writeObject(kmeans.getC().toString(data));
                        next = (Boolean)in.readObject();
                        break;
                    default:
                        System.out.println("Qualcosa è andato storto :/");
                }
            } while (next);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e){
            System.err.println(e.getMessage());
        } catch (EmptySetException e) {
            System.err.println(e.getMessage());
        } catch (NoValueException e) {
            System.err.println(e.getMessage());
        } catch (DatabaseConnectionException e) {
            System.err.println(e.getMessage());
        } catch (OutOfRangeSampleSize e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}