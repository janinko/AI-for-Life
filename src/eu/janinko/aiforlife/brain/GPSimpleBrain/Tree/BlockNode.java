package eu.janinko.aiforlife.brain.GPSimpleBrain.Tree;

import java.util.ArrayList;
import java.util.Random;

import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainCatalog;
import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainTree;

public class BlockNode extends Node{
	private ArrayList<Node> nodes;
	private int in;
	
	public BlockNode(Node parent, Random generator, GPSimpleBrainCatalog catalog, int depth){
		super(parent);
		double modif = 1;
		if(depth > 10){
			modif = 10 / depth;
		}
		nodes = new ArrayList<Node>();
		while(generator.nextDouble() < 0.65 * modif){
			double rand = generator.nextDouble();
			if(rand < 1.5 - modif){
				nodes.add(new VoidNode(this, generator, catalog));
			}else if(rand < 1.8 - modif){
				nodes.add(new ConditionNode(this, generator, catalog, depth+1));
			}else{
				nodes.add(new ForNode(this,generator,catalog, depth+1));
			}
		}
	}

	@Override
	public Node execute(GPSimpleBrainTree bt){
		if(nodes.isEmpty()){
			if(parent == null){
				return this;
			}
			return parent.execute(bt);
		}
		if(active){
			if(in >= nodes.size()){
				if(parent == null){
					in = 1;
					return nodes.get(0).execute(bt);
				}
				active = false;
				return parent.execute(bt);
			}
			Node ret = nodes.get(in).execute(bt);
			return ret;
		}else{
			active = true;
			in = 1;
			return nodes.get(0).execute(bt);
		}
	}

	@Override
	public void print(int d) {
		for(int i=0; i<d; i++){
			System.out.print('\t');
		}
		System.out.println("Blk");
		for(Node n : nodes){
			n.print(d+1);
		}
	}
}
