package eu.janinko.aiforlife.brain.GPSimpleBrain;


import java.util.Random;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.brain.GPSimpleBrain.Tree.BlockNode;
import eu.janinko.aiforlife.brain.GPSimpleBrain.Tree.Node;

public class GPSimpleBrainTree {
	private BlockNode root;
	private Node active;
	private GPSimpleBrainCatalog catalog;
	
	private Random generator = new Random();
	private Organism organism;
	
	public GPSimpleBrainTree(Organism o, GPSimpleBrainCatalog catalog){
		this.catalog = catalog;
		root = new BlockNode(null, generator, catalog, 0);
		organism = o;
		active = root;
	}
	
	
	public BlockNode getRoot() {
		return root;
	}
	public void setRoot(BlockNode root) {
		this.root = root;
	}
	public GPSimpleBrainCatalog getCatalog() {
		return catalog;
	}
	public void setCatalog(GPSimpleBrainCatalog catalog) {
		this.catalog = catalog;
	}
	public void doVoidFunction(int fnumber) {
		catalog.doVoidFunction(fnumber, organism);
	}
	public double doDoubleFunction(int fnumber) {
		return catalog.doDoubleFunction(fnumber, organism);
	}
	public boolean doBooleanFunction(int fnumber) {
		return catalog.doBooleanFunction(fnumber, organism);
	}


	public void execute() {
		active = active.execute(this);
	}
}
