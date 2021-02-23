package ejercicio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;

import org.apache.commons.net.ftp.FTPFile;


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
				
			
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "El servidor esta apagado.");
			return "No Conectado";
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
	
	public File RaizActual() {
		try {
			return new File(cliente.printWorkingDirectory());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
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
	
	public ArrayList<File> DameFiles() {
		try {
			
			ArrayList<File> archivos = new ArrayList<File>();
			
			for(FTPFile ftpfile: cliente.listFiles()) {
				
				InputStream iStream=cliente.retrieveFileStream(ftpfile.getName());
				File file = new File(ftpfile.getName());
				FileUtils.copyInputStreamToFile(iStream, file);
				archivos.add(file);
			}
			return archivos;
			

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
