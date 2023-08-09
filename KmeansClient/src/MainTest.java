import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import keyboardinput.Keyboard;
public class MainTest {

	private ObjectOutputStream out;
	private ObjectInputStream in ; // stream con richieste del client
	
	/**
	 * Costruttore di MainTest
	 * @param ip
	 * @param port
	 * @throws IOException
	 */
	public MainTest(String ip, int port) throws IOException{
		InetAddress addr = InetAddress.getByName(ip); //ip
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, port); //Port
		System.out.println(socket);
		
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
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
			System.out.print("Risposta: ");
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
		System.out.print("Nome tabella: ");
		String tabName = Keyboard.readString();
		out.writeObject(tabName);
		System.out.print("Numero iterate: ");
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
		System.out.print("Nome tabella: ");
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
		System.out.print("Numero di cluster: ");
		int k = Keyboard.readInt();
		out.writeObject(k);
		String result = (String)in.readObject();
		if(result.equals("OK")){
			System.out.println("Clustering output: " + in.readObject());
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
		boolean iter = true;
		do {
			int menuAnswer = main.menu();
			switch(menuAnswer) {
				case 1:
					try {
						String kmeans = main.learningFromFile();
						System.out.println(kmeans);
					}
					catch (SocketException e) {
						System.err.println(e.getMessage());
						return;
					}
					catch (FileNotFoundException e) {
						System.err.println(e.getMessage());
						return ;
					} catch (IOException e) {
						System.err.println(e.getMessage());
						return;
					} catch (ClassNotFoundException e) {
						System.err.println(e.getMessage());
						return;
					}
					catch (ServerException e) {
						System.err.println(e.getMessage());
					}
					break;
				case 2: // learning from db
					while(true) {
						try {
							main.storeTableFromDb();
							break; //esce fuori dal while
						} catch (SocketException e) {
							System.err.println(e.getMessage());
							return;
						} catch (FileNotFoundException e) {
							System.err.println(e.getMessage());
							return;
							
						} catch (IOException e) {
							System.err.println(e.getMessage());
							return;
						} catch (ClassNotFoundException e) {
							System.err.println(e.getMessage());
							return;
						} catch (ServerException e) {
							System.err.println(e.getMessage());
						}
					} //end while [viene fuori dal while con un db (in alternativa il programma termina)
						
					char answer = 'y';//itera per learning al variare di k
					do {
						try {
							String clusterSet = main.learningFromDbTable();
							System.out.println(clusterSet);
							main.storeClusterInFile();	
						} catch (SocketException e) {
							System.err.println(e.getMessage());
							return;
						} catch (FileNotFoundException e) {
							System.err.println(e.getMessage());
							return;
						} catch (ClassNotFoundException e) {
							System.err.println(e.getMessage());
							return;
						} catch (IOException e) {
							System.err.println(e.getMessage());
							return;
						} catch (ServerException e) {
							System.err.println(e.getMessage());
						}
						do {
							System.out.println("Vuoi ripetere l'esecuzione? (y/n)");
							System.out.print("Risposta: ");
							answer = Keyboard.readChar();
							if (answer == 'y') {
								try {
									main.out.writeObject(true);
								} catch (IOException e) {
									System.err.println(e.getMessage());
								}
							} else if (answer != 'n') {
								System.out.println("Risposta non valida!");
							}
						} while (answer != 'y' && answer != 'n');
						/*System.out.println("Vuoi ripetere l'esecuzione? (y/n)");
						System.out.print("Risposta: ");
						answer = Keyboard.readChar();
						if (answer == 'y') {
							try {
								main.out.writeObject(true);
							} catch (IOException e) {
								System.err.println(e.getMessage());
							}
						}*/
					} while(answer == 'y');
					break; //fine case 2
				default:
					System.out.println("Opzione non valida!");
			}
			char next;
			do {
				System.out.println("Vuoi scegliere una nuova operazione da menu? (y/n)");
				System.out.print("Risposta: ");
				next = Keyboard.readChar();
				if (next == 'n') {
					try {
						main.out.writeObject(false);
						main.out.close();
						main.in.close();
					} catch (IOException e) {
						System.err.println(e.getMessage());
					}
					iter = false;
				} else if (next == 'y') {
					try {
						main.out.writeObject(true);
					} catch (IOException e) {
						System.err.println(e.getMessage());
					}
				} else {
					System.out.println("Risposta non valida!");
				}
			} while (next != 'y' && next != 'n');
			/*if (Keyboard.readChar()!='y') {
				try {
					main.out.writeObject(false);
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
				break;
			} else {
				try {
					main.out.writeObject(true);
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}*/
		} while(iter);
	}
}