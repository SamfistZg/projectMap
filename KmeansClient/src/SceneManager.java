import javax.swing.*;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SceneManager extends JFrame {
    private JPanel currentScene;

    public SceneManager() {
        setTitle("Progetto MAP 2022/23");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Crea la prima scena
        Scene1 scene1 = new Scene1();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SceneManager();
            }
        });
    }
}

class Scene1 extends JPanel {
    public Scene1() {
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
			SceneManager sceneManager = (SceneManager) SwingUtilities.getWindowAncestor(Scene1.this);
            Scene2 scene2 = new Scene2();
            sceneManager.setScene(scene2);
			}
		});

		button2.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			SceneManager sceneManager = (SceneManager) SwingUtilities.getWindowAncestor(Scene1.this);
            Scene3 scene3 = new Scene3();
            sceneManager.setScene(scene3);
			}
		});
    }
}

class Scene2 extends JPanel {
    public Scene2() {

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
            }
        });	
        add(confirmButton);

        JButton previousButton = new JButton("ANNULLA");
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SceneManager sceneManager = (SceneManager) SwingUtilities.getWindowAncestor(Scene2.this);
                Scene1 scene1 = new Scene1();
                sceneManager.setScene(scene1);
            }
        });

        add(previousButton);
    }
}

class Scene3 extends JPanel {
    public Scene3() {

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
                SceneManager sceneManager = (SceneManager) SwingUtilities.getWindowAncestor(Scene3.this);
                Scene1 scene1 = new Scene1();
                sceneManager.setScene(scene1);
            }
        });

        add(previousButton);
    }
}