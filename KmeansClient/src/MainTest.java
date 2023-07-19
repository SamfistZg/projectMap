import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import keyboardinput.Keyboard;

public class MainTest extends JFrame {

	private ObjectOutputStream out;
	private ObjectInputStream in ; // stream con richieste del client
	private JFrame frame;
	private Container container;
	private JLabel label;
	private JButton button1;
	private JButton button2;
	
	/**
	 * Costruttore di MainTest
	 * @param ip
	 * @param port
	 * @throws IOException
	 */
	public MainTest(String ip, int port) throws IOException{
		super("Progetto MAP 2022/23");

		InetAddress addr = InetAddress.getByName(ip); //ip
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, port); //Port
		System.out.println(socket);
		
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());

        this.frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 500, 500);
        this.container = frame.getContentPane();
        container.setLayout(null);

        this.label = new JLabel("Scegli un'opzione: ");
        label.setBounds(200, 50, 200, 40);   
        container.add(label);
        this.button1 = new JButton("Carica un risultato da file");
        button1.setBounds(100, 120, 300, 40);   
        container.add(button1);
        this.button2 = new JButton("Esegui un nuovo risultato");
        button2.setBounds(100, 200, 300, 40); 
        container.add(button2);
        
        frame.setVisible(true);
        frame.setResizable(false);
	}
	
	/**
	 * Metodo che stampa il menu e restituisce la scelta.
	 * @return
	 */
	private int menu() {
		int answer;
		System.out.println("Scegli un'opzione");
		do {
			System.out.println("(1) Carica un risultato precedente da file");
			System.out.println("(2) Esegui un nuovo risultato");
			System.out.print("Risposta:");
			answer=Keyboard.readInt();
		}
		while(answer<=0 || answer>2);
		return answer;
	}
	
	/**
	 * Metodo che prende le informazioni da tastiera da parte dell'utente e restituisce l'oggetto sul file precompilato.
	 * @return
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private String learningFromFile() throws SocketException, ServerException, IOException, ClassNotFoundException{
		
		out.writeObject(3);
		System.out.print("Nome tabella:");
		String tabName = Keyboard.readString();
		out.writeObject(tabName);
		System.out.print("Numero iterate:");
		int k = Keyboard.readInt();
		out.writeObject(k);
		String result = (String)in.readObject();
		if(result.equals("OK"))
			return (String)in.readObject();
		else throw new ServerException(result);
	}

	/**
	 * Metodo che chiede da tastiera all'utente il nome della tabella e lo restituisce al server socket.
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void storeTableFromDb() throws SocketException, ServerException, IOException, ClassNotFoundException{
		out.writeObject(0);
		System.out.print("Nome tabella:");
		String tabName = Keyboard.readString();
		out.writeObject(tabName);
		String result = (String)in.readObject();
		if(!result.equals("OK"))
			throw new ServerException(result);
		
	}

	/**
	 * Metodo che legge da server socket e stampa i cluster.
	 * @return
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private String learningFromDbTable() throws SocketException, ServerException, IOException, ClassNotFoundException{
		out.writeObject(1);
		System.out.print("Numero di cluster:");
		int k = Keyboard.readInt();
		out.writeObject(k);
		String result = (String)in.readObject();
		if(result.equals("OK")){
			System.out.println("Clustering output:" + in.readObject());
			return (String)in.readObject();
		}
		else throw new ServerException(result);
	}
	
	/**
	 * Metodo che salva i cluster 
	 */
	private void storeClusterInFile() throws SocketException, ServerException, IOException, ClassNotFoundException{
		out.writeObject(2);
		String result = (String)in.readObject();
		if(!result.equals("OK"))
			 throw new ServerException(result);
	}

	public static void main(String[] args) {
		String ip;
		int port;
		if (args.length > 0) {
			ip = args[0];
			port = Integer.parseInt(args[1]);
		} else {
			ip = "127.0.0.1";
			port = 8080;
		}

		
		MainTest main = null;
		try {
			main = new MainTest(ip, port);
		} catch (IOException e) {
			System.out.println(e);
			return;
		}

		main.button1.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
            try {
                String kmeans = main.learningFromFile();
					System.out.println(kmeans);
				} catch (SocketException e1) {
                    System.out.println(e1);
                    return;
                } catch (FileNotFoundException e2) {
                    System.out.println(e2);
                    return ;
                } catch (IOException e3) {
                    System.out.println(e3);
                    return;
                } catch (ClassNotFoundException e4) {
                    System.out.println(e4);
                    return;
                } catch (ServerException e5) {
                    System.out.println(e5.getMessage());
                }
            
           }
        });

        main.button2.addActionListener(new ActionListener() {
            @Override
           public void actionPerformed(ActionEvent e) {
            //case 2

           }
        });
		/*
		do {
			int menuAnswer = main.menu();
			switch(menuAnswer)
			{
				case 1:
					try {
						String kmeans = main.learningFromFile();
						System.out.println(kmeans);
					}
					catch (SocketException e) {
						System.out.println(e);
						return;
					}
					catch (FileNotFoundException e) {
						System.out.println(e);
						return ;
					} catch (IOException e) {
						System.out.println(e);
						return;
					} catch (ClassNotFoundException e) {
						System.out.println(e);
						return;
					}
					catch (ServerException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2: // learning from db
				
					while(true) 
					{
						try {
							main.storeTableFromDb();
							break; //esce fuori dal while
						} catch (SocketException e) {
							System.out.println(e);
							return;
						} catch (FileNotFoundException e) {
							System.out.println(e);
							return;
							
						} catch (IOException e) {
							System.out.println(e);
							return;
						} catch (ClassNotFoundException e) {
							System.out.println(e);
							return;
						} catch (ServerException e) {
							System.out.println(e.getMessage());
						}
					} //end while [viene fuori dal while con un db (in alternativa il programma termina)
						
					char answer = 'y';//itera per learning al variare di k
					do {
						try {
							String clusterSet = main.learningFromDbTable();
							System.out.println(clusterSet);
							main.storeClusterInFile();	
						} catch (SocketException e) {
							System.out.println(e);
							return;
						} catch (FileNotFoundException e) {
							System.out.println(e);
							return;
						} catch (ClassNotFoundException e) {
							System.out.println(e);
							return;
						} catch (IOException e) {
							System.out.println(e);
							return;
						} catch (ServerException e) {
							System.out.println(e.getMessage());
						}
						System.out.print("Vuoi ripetere l'esecuzione?(y/n)");
						answer = Keyboard.readChar();
					} while(answer == 'y');
					break; //fine case 2
					default:
					System.out.println("Opzione non valida!");
			}
			
			System.out.print("Vuoi scegliere una nuova operazione da menu?(y/n)");
			if (Keyboard.readChar()!='y')
				break;
			} while(true);
	*/		
	}
}