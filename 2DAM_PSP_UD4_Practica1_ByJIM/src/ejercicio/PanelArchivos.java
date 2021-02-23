package ejercicio;


import java.io.File;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


public class PanelArchivos implements TreeExpansionListener{
	    
	    private JTree jTree1;
	    private DefaultTreeModel modelo;
	    private String[] Origenes;

	    public DefaultTreeModel getModelo() {
	        return modelo;
	    }


	    public PanelArchivos(JTree jTree1) {
	        this.jTree1 = jTree1;
	        
	    }

	    public void setOrigen(String[] origenes) {
	    	this.Origenes = origenes;
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
	        		if(f.getPath().equals("C:\\")) {
	        		top.add(raiz);
	        		//hacemos un recorrido de dos niveles a partir de cada una unidad
	        		actualizaNodo(raiz, f); 
	        		}
	        	}
	        }else {
	        	//creamos el nodo principal
		        DefaultMutableTreeNode top = new DefaultMutableTreeNode("FTP:");
		        //creamos un modelo con el nodo que creamos principal
		        modelo = new DefaultTreeModel(top);
		        // seteamos el modelo y el escucha al componente 
		        jTree1.setModel(modelo);
		        jTree1.addTreeExpansionListener(this);
		        if(Origenes == null)return;


		        
	        for (String f : Origenes) {
	        	
		        	File fila = new File(f);
	        		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(fila);
	        		
	        		top.add(raiz);
	        		//hacemos un recorrido de dos niveles a partir de cada una unidad
	        		actualizaNodo(raiz, fila); 
	        		
	        	}
	        	
	        }
	    }

	    private boolean actualizaNodo(DefaultMutableTreeNode nodo, File f) {
	        //quitamos lo que tenga el nodo 
	        nodo.removeAllChildren();
	        //recursivamente mandamos actualizar
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

	    @Override
	    public void treeCollapsed(TreeExpansionEvent event) { }


	}