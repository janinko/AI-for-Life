package eu.janinko.aiforlife.brain.GPSimpleBrain.Tree;

import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainTree;

public abstract class Node{
	protected Node parent;
	protected boolean active = false;
	
	public Node(Node parent) {
		this.parent = parent;
	}

	public abstract Node execute(GPSimpleBrainTree bt);
	
	public abstract void print(int d);
}
