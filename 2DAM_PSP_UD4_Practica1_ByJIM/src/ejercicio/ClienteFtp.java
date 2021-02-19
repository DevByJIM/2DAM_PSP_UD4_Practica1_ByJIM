package ejercicio;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;


public class ClienteFtp {

	FTPClient cliente = new FTPClient();
	String host = "localhost";
	String user =  "Jim";
	String pass = "byjim";
	
	
	
	public ClienteFtp() {
		
	}
	
	public String Conectar() {
		try {
			cliente.connect(host);
			cliente.enterLocalPassiveMode();
			if(cliente.login(user, pass)) {
				return cliente.getReplyString();
			}
			cliente.disconnect();
			return cliente.getReplyString();
				
			
		} catch (SocketException e) {
			e.printStackTrace();
			return cliente.getReplyString();
		} catch (IOException e) {
			e.printStackTrace();
			return cliente.getReplyString();
		}
	}
	
	public void Desconectar() {
		try {
			cliente.logout();
			cliente.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String DirActual() {
		try {
			return cliente.printWorkingDirectory();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	

	
	
}
