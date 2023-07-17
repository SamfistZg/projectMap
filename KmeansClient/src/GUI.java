import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

    private JTextArea textArea;
    private JButton button1;
    private JButton button2;

	public GUI() {

		super("Progetto MAP 2022/23");
        setSize(1000, 600);
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
        textArea.setSize(400, 60);
        button1.setSize(400, 60);
        button2.setSize(400, 60);

        textArea.setLocation(300, 50);
        button1.setLocation(300, 150);
        button2.setLocation(300,250);

        button1.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
            //case 1
            
           }
        });

        button2.addActionListener(new ActionListener() {
            @Override
           public void actionPerformed(ActionEvent e) {
            //case 2

           }
        });
	}
}