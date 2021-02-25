package ejercicio;

import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.commons.net.ftp.FTPFile;

public class JTreeFtp implements TreeExpansionListener{

	private JTree jTree;
    private DefaultTreeModel modelo;
    //private ArrayList<File> Origenes;
    private ClienteFtp cliente;
    
    
    public JTreeFtp(JTree jTree, ClienteFtp cliente) {
    	
        this.jTree = jTree;	
        this.cliente = cliente;
        iniciar();

    }
    
    
    
    public void iniciar() {

    	DefaultMutableTreeNode top = new DefaultMutableTreeNode("FTP:");
    	modelo = new DefaultTreeModel(top);

    	jTree.setModel(modelo);
    	jTree.addTreeExpansionListener(this);
    	
 
    	for (FTPFile f : cliente.getArchivos()) {
    		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(f.getName());
    		top.add(raiz);
    		if(f.isDirectory()) 
    			raiz.add(new DefaultMutableTreeNode("null"));
    		
    		actualizaNodo(raiz,f);
    	}
    }
        
    private boolean actualizaNodo(DefaultMutableTreeNode nodo, FTPFile f) {
        nodo.removeAllChildren();
        System.out.println("1--------->>" + nodo.toString());
        return actualizaNodo(nodo,f,2); 
    }

    private boolean actualizaNodo(DefaultMutableTreeNode nodo, FTPFile f, int profundidad) {

    	for(FTPFile file: cliente.getArchivos(nodo)) 
    	{
    		if(file!=null && profundidad>0) 
    		{     			
    			//System.out.println("@11@ " + file.getName());
    			DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(file.getName());	               
    			nodo.add(nuevo);
    			if(file.isDirectory()) 
    				nodo.add(new DefaultMutableTreeNode("null"));
    			
    			actualizaNodo(nuevo, file, profundidad-1); 

    			//nodo.add(nuevo);

    		}
    	}

    	return true; 
    }

    
    
    //EVENTOS DEL JTREE----------------------------------------------------------
	@Override
	public void treeExpanded(TreeExpansionEvent event) {
		System.out.println(event.getPath());
		TreePath path = event.getPath(); // Se obtiene el path del nodo
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();

		for (FTPFile f : cliente.getArchivos(node)) {
			
    		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(f.getName());

    		node.add(raiz);
    		if(f.isDirectory()) {
    			node.add(new DefaultMutableTreeNode("null"));
    		}
    		actualizaNodo(raiz, f);   		
    	}
		
		
	}

    @Override
	public void treeCollapsed(TreeExpansionEvent event) {
			
	}
}



