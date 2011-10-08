package eu.janinko.aiforlife.brain.GPSimpleBrain.Tree;

import java.util.Random;

import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainCatalog;
import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainTree;

public class BooleanNode implements BoolNode{
	private int fnumber;
	
	public BooleanNode(Random generator, GPSimpleBrainCatalog catalog) {
		fnumber = generator.nextInt(catalog.getBooleanFunctions());
	}

	@Override
	public boolean execute(GPSimpleBrainTree bt) {
		return bt.doBooleanFunction(fnumber);
	}

	@Override
	public void print(int d) {
		for(int i=0; i<d; i++){
			System.out.print('\t');
		}
		System.out.println("Bool" + fnumber);
	}
	
	
}
