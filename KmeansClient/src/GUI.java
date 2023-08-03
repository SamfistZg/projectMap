import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {
    
	public GUI() {

		super("Progetto MAP 2022/23");
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 500, 500);
        Container container = frame.getContentPane();
        container.setLayout(null);

        JLabel label = new JLabel("Scegli un'opzione: ");
        label.setBounds(200, 50, 200, 40);   
        container.add(label);
        JButton button1 = new JButton("Carica un risultato da file");
        button1.setBounds(100, 120, 300, 40);   
        container.add(button1);
        JButton button2 = new JButton("Esegui un nuovo risultato");
        button2.setBounds(100, 200, 300, 40); 
        container.add(button2);
        
        frame.setVisible(true);
        frame.setResizable(false);

        button1.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
            try {
                String kmeans = main.learningFromFile();
					System.out.println(kmeans);
				}
                catch (SocketException e1) {
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

        button2.addActionListener(new ActionListener() {
            @Override
           public void actionPerformed(ActionEvent e) {
            //case 2

           }
        });
	}
}