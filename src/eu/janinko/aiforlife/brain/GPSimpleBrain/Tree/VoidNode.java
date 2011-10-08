package eu.janinko.aiforlife.brain.GPSimpleBrain.Tree;

import java.util.Random;

import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainCatalog;
import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainTree;

public class VoidNode extends Node{
	private int fnumber;
	
	public VoidNode(Node parent, Random generator, GPSimpleBrainCatalog catalog) {
		super(parent);
		fnumber = generator.nextInt(catalog.getVoidFunctions());
	}

	@Override
	public Node execute(GPSimpleBrainTree bt){
		if(active){
			active=false;
			return parent.execute(bt);
		}else{
			active = true;
			bt.doVoidFunction(fnumber);
			return this;
		}
	}

	@Override
	public void print(int d) {
		for(int i=0; i<d; i++){
			System.out.print('\t');
		}
		System.out.println("V" + fnumber);
	}
	
}



