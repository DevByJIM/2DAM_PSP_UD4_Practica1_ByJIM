package ejercicio;

import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

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

    	for (File f : cliente.DameFiles()) {
    		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(f);
    		
    		top.add(raiz);
    		//actualizaNodo(raiz, f);   		
    	}
    }
    
    
    
//    private boolean actualizaNodo(DefaultMutableTreeNode nodo, File f) {
//        nodo.removeAllChildren();
//        System.out.println("1--------->>" + nodo.toString());
//        return actualizaNodo(nodo,f,2); 
//    }
//    
    public boolean cargarNodos(DefaultMutableTreeNode nodo,ArrayList<File> archivos) {
    	System.out.println(archivos.size());
    	for(File file: archivos) 
    	{
    		System.out.println("@11@ " + file.toString());
    		DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(file);	               
    		nodo.add(nuevo); 
    	}

    	return true; 
    }
//
//    private boolean actualizaNodo(DefaultMutableTreeNode nodo, File f, int profundidad) {
//
////    	for(File file: cliente.DameFiles("Carpeta1")) 
////    	{
////
////    		System.out.println("@11@ " + file.toString());
////    		DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(file);	               
////
////    		actualizaNodo(nuevo, file,profundidad-1); 
////    		nodo.add(nuevo); 
////    	}
//
//    	return true; 
//    }

    
    
    //EVENTOS DEL JTREE----------------------------------------------------------
	@Override
	public void treeCollapsed(TreeExpansionEvent event) {
		
		System.out.println("$$$$$$$ " + event.getSource());
		System.out.println(event.getPath());
	
			
	}



	@Override
	public void treeExpanded(TreeExpansionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
