package ejercicio;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


public class Interfaz extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ClienteFtp cliente;
	Boolean Conectado = false;
	String servidor; 
	String user = "JIM";
	char[] pass = null;

	public Interfaz() {
		Iniciar();
	}
	
	public Interfaz(String user, char[] pass) {
		this.user = user;
		this.pass = pass;
		Iniciar();
	}
	
	private void Iniciar() {
		
			 		
		crearComponentes();
		crearArbolLocal();
		crearArbolServidor();
		
		this.setVisible(true);
	}
	
	private void ConectarServer() {
		try{
			cliente = new ClienteFtp(servidor,user, pass);
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "El servidor esta apagado.");
			
		}

		if(cliente.Conectar().toString().contains("530")) {
			JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta");
			System.exit(0);
		};
	}
	
	
	
	private void crearComponentes() {
		this.setBounds(200, 200, 1020, 730);
        this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		panel = new JPanel();
		panel.setBackground(COLOR_FONDO);
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
				
		txtDiario = new JTextArea();
		txtDiario.setBounds(30, 500, 940, 140);
		
		txtLocal = new JTextField();
		txtLocal.setBounds(30, 100, 400, 25);
		
		txtServidor = new JTextField();
		txtServidor.setBounds(570, 100, 400, 25);
		
		btnConectar = new JButton("CONECTAR");
		btnConectar.setBounds(445, 130, 110, 50);
		btnConectar.addActionListener(this);
		
		btnSubir = new JButton("SUBIR");
		btnSubir.setBounds(445, 210, 110, 100);
		btnSubir.setIcon(new ImageIcon("./Imagenes/flechaDch.png"));
		btnSubir.setHorizontalTextPosition( SwingConstants.CENTER );
		btnSubir.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnSubir.setEnabled(false);
		btnSubir.addActionListener(this);
		
		btnBajar = new JButton("BAJAR");
		btnBajar.setBounds(445, 330, 110, 100);
		btnBajar.setIcon(new ImageIcon("./Imagenes/flechaIzq.png"));
		btnBajar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnBajar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnBajar.setEnabled(false);
		btnBajar.addActionListener(this);
		
		btnMakeDir = new JButton("CREAR DIRECTORIO");
		btnMakeDir.setBounds(570, 440, 180, 40);
		btnMakeDir.setEnabled(false);
		btnMakeDir.addActionListener(this);
		
		btnErase = new JButton("ELIMINAR OBJETO");
		btnErase.setBounds(790, 440, 180, 40);
		btnErase.setEnabled(false);
		btnErase.addActionListener(this);
		

		panel.add(txtDiario);
		panel.add(txtLocal);
		panel.add(txtServidor);
		panel.add(btnConectar);
		panel.add(btnMakeDir);
		panel.add(btnErase);
		panel.add(btnSubir);
		panel.add(btnBajar);
		panel.add(lbTitulo);
		panel.add(lbLogo);
	}
	
	
	private void crearArbolLocal() {
		//PARTE QUE CREA EL ARBOL DE ARCHIVOS LOCALES ..........................
		FolderLocal = new JTree();
		JScrollPane scrollLocal = new JScrollPane(FolderLocal);
		scrollLocal.setBounds(30, 130, 400, 350);
		
		arbolLocal = new PanelArchivos(FolderLocal);
		arbolLocal.iniciar(1);
		
		FolderLocal.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				TreePath path = e.getPath();
				Object[] nodos = path.getPath();
				
				DefaultMutableTreeNode ultimoNodo = 
					(DefaultMutableTreeNode)nodos[nodos.length-1];
				
				System.out.println("-->" + ultimoNodo.toString());
				txtLocal.setText(ultimoNodo.toString());
			}
		});
		panel.add(scrollLocal);
	}
	
	
	private void crearArbolServidor() {
		//PARTE QUE CREA EL ARBOL DE ARCHIVOS DEL SERVIDOR .....................
				FolderServidor = new JTree();
				FolderServidor.removeAll();				
				JScrollPane scrollServidor = new JScrollPane(FolderServidor);
				scrollServidor.setBounds(570, 130, 400, 300);
				scrollServidor.revalidate();
				
				
				if(Conectado) {

					FolderServidor.setEnabled(true);
					arbolServidor = new JTreeFtp(FolderServidor, cliente);
					FolderServidor.addTreeSelectionListener(new TreeSelectionListener() {
						
						@Override
						public void valueChanged(TreeSelectionEvent e) {
							TreePath path = e.getPath();
							Object[] nodos = path.getPath();
							System.out.println("asdsdHIOlasd");
							DefaultMutableTreeNode ultimoNodo = 
								(DefaultMutableTreeNode)nodos[nodos.length-1];

							txtServidor.setText(cliente.damePathCompleto(ultimoNodo));
						}
					});		
					panel.add(scrollServidor);
				}else {
					FolderServidor.setEnabled(false);
					JPanel panelin = new JPanel();
					panelin.setBounds(570, 130, 400, 300);
					panel.add(panelin);
				}
				

		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String Ahora = LocalDate.now() + ". " +  LocalTime.now();
		if(e.getSource().equals(btnMakeDir)) {
			String nameCarpeta = JOptionPane.showInputDialog(null,"Nombre de la nueva carpeta",
			        		"ByJIM®2021", JOptionPane.QUESTION_MESSAGE); 
			if(cliente.crearDirectorio(txtServidor.getText() + "\\"+  nameCarpeta))
				txtDiario.append("Se ha creado la carpeta " + txtServidor.getText() + "\\"+  nameCarpeta + " en el servidor FTP. " + Ahora + ".\n");

		}else if(e.getSource().equals(btnErase)) {
			String elemento = txtServidor.getText();
			
			if(elemento.substring(elemento.length()-4, elemento.length()-3).equals(".")){
				
				if(cliente.eliminarArchivo(elemento))
					txtDiario.append("Se ha eliminado el archivo " + txtServidor.getText() + " en el servidor FTP. " + Ahora + ".\n");

			}else {
				
				if(cliente.eliminarDirectorio(elemento))
					txtDiario.append("Se ha eliminado el directorio " + txtServidor.getText() + " en el servidor FTP. " + Ahora + ".\n");
			}

		}else if(e.getSource().equals(btnSubir)) {
			cliente.subirArchivo(new File(txtLocal.getText()), txtServidor.getText());
			arbolServidor.actualizarJtree(new DefaultMutableTreeNode(txtServidor.getText()));
			
			txtDiario.append("Se ha subido el archivo " + txtLocal.getText() + " en el servidor FTP. " + Ahora + ".\n");
		
		}else if(e.getSource().equals(btnBajar)) {
			cliente.bajarArchivo(txtLocal.getText(), txtServidor.getText());
			
			txtDiario.append("Se ha bajado el archivo " + txtServidor.getText() + " del servidor FTP. " + Ahora + ".\n");

			
		}else if(e.getSource().equals(btnConectar)) {
			if(btnConectar.getText().equals("CONECTAR")) {

				Login login = new Login();
				Activadores(true);
				this.servidor = login.getServer();
				this.user = login.getUser();
				this.pass = login.getPass();
				System.out.println(Conectado);
				btnConectar.setText("DisCONECT");				
				ConectarServer();
				crearArbolServidor();
				txtDiario.append("Conectado al Servidor FTP: " + this.servidor + "." + Ahora + ".\n");
				
			}
			else {
				btnConectar.setText("CONECTAR");
				txtDiario.append("Se ha desconectado del Servidor FTP: " + this.servidor + "." + Ahora + ".\n");
				Activadores(false);
			}

		}
		crearArbolServidor();
		
	}
	
	private void Activadores(boolean valor) {
		FolderServidor.setEnabled(valor);
		this.Conectado = valor;
		btnSubir.setEnabled(valor);
		btnBajar.setEnabled(valor);
		btnMakeDir.setEnabled(valor);
		btnErase.setEnabled(valor);		
	}
	
	
	
	Font fuenteTitulo= new Font("CAMBRIA",Font.BOLD,48);
	Font fuenteLogo = new Font("Segoe UI",Font.BOLD,18);
	Font fuentePizarra = new Font("Segoe UI",Font.PLAIN,16);
	
	public static final Color COLOR_FONDO = new Color(0,128,255);
	

	private JLabel lbTitulo;
	private JLabel lbLogo;
	private JPanel panel;
	private JButton btnConectar;
	private JButton btnMakeDir;
	private JButton btnErase;
	private JButton btnSubir;
	private JButton btnBajar;
	private JTextField txtLocal;
	private JTextField txtServidor;
	private JTree FolderLocal;
	private PanelArchivos arbolLocal;
	private JTreeFtp arbolServidor;
	private JTree FolderServidor;
	private JTextArea txtDiario;


}
