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
import java.awt.BorderLayout;

import keyboardinput.Keyboard;

/**
 * Classe principale del programma, che gestisce la connessione al server e l'interfaccia grafica.
 */
public class MainTest extends JFrame {

	private ObjectOutputStream out;
	private ObjectInputStream in ; // stream con richieste del client
	private JPanel currentScene;
	private boolean connected;
	
	/**
	 * Costruttore di MainTest.
	 * @param ip
	 * @param port
	 * @throws IOException
	 */
	public MainTest(String ip, int port) throws IOException {
		super("Progetto MAP 2022/23");

		InetAddress addr = InetAddress.getByName(ip); //ip
		Socket socket = new Socket(addr, port); //Port

		connected = true;
		
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());

		setTitle("Progetto MAP 2022/23");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
		setResizable(false);

        Scene1 scene1 = new Scene1(this);
        setScene(scene1);

        setVisible(true);
	}

	/**
	 * Metodo che cambia la scena corrente con quella passata come paramentro.
	 * @param scene scena che andrà a sostituire la scena corrente.
	 */
	void setScene(JPanel scene) {
        if (currentScene != null) {
            remove(currentScene);
        }

        currentScene = scene;
        add(currentScene);
        revalidate();
        repaint();
    }

	/**
	 * Metodo che rivela se il programma è connesso o meno.
	 * @return bool
	 */
	boolean isConnected() {
		return connected;
	}

	/**
	 * Metodo che scrive in un flusso (out) un boolean passato come parametro.
	 * @param choice
	 */
	void writeBoolean(boolean choice) {
		try {
			out.writeObject(choice);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Metodo che chiude i due flussi (in e out).
	 */
	void closeConnection() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Metodo che prende le informazioni da tastiera da parte dell'utente e restituisce l'oggetto sul file precompilato.
	 * @return stringa letta da file, risultato del kmeans
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
	void storeTableFromDb(String nameTable) throws SocketException, ServerException, IOException, ClassNotFoundException{
		out.writeObject(0);
		out.writeObject(nameTable);
		String result = (String)in.readObject();
		if(!result.equals("OK"))
			throw new ServerException(result);
		
	}

	/**
	 * Metodo che legge da server socket e stampa i cluster.
	 * @return stringa, i cluster prodotti
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	String learningFromDbTable(String nrCluster) throws SocketException, ServerException, IOException, ClassNotFoundException{
		out.writeObject(1);
		int k = Integer.parseInt(nrCluster);
		out.writeObject(k);
		String result = (String)in.readObject();

		if(result.equals("OK")) {
			String text = "<html><br/>Clustering output: " + in.readObject() + "<br/>";
			return text + (String)in.readObject() + "</html>";
		}
		else throw new ServerException(result);
	}
	
	/**
	 * Metodo che salva i cluster su file.
	 */
	void storeClusterInFile() throws SocketException, ServerException, IOException, ClassNotFoundException{
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
	}

}

/**
 * Classe che rappresenta la scena1 cioè la schermata principale.
 */
class Scene1 extends JPanel {
	/**
	 * Costruttore di Scene1.
	 * @param m
	 */
    public Scene1(MainTest m) {
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createVerticalStrut(100));

		JLabel titleLabel = new JLabel("KMeans");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50)); 
		panel.add(titleLabel);
		panel.add(Box.createVerticalStrut(10));

		JLabel label = new JLabel("Scegli un'opzione: ");
		label.setFont(new Font("Arial", Font.BOLD, 20)); 
		label.setBounds(200, 50, 200, 40);   
		panel.add(label);
		panel.add(Box.createVerticalStrut(10));

		JButton button1 = new JButton("Carica un risultato da file");
		button1.setBounds(100, 120, 300, 40);   
		panel.add(button1);
		panel.add(Box.createVerticalStrut(10));

		JButton button2 = new JButton("Esegui un nuovo risultato");
		button2.setBounds(100, 200, 300, 40); 
		panel.add(button2);
		panel.add(Box.createVerticalStrut(10));

		add(panel);

		JPanel onlineStatus = new JPanel();
		onlineStatus.setPreferredSize(new Dimension(20, 20));

		JLabel connected = new JLabel();
		connected.setFont(new Font("Arial", Font.BOLD, 20)); 
		connected.setBounds(200, 50, 200, 40);

		if (m.isConnected()) {
			onlineStatus.setBackground(Color.GREEN);
			connected.setText("Connesso");
		} else {
			onlineStatus.setBackground(Color.RED);
			connected.setText("Non connesso");
		}
		
		add(onlineStatus);
		add(connected);

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

/**
 * Classe che rappresenta la scena2 cioè la scena per recuperare un kmeans precedentemente salvato.
 */
