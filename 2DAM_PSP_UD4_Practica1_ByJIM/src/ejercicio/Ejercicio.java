package ejercicio;

import javax.swing.JOptionPane;



public class Ejercicio {

	public static void main(String[] args) {
		String usuario = JOptionPane.showInputDialog
				(null,"INTRODUCE TU NOMBRE DE USUARIO",
						"MultiCHAT ByJIM®2021",
						JOptionPane.QUESTION_MESSAGE);
				
		if(usuario!=null)
			new Interfaz();
	}

}
