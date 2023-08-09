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
        setSize(500, 500);
		setResizable(false);

        // Crea la prima scena
        Scene1 scene1 = new Scene1(this);
        setScene(scene1);

        setVisible(true);
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
	void storeTableFromDb(String nameTable) throws SocketException, ServerException, IOException, ClassNotFoundException{
		out.writeObject(0);
		out.writeObject(nameTable);
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
	 * Metodo che salva i cluster 
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

class Scene1 extends JPanel {
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

		add(panel);

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
		confirmButton.setBounds(100, 120, 300, 40);
		panel.add(Box.createVerticalStrut(10));
        panel.add(confirmButton);

        JButton previousButton = new JButton("ANNULLA");
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
 * Scena in cui si elabora un nuovo risultato.
 */
class Scene3 extends JPanel {
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
					while(true) 
					{
						try {
							m.storeTableFromDb(nameTable);
							break;
						} catch (SocketException e1) {
							System.out.println(e1.getMessage());
							return;
						} catch (FileNotFoundException e2) {
							System.out.println(e2.getMessage());
							return;
						} catch (IOException e3) {
							System.out.println(e3.getMessage());
							return;
						} catch (ClassNotFoundException e4) {
							System.out.println(e4.getMessage());
							return;
						} catch (ServerException e5) {
							System.out.println(e5.getMessage());
						}
					} 

					char answer = 'n';
					do {
						try {
							String nrCluster = textArea2.getText();
							String clusterSet = m.learningFromDbTable(nrCluster);
							m.storeClusterInFile();	
							MainTest mainTest = (MainTest) SwingUtilities.getWindowAncestor(Scene3.this);
							Scene4 scene4 = new Scene4(m, clusterSet);
							mainTest.setScene(scene4);
						} catch (SocketException e1) {
							System.out.println(e1.getMessage());
							return;
						} catch (FileNotFoundException e2) {
							System.out.println(e2.getMessage());
							return;
						} catch (ClassNotFoundException e3) {
							System.out.println(e3.getMessage());
							return;
						} catch (IOException e4) {
							System.out.println(e4.getMessage());
							return;
						} catch (ServerException e5) {
							System.out.println(e5.getMessage());
						}
						System.out.print("Vuoi ripetere l'esecuzione?(y/n)");
						//answer = Keyboard.readChar();
					} while(answer == 'y');
            }
        });	
		panel.add(Box.createVerticalStrut(10));
        panel.add(confirmButton);

        JButton previousButton = new JButton("ANNULLA");
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
 * Scena che stampa il risultato di un kmeans caricato da file.
 */
class Scene4 extends JPanel {
    public Scene4(MainTest m, String kmeans) {

        JLabel label1 = new JLabel(kmeans);
		//label1.setBounds(200, 50, 200, 40); 
		add(label1);

		JScrollPane scrollPane = new JScrollPane(label1);
		scrollPane.setPreferredSize(new Dimension(450, 400)); 
		add(scrollPane);

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