class Scene2 extends JPanel {
	/**
	 * Costruttore di Scene2.
	 * @param m
	 */
    public Scene2(MainTest m) {
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label1 = new JLabel("Nome tabella: ");
		label1.setFont(new Font("Arial", Font.BOLD, 20)); 
		label1.setBounds(200, 50, 200, 40); 
		panel.add(Box.createVerticalStrut(10));
		panel.add(label1);

		JTextArea textArea1 = new JTextArea("Inserisci nome tabella...");
        textArea1.setPreferredSize(new Dimension(200, 20)); 
		panel.add(Box.createVerticalStrut(10));
		panel.add(textArea1);

        JLabel label2 = new JLabel("Numero Iterate: ");
		label2.setFont(new Font("Arial", Font.BOLD, 20)); 
		label2.setBounds(200, 50, 200, 40); 
		panel.add(Box.createVerticalStrut(10));
		panel.add(label2);
		
		JTextArea textArea2 = new JTextArea("Inserisci numero iterate...");
        textArea2.setPreferredSize(new Dimension(200, 20)); 
		panel.add(Box.createVerticalStrut(10));
		panel.add(textArea2);

        JButton confirmButton = new JButton("Esegui l'operazione");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nameTable = textArea1.getText();
		        String nrIterate = textArea2.getText();     
				try {
					String kmeans = m.learningFromFile(nameTable, nrIterate);
					MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene2.this);
					Scene4 scene4 = new Scene4(m, kmeans);
					mainTest.setScene(scene4);
				} catch (SocketException ex) {
					MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene2.this);
					Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
					mainTest.setScene(scene7);
				}
				catch (FileNotFoundException ex) {
					MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene2.this);
					Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
					mainTest.setScene(scene7);
				} catch (IOException ex) {
					MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene2.this);
					Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
					mainTest.setScene(scene7);
				} catch (ClassNotFoundException ex) {
					MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene2.this);
					Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
					mainTest.setScene(scene7);
				}
				catch (ServerException ex) {
					MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene2.this);
					Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
					mainTest.setScene(scene7);
				}
            }
        });	
		confirmButton.setBounds(100, 120, 300, 40);
		panel.add(Box.createVerticalStrut(10));
        panel.add(confirmButton);

        JButton previousButton = new JButton("Annulla");
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene2.this);
                Scene1 scene1 = new Scene1(m);
                mainTest.setScene(scene1);
            }
        });
		previousButton.setBounds(100, 120, 300, 40);
		panel.add(Box.createVerticalStrut(10));
        panel.add(previousButton);

		add(panel);
    }
}

/*
 * Classe che rappresenta la scena3 cioè la scena in cui si vuole produrre un nuovo risultato.
 */
class Scene3 extends JPanel {
	/**
	 * Costruttore di Scene3.
	 * @param m
	 */
    public Scene3(MainTest m) {
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label1 = new JLabel("Nome tabella: ");
		label1.setFont(new Font("Arial", Font.BOLD, 20));
		label1.setBounds(200, 50, 200, 40);
		panel.add(Box.createVerticalStrut(10));
		panel.add(label1);

		JTextArea textArea1 = new JTextArea("Inserisci nome tabella...");
        textArea1.setPreferredSize(new Dimension(200, 20)); 
		panel.add(Box.createVerticalStrut(10));
		panel.add(textArea1);

		JLabel label2 = new JLabel("Numero di cluster: ");
		label2.setFont(new Font("Arial", Font.BOLD, 20)); 
		label2.setBounds(200, 50, 200, 40); 
		panel.add(Box.createVerticalStrut(10));
		panel.add(label2);
		
		JTextArea textArea2 = new JTextArea("Inserisci numero di cluster...");
        textArea2.setPreferredSize(new Dimension(200, 20)); 
		panel.add(Box.createVerticalStrut(10));
		panel.add(textArea2);

        JButton confirmButton = new JButton("Esegui l'operazione");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nameTable = textArea1.getText();
					while(true) {
						try {
							m.storeTableFromDb(nameTable);
							break;
						} catch (SocketException ex) {
							MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene3.this);
							Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
							mainTest.setScene(scene7);
						} catch (FileNotFoundException ex) {
							MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene3.this);
							Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
							mainTest.setScene(scene7);
						} catch (IOException ex) {
							MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene3.this);
							Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
							mainTest.setScene(scene7);
						} catch (ClassNotFoundException ex) {
							MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene3.this);
							Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
							mainTest.setScene(scene7);
						} catch (ServerException ex) {
							MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene3.this);
							Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
							mainTest.setScene(scene7);
						}
					}
					try {
						String nrCluster = textArea2.getText();
						String clusterSet = m.learningFromDbTable(nrCluster);
						m.storeClusterInFile();	
						MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene3.this);
						Scene5 scene5 = new Scene5(m, clusterSet);
						mainTest.setScene(scene5);
					} catch (SocketException ex) {
						MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene3.this);
						Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
						mainTest.setScene(scene7);
					} catch (FileNotFoundException ex) {
						MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene3.this);
						Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
						mainTest.setScene(scene7);
					} catch (ClassNotFoundException ex) {
						MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene3.this);
						Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
						mainTest.setScene(scene7);
					} catch (IOException ex) {
						MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene3.this);
						Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
						mainTest.setScene(scene7);
					} catch (ServerException ex) {
						MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene3.this);
						Scene7 scene7 = new Scene7(m, ex.getMessage() + ", riavvia il programma");
						mainTest.setScene(scene7);
					}
            }
        });	
		panel.add(Box.createVerticalStrut(10));
        panel.add(confirmButton);

        JButton previousButton = new JButton("Annulla");
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene3.this);
                Scene1 scene1 = new Scene1(m);
                mainTest.setScene(scene1);
            }
        });
		panel.add(Box.createVerticalStrut(10));
        panel.add(previousButton);
		add(panel);
    }
}

