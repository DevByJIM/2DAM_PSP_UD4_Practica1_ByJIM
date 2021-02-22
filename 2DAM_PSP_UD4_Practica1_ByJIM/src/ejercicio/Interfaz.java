package ejercicio;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class Interfaz extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Interfaz(String user, char[] pass) {
		ClienteFtp cliente = new ClienteFtp(user, pass);

		if(cliente.Conectar().toString().contains("530")) {
			JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta");
			System.exit(0);
		};
		
		System.out.println(cliente.DirActual());
		
		crearComponentes();
		
		this.setVisible(true);
	}
	
	private void crearComponentes() {
		this.setBounds(200, 200, 1020, 730);
        this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		panel = new JPanel();
		panel.setBackground(Color.BLUE);
		panel.setLayout(null);
		this.add(panel);
		
		lbTitulo = new JLabel("·· ADMINISTRADOR FTP ··",SwingConstants.CENTER);
		lbTitulo.setBounds(60, 10, 900, 50);
		lbTitulo.setForeground(Color.WHITE);
		lbTitulo.setFont(fuenteTitulo);

		lbLogo = new JLabel("Desarrollado por José Ignacio Martínez. ByJIM®2021.",SwingConstants.RIGHT);
		lbLogo.setBounds(60, 640, 900, 50);
		lbLogo.setForeground(Color.WHITE);
		lbLogo.setFont(fuenteLogo);
		
		FolderLocal = new JTree();
		FolderLocal.setBounds(30, 130, 400, 350);

		miPanel = new PanelArchivos();
		miPanel.setJTree(FolderLocal);
		miPanel.init();
		
		FolderServidor = new JTree();
		FolderServidor.setBounds(570, 130, 400, 350);
		
		txtDiario = new JTextArea();
		txtDiario.setBounds(30, 500, 940, 140);
		
		txtLocal = new JTextField();
		txtLocal.setBounds(30, 100, 400, 25);
		
		txtServidor = new JTextField();
		txtServidor.setBounds(570, 100, 400, 25);
		
		btnSubir = new JButton("SUBIR");
		btnSubir.setBounds(445, 170, 110, 120);
		
		btnBajar = new JButton("BAJAR");
		btnBajar.setBounds(445, 320, 110, 120);
		
		panel.add(FolderLocal);
		panel.add(FolderServidor);
		panel.add(txtDiario);
		panel.add(txtLocal);
		panel.add(txtServidor);
		panel.add(btnSubir);
		panel.add(btnBajar);
		panel.add(lbTitulo);
		panel.add(lbLogo);
	}
	
	Font fuenteTitulo= new Font("CAMBRIA",Font.BOLD,48);
	Font fuenteLogo = new Font("Segoe UI",Font.BOLD,18);
	Font fuentePizarra = new Font("Segoe UI",Font.PLAIN,16);
	

	private JLabel lbTitulo;
	private JLabel lbLogo;
	private JPanel panel;
	private JButton btnSubir;
	private JButton btnBajar;
	private JTextField txtLocal;
	private JTextField txtServidor;
	private JTree FolderLocal;
	private PanelArchivos miPanel;
	private JTree FolderServidor;
	private JTextArea txtDiario;
}
