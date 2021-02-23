package ejercicio;


import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


public class PanelArchivos implements TreeExpansionListener{
	    
	    private JTree jTree1;
	    private DefaultTreeModel modelo;
	    private ArrayList<File> Origenes;
	    private ClienteFtp cliente;
	    private String modo= "LOCAL";

	    public DefaultTreeModel getModelo() {
	        return modelo;
	    }


	    public PanelArchivos(JTree jTree1, ClienteFtp cliente) {
	        this.jTree1 = jTree1;	
	        this.cliente = cliente;
	    }

	    public void setOrigen(ArrayList<File>  origenes) {
	    	this.Origenes = origenes;
	    	modo= "FTP";
	    }
	    
	    public void setJTree(JTree jTree1) {
	        this.jTree1 = jTree1;
	    }

	    /**
	     * Metodo que permite enlazar los escuchas de eventos y permite actualizar 
	     */
	    public void iniciar(int modo) {
	        
	        if(modo == 1) {
	        	//creamos el nodo principal
		        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Mi PC:");
		        //creamos un modelo con el nodo que creamos principal
		        modelo = new DefaultTreeModel(top);
		        // seteamos el modelo y el escucha al componente 
		        jTree1.setModel(modelo);
		        jTree1.addTreeExpansionListener(this);
		        //extraemos todas las unidades disponibles en caso que tengamos C, D o otra
	        	for (File f : File.listRoots()) {
	        		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(f);
	        		//if(f.getPath().equals("C:\\")) {
	        		top.add(raiz);
	        		//hacemos un recorrido de dos niveles a partir de cada una unidad
	        		actualizaNodo(raiz, f); 
	        		//}
	        	}
	        }else {

		        DefaultMutableTreeNode top = new DefaultMutableTreeNode("FTP:");

		        modelo = new DefaultTreeModel(top);
		        jTree1.setModel(modelo);
		        jTree1.addTreeExpansionListener(this);
		        if(Origenes == null)return;
		        
	        for (File f : cliente.DameFiles()) {	        	
	        		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(f);
	        		System.out.println("-o->" + Origenes);
	        		top.add(raiz);

	        		actualizaNodo(raiz, f); 
	        		
	        	}
	        	
	        }
	    }

	    private boolean actualizaNodo(DefaultMutableTreeNode nodo, File f) {
	        nodo.removeAllChildren();
	        return actualizaNodo(nodo,f,2); 
	    }
	    
	    


	    private boolean actualizaNodo(DefaultMutableTreeNode nodo, File f, int profundidad) {
	       File[] files = f.listFiles(); 
	       if(files!=null && profundidad>0) 
	       {   
	           for(File file: files) 
	           {
	               DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(file);	               
	               //vuelve a mandar en caso que sea directorio 
	               actualizaNodo(nuevo, file,profundidad-1); 
	               nodo.add(nuevo); 
	              
	               
	           }
	       }
	       return true; 
	    }

	    @Override
	    public void treeExpanded(TreeExpansionEvent event) {
	    	if(modo=="LOCAL"){
	    		TreePath path = event.getPath(); // Se obtiene el path del nodo
	    		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
	    		// verifica que sea nodo valido
	    		if(node==null || !(node.getUserObject() instanceof File) ) return; 
	    		File f = (File) node.getUserObject();
	    		actualizaNodo(node, f);  //actualiza la estructura
	    		JTree tree = ( JTree) event.getSource(); 
	    		DefaultTreeModel model = (DefaultTreeModel)tree.getModel(); 
	    		model.nodeStructureChanged(node);
	    	}else {
	    		TreePath path = event.getPath(); // Se obtiene el path del nodo
	    		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
	    		// verifica que sea nodo valido
	    		if(node==null || !(node.getUserObject() instanceof File) ) return; 
	    		File f = (File) node.getUserObject();
	    		actualizaNodo(node, f);  //actualiza la estructura
	    		JTree tree = ( JTree) event.getSource(); 
	    		DefaultTreeModel model = (DefaultTreeModel)tree.getModel(); 
	    		model.nodeStructureChanged(node);
	    	}
	    }

	    @Override
	    public void treeCollapsed(TreeExpansionEvent event) { }


	}