/*
 * Classe che rappresenta la scena4 cioè la scena in cui viene stampato il contenuto di un file.
 */
class Scene4 extends JPanel {
	/**
	 * Costruttore di Scene4.
	 * @param m
	 * @param read
	 */
    public Scene4(MainTest m, String read) {

        JLabel label1 = new JLabel(read);
		//label1.setBounds(200, 50, 200, 40); 
		add(label1);

		JScrollPane scrollPane = new JScrollPane(label1);
		scrollPane.setPreferredSize(new Dimension(450, 400)); 
		add(scrollPane);

        JButton previousButton = new JButton("Torna al menù principale");
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene4.this);
                Scene6 scene6 = new Scene6(m);
                mainTest.setScene(scene6);
            }
        });
        add(previousButton);
    }
}

/*
 * Classe che rappresenta la scena5 cioè la scena in cui viene stampato un nuovo risultato(kmeans).
 */
class Scene5 extends JPanel {
	/**
	 * Costruttore di Scene5.
	 * @param m
	 * @param result
	 */
    public Scene5(MainTest m, String result) {

        JLabel label1 = new JLabel(result);
		//label1.setBounds(200, 50, 200, 40); 
		add(label1);

		JScrollPane scrollPane = new JScrollPane(label1);
		scrollPane.setPreferredSize(new Dimension(450, 400)); 
		add(scrollPane);

		JButton redoButton = new JButton("Riprova");
        redoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				m.writeBoolean(true);
                MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene5.this);
                Scene3 scene3 = new Scene3(m);
                mainTest.setScene(scene3);
            }
        });
        add(redoButton);

        JButton previousButton = new JButton("Torna al menù principale");
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene5.this);
                Scene6 scene6 = new Scene6(m);
                mainTest.setScene(scene6);
            }
        });
        add(previousButton);
    }
}

/*
 * Classe che rappresenta la scena6 cioè la scena in cui si chiede all'utente se continuare ad utilizzare il programma o meno.
 */
class Scene6 extends JPanel {
    public Scene6(MainTest m) {

        JLabel label1 = new JLabel("Vuoi continuare?");
		//label1.setBounds(200, 50, 200, 40); 
		add(label1);

		JScrollPane scrollPane = new JScrollPane(label1);
		scrollPane.setPreferredSize(new Dimension(450, 400)); 
		add(scrollPane);

        JButton yesButton = new JButton("Sì");
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				m.writeBoolean(true);
                MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene6.this);
                Scene1 scene1 = new Scene1(m);
                mainTest.setScene(scene1);
            }
        });
        add(yesButton);

		JButton noButton = new JButton("No");
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				m.writeBoolean(false);
				MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene6.this);
				Scene7 scene7 = new Scene7(m, "Connessione interrotta, grazie per l'utilizzo");
				mainTest.setScene(scene7);
            }
        });
        add(noButton);
    }
}

/**
 * Classe che rappresenta la scena7 in cui si stampa un errore o si ringrazia l'utente per l'uso.
 */
class Scene7 extends JPanel {
    public Scene7(MainTest m, String read) {
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createVerticalStrut(100));

        JLabel label1 = new JLabel(read);
		label1.setBounds(200, 50, 200, 40);
		panel.add(label1);
		
		panel.add(Box.createVerticalStrut(10));

        JButton previousButton = new JButton("Chiudi");
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m.closeConnection();
                System.exit(0);
            }
        });
        panel.add(previousButton);

		add(panel);
    }
}
