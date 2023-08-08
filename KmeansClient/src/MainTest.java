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
	private JPanel currentScene;
	
	/**
	 * Costruttore di MainTest
	 * @param ip
	 * @param port
	 * @throws IOException
	 */
	public MainTest(String ip, int port) throws IOException {
		super("Progetto MAP 2022/23");

		InetAddress addr = InetAddress.getByName(ip); //ip
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, port); //Port
		System.out.println(socket);
		
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		setTitle("Progetto MAP 2022/23");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 600);

        // Crea la prima scena
        Scene1 scene1 = new Scene1(this);
        setScene(scene1);

        setVisible(true);
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

	public void setScene(JPanel scene) {
        if (currentScene != null) {
            remove(currentScene);
        }

        currentScene = scene;
        add(currentScene);
        revalidate();
        repaint();
    }
	
	/**
	 * Metodo che prende le informazioni da tastiera da parte dell'utente e restituisce l'oggetto sul file precompilato.
	 * @return
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	String learningFromFile(String nameTable , String nrIterate) throws SocketException, ServerException, IOException, ClassNotFoundException{
		
		out.writeObject(3);
		out.writeObject(nameTable);
		int k = Integer.parseInt(nrIterate);
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
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						new MainTest(ip, port);
					} catch (IOException e) {
					System.out.println(e);
					return;
					}
				}
			});


		/*
		main.button1.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
            try {
                String kmeans = main.learningFromFile();
				new Scene1().setVisible(true);
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
		*/



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


class Scene1 extends JPanel {
    public Scene1(MainTest m) {
		JLabel label = new JLabel("Scegli un'opzione: ");
		label.setBounds(200, 50, 200, 40);   
		add(label);
		JButton button1 = new JButton("Carica un risultato da file");
		button1.setBounds(100, 120, 300, 40);   
		add(button1);
		JButton button2 = new JButton("Esegui un nuovo risultato");
		button2.setBounds(100, 200, 300, 40); 
		add(button2);

        button1.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene1.this);
            Scene2 scene2 = new Scene2(m);
            mainTest.setScene(scene2);
			}
		});

		button2.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene1.this);
            Scene3 scene3 = new Scene3(m);
            mainTest.setScene(scene3);
			}
		});
    }
}

class Scene2 extends JPanel {
    public Scene2(MainTest m) {

        JLabel label1 = new JLabel("Nome tabella: ");
		label1.setBounds(200, 50, 200, 40); 
		add(label1);

		JTextArea textArea1 = new JTextArea("Inserisci nome tabella...");
        textArea1.setPreferredSize(new Dimension(250, 20)); 
		add(textArea1);

        JLabel label2 = new JLabel("Numero Iterate: ");
		label2.setBounds(200, 50, 200, 40); 
		add(label2);
		
		JTextArea textArea2 = new JTextArea("Inserisci numero iterate...");
        textArea2.setPreferredSize(new Dimension(250, 20)); 
		add(textArea2);

        JButton confirmButton = new JButton("Esegui l'operazione");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nameTable = textArea1.getText();
		        String nrIterate = textArea2.getText();     
				try{
					String kmeans = m.learningFromFile(nameTable, nrIterate);
					MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene2.this);
					Scene4 scene4 = new Scene4(m, kmeans);
					mainTest.setScene(scene4);
				} catch (SocketException e1) {
						System.out.println(e1);
						return;
					}
					catch (FileNotFoundException e2) {
						System.out.println(e2);
						return ;
					} catch (IOException e3) {
						System.out.println(e3);
						return;
					} catch (ClassNotFoundException e4) {
						System.out.println(e4);
						return;
					}
					catch (ServerException e5) {
						System.out.println(e5.getMessage());
					}
            }
        });	
        add(confirmButton);

        JButton previousButton = new JButton("ANNULLA");
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene2.this);
                Scene1 scene1 = new Scene1(m);
                mainTest.setScene(scene1);
            }
        });

        add(previousButton);
    }
}

/*
 * Scena in cui si vuole rinizializzare un risultato.
 */
class Scene3 extends JPanel {
    public Scene3(MainTest m) {

        JLabel label1 = new JLabel("Nome tabella: ");
		label1.setBounds(200, 50, 200, 40); 
		add(label1);

		JTextArea textArea1 = new JTextArea("Inserisci nome tabella...");
        textArea1.setPreferredSize(new Dimension(250, 20)); 
		add(textArea1);

        JButton confirmButton = new JButton("Esegui l'operazione");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nameTable = textArea1.getText();     
            }
        });	
        add(confirmButton);

        JButton previousButton = new JButton("ANNULLA");
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene3.this);
                Scene1 scene1 = new Scene1(m);
                mainTest.setScene(scene1);
            }
        });

        add(previousButton);
    }
}

class Scene4 extends JPanel {
    public Scene4(MainTest m, String kmeans) {

        JLabel label1 = new JLabel(kmeans);
		//label1.setBounds(200, 50, 200, 40); 
		add(label1);

        JButton previousButton = new JButton("ANNULLA");
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene4.this);
                Scene1 scene1 = new Scene1(m);
                mainTest.setScene(scene1);
            }
        });

        add(previousButton);
    }
}