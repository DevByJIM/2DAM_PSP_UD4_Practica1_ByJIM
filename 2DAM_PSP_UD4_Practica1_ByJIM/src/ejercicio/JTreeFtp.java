package ejercicio;

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
    		
    		//actualizaNodo(raiz,f);
   	}
 }
     
    public void actualizarJtree(DefaultMutableTreeNode node) {
    	node.removeAllChildren();
		for (FTPFile f : cliente.getArchivos(node)) {
    		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(f.getName());
    		
    		node.add(raiz);
    		if(f.isDirectory()) {
    			raiz.add(new DefaultMutableTreeNode("vacía"));
    		}
    		
    		this.modelo.nodeChanged(node);
    		this.modelo.reload(node);		
    	}	
    }
    
    //EVENTOS DEL JTREE----------------------------------------------------------
	@Override
	public void treeExpanded(TreeExpansionEvent event) {
		TreePath path = event.getPath(); 
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
		node.removeAllChildren();
		for (FTPFile f : cliente.getArchivos(node)) {
    		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(f.getName());
    		
    		node.add(raiz);
    		if(f.isDirectory()) {
    			raiz.add(new DefaultMutableTreeNode("vacía"));
    		}
    		
    		this.modelo.nodeChanged(node);
    		this.modelo.reload(node);		
    	}			
	}

    @Override
	public void treeCollapsed(TreeExpansionEvent event) {
			
	}
}



