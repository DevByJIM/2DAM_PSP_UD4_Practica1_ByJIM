package ejercicio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;

import org.apache.commons.net.ftp.FTPFile;


public class ClienteFtp {

	FTPClient cliente = new FTPClient();
	String host = "localhost";
	String user =  "Jim";
	String pass = "byjim";
	
	public ClienteFtp() {};
	
	public ClienteFtp(String host, String user, char[] pass) {
//		this.user = user;
//		this.pass = String.valueOf(pass);
		this.host = "localhost";
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
			cliente.makeDirectory(path);
			cliente.changeWorkingDirectory("/");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean eliminarDirectorio(String path) {
		try {
			FTPFile[] archivos = cliente.listFiles(path);
			
			if(archivos.length == 0) {
				return cliente.removeDirectory(path);
			}
			
			for(FTPFile file: archivos) {
				if(file.isDirectory()) {
					eliminarDirectorio(path + "/"+ file.getName());
				}
				eliminarArchivo(path + "/"+ file.getName());
			}
			cliente.removeDirectory(path);

			cliente.changeWorkingDirectory("/");
			return true;
		
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean eliminarArchivo(String path) {
		try {
			cliente.deleteFile(path);
			cliente.changeWorkingDirectory("/");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean subirArchivo(File pathLocal, String pathRemoto) {		
		try {		
			BufferedInputStream inSt = new BufferedInputStream(new FileInputStream(pathLocal));
			cliente.changeWorkingDirectory(pathRemoto);
			
			if(cliente.storeFile(pathLocal.getName(), inSt)) {
				inSt.close();
				cliente.changeWorkingDirectory("/");
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

	
	public boolean bajarArchivo(String pathLocal, String pathRemoto) {
		try {	
			BufferedOutputStream outSt = new BufferedOutputStream(
					new FileOutputStream(pathLocal + dameNombreArchivo(pathRemoto)));

			if(cliente.retrieveFile(pathRemoto, outSt)) {
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
	
	
	public String dameNombreArchivo(String pathRemoto) {
		
		int posicion = pathRemoto.lastIndexOf("/");
		
		return pathRemoto.substring(posicion);
		
	}
	
	
	
	public ArrayList<File> DameFiles() {
		try {
			
			ArrayList<File> archivos = new ArrayList<File>();
			
			for(FTPFile ftpfile: cliente.listFiles()) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				cliente.retrieveFile(ftpfile.getName(), outputStream);
				InputStream iStream=new ByteArrayInputStream(outputStream.toByteArray());
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
	
	public ArrayList<File> DameFiles(String Path) {
		try {

			ArrayList<File> archivos = new ArrayList<File>();
				
				
				if(!Path.equals("FTP:"))
				for(FTPFile ftpfile: cliente.listFiles(this.DirActual() + Path)) {
					
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					cliente.retrieveFile(ftpfile.getName(), outputStream);
					InputStream iStream=new ByteArrayInputStream(outputStream.toByteArray());
							
					File file = new File(ftpfile.getName());
					FileUtils.copyInputStreamToFile(iStream, file);
					archivos.add(file);
				}
				
				System.out.println(cliente.getReplyString());
				return archivos;


		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
		
	
	public FTPFile[] getArchivos() {
		
		try {
			return cliente.listFiles();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public FTPFile[] getArchivos(DefaultMutableTreeNode node) {
		
		try {
			return cliente.listFiles(damePathCompleto(node));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String damePathCompleto(DefaultMutableTreeNode node) {
		String ruta= "/" + node.toString();
		DefaultMutableTreeNode padre = (DefaultMutableTreeNode) node.getParent();
		
		if(padre!=null) {
			ruta = damePathCompleto(padre) + ruta;
		}else {
			return "";
		}
		System.out.println("RUTACOMPLETA" + ruta);
		return ruta;
		
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
