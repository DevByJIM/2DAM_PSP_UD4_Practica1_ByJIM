package ejercicio;

import java.awt.Color;

import javax.swing.*;

public class Interfaz extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Interfaz() {
		ClienteFtp cliente = new ClienteFtp();

		System.out.println(cliente.Conectar());
		
		System.out.println(cliente.DirActual());
		
		crearComponentes();
		
		this.setVisible(true);
	}
	
	private void crearComponentes() {
		this.setBounds(100, 100, 800, 530);
        this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		panel = new JPanel();
		panel.setBackground(Color.BLUE);
		panel.setLayout(null);
		this.add(panel);
		
		JTree carpetas = new JTree();
		panel.add(carpetas);
	}


	
	private JPanel panel;
}
