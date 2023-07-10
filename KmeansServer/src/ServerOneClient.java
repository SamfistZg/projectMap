import mining.KMeansMiner;
import java.io.*;
import java.net.*;
import java.sql.SQLException;

import data.Data;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;

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
        run();
    }

    /**
     * Metodo che riscrive il metodo run della superclasse Thread.
     */
    public void run() {
        try {
            System.out.println("SI, MA VAI PIANO");
            while(true)
            {
                int str = (Integer)in.readObject();
                switch(str){
                    case 3:
                        String nameFile = (String)in.readObject();
                        Data data = new Data(nameFile);
                        nameFile += "_" + (Integer)in.readObject();
                        out.writeObject("OK");
                        KMeansMiner kmeans = new KMeansMiner(nameFile);
                        out.writeObject(kmeans.getC().toString(data));
                        break;
                    case 0:
                        String nameTable = (String)in.readObject();
                        Data data_2 = new Data(nameTable);
                        out.writeObject("OK");    
                        break;
                    case 1:
                        int numeroIterate = (Integer)in.readObject();
                        out.writeObject("OK");
                        KMeansMiner kmeans_2 = new KMeansMiner(numeroIterate);
                        //out.writeObject(kmeans_2.getC().toString(data_2));
                    default:
                        System.out.println("Qualcosa è andato storto :/");
                }
        }
        } catch (IOException e) {
            System.out.println("1");
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("2");
            System.err.println(e.getMessage());
        } catch (SQLException e){
            System.out.println("3");
            System.err.println(e.getMessage());
        } catch (EmptySetException e){
            System.out.println("4");
            System.err.println(e.getMessage());
        } catch (NoValueException e){
            System.out.println("5");
            System.err.println(e.getMessage());
        } catch (DatabaseConnectionException e){
            System.out.println("6");
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