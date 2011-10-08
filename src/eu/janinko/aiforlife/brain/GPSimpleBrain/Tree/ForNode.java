package eu.janinko.aiforlife.brain.GPSimpleBrain.Tree;

import java.util.Random;

import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainCatalog;
import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainTree;

public class ForNode extends Node{
	private BlockNode nodeBlock;
	private int repeats = 1;
	private int act;
	
	public ForNode(Node parent, Random generator, GPSimpleBrainCatalog catalog, int depth) {
		super(parent);
		double modif = 1;
		if(depth > 10){
			modif = 10 / depth;
		}
		while(generator.nextDouble() < 0.66 * modif){
			repeats++;
		}
		nodeBlock = new BlockNode(this, generator, catalog, depth+1);
	}

	@Override
	public Node execute(GPSimpleBrainTree bt) {
		if(active){
			if(act < repeats){
				act++;
				return nodeBlock.execute(bt);
			}else{
				active = true;
				return parent.execute(bt);
			}
		}else{
			act = 0;
			if(act < repeats){
				active = true;
				act++;
				return nodeBlock.execute(bt);
			}else{
				return parent.execute(bt);
			}
		}
	}

	@Override
	public void print(int d) {
		for(int i=0; i<d; i++){
			System.out.print('\t');
		}
		System.out.println("F" + repeats);
		nodeBlock.print(d+1);
	}
	
}
