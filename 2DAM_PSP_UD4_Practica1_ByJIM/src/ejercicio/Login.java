package ejercicio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Login extends JDialog implements ActionListener{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Login() {
		this.setTitle("Servidor FTP ByJIM®2021");
		this.setBounds(550, 400, 320, 200);
		this.setResizable(false);
		this.setModal(true);


		miPanel panel = new miPanel();
		
		btnCancelar.addActionListener(this);
		btnOk.addActionListener(this);
		
		this.add(panel);

		this.setVisible(true);
	}
	
	
	
	
	
class miPanel extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		miPanel(){
			this.setLayout(null);

			lbServer = new JLabel("SERVIDOR:");
			lbServer.setBounds(10, 10, 100, 25);
			
			txtServer = new JTextField(20);
			txtServer.setBounds(130, 10, 160, 25);
			
			lbUser = new JLabel("USUARIO:");
			lbUser.setBounds(10, 40, 100, 25);
			
			txtUser = new JTextField(20);
			txtUser.setBounds(130, 40, 160, 25);
			
			lbPass = new JLabel("PASSWORD:");
			lbPass.setBounds(10, 70, 100, 25);
			
			txtPass = new JPasswordField(20);
			txtPass.setBounds(130, 70, 160, 25);			
			
			btnCancelar = new JButton("CANCELAR");
			btnCancelar.setBounds(10, 120, 100, 25);	
			
			
			btnOk = new JButton("ACEPTAR");
			btnOk.setBounds(190,120, 100, 25);

			this.add(lbServer);
			this.add(txtServer);
			this.add(lbUser);
			this.add(txtUser);
			this.add(lbPass);
			this.add(txtPass);
			this.add(btnCancelar);
			this.add(btnOk);
		}
			
}		

@Override
public void actionPerformed(ActionEvent e) {
	
	if(e.getSource().equals(btnOk)) {
		this.server = "LOCALHOST";
		this.user = txtUser.getText();
		this.pass = txtPass.getPassword();
		this.setVisible(false);
				
	}else {
		this.setVisible(false);
	}
}

	private JLabel lbServer;
	private JTextField txtServer;
	private JLabel lbUser;
	private JTextField txtUser;
	private JLabel lbPass;
	private JPasswordField txtPass;
	private JButton btnCancelar;
	private JButton btnOk;
	
	private String server;
	private String user;
	private char[] pass;
	
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public char[] getPass() {
		return pass;
	}

	public void setPass(char[] pass) {
		this.pass = pass;
	}






	



	
}
