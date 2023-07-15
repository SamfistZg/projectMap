import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private JTextArea textArea;
    private JButton button1;
    private JButton button2;

	public GUI() {

		super("Progetto MAP 2022/23");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        textArea = new JTextArea("Scegli un'opzione: ");
        button1 = new JButton("Carica un risultato da file");
        button2 = new JButton("Esegui un nuovo risultato");

        add(textArea);
        add(button1);
        add(button2);
        textArea.setSize(200, 40);
        button1.setSize(200, 40);
        button2.setSize(200, 40);

        textArea.setLocation(100, 50);
        button1.setLocation(100, 100);
        button2.setLocation(100,150);

        button1.addActionListener(new ActionListener() {
           // dare i via al case 1 
        });

        button2.addActionListener(new ActionListener() {
            // dare il via al case 2
        });
	}
}