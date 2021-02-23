package ejercicio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;


public class ClienteFtp {

	FTPClient cliente = new FTPClient();
	String host = "localhost";
	String user =  "Jim";
	String pass = "byjim";
	
	
	
	public ClienteFtp(String user, char[] pass) {
//		this.user = user;
//		this.pass = String.valueOf(pass);
		this.user = "Jim";
		this.pass = "byjim";
	}
	
	public String Conectar() {
		try {
			cliente.connect(host);
			cliente.enterLocalPassiveMode();
			if(cliente.login(user, pass)) {
				return cliente.getReplyString();
			}
			//cliente.disconnect();
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
	
	public boolean crearDirectorio(String path) {
		try {
			return cliente.makeDirectory(path);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean eliminarDirectorio(String path) {
		try {
			return cliente.removeDirectory(path);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean eliminarArchivo(String path) {
		try {
			return cliente.deleteFile(path);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean subirArchivo(String path) {		
		try {		
			BufferedInputStream inSt = new BufferedInputStream(new FileInputStream(path));
			if(cliente.storeFile(path, inSt)) {
				inSt.close();
				return true;
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean bajarArchivo(String path) {
		try {		
			BufferedOutputStream outSt = new BufferedOutputStream(new FileOutputStream(path));
			if(cliente.retrieveFile(path, outSt)) {
				outSt.close();
				return true;
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	public File RaizFtp() {
		try {
			return new File(cliente.getRemoteAddress().toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String[] Archivos() {
		try {
			return cliente.listNames();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
}
