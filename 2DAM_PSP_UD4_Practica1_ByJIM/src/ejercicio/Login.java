package ejercicio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login {

	Login(){
	JFrame frame = new JFrame("Servidor FTP ByJIM®2021");
	frame.setBounds(550, 400, 330, 160);
	frame.setResizable(false);


	miPanel panel = new miPanel();
	frame.add(panel);

	frame.setVisible(true);
	}
}
	
class miPanelin extends JPanel implements ActionListener{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		miPanelin(){
			this.setLayout(null);

			lbUser = new JLabel("USUARIO:");
			lbUser.setBounds(10, 10, 100, 25);
			
			txtUser = new JTextField(20);
			txtUser.setBounds(130, 10, 160, 25);
			
			lbPass = new JLabel("PASSWORD:");
			lbPass.setBounds(10, 40, 100, 25);
			
			txtPass = new JPasswordField(20);
			txtPass.setBounds(130, 40, 160, 25);			
			
			btnCancelar = new JButton("CANCELAR");
			btnCancelar.setBounds(10, 80, 100, 25);	
			btnCancelar.addActionListener(this);
			
			btnOk = new JButton("ACEPTAR");
			btnOk.setBounds(190, 80, 100, 25);
			btnOk.addActionListener(this);
			
			this.add(lbUser);
			this.add(txtUser);
			this.add(lbPass);
			this.add(txtPass);
			this.add(btnCancelar);
			this.add(btnOk);
		}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnOk)) {
		
			//new Interfaz(txtUser.getText(),txtPass.getPassword());
			this.getParent().setVisible(false);
		}else {
			
		}
		System.exit(0);
	}
		
		
	private JLabel lbUser;
	private JTextField txtUser;
	private JLabel lbPass;
	private JPasswordField txtPass;
	private JButton btnCancelar;
	private JButton btnOk;


}
