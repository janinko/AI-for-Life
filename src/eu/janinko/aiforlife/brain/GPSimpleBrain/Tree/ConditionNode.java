package eu.janinko.aiforlife.brain.GPSimpleBrain.Tree;

import java.util.Random;

import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainCatalog;
import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainTree;

public class ConditionNode extends Node{
	private BoolNode condition;
	private BlockNode trueBlock;
	private BlockNode falseBlock = null;
	
	public ConditionNode(Node parent, Random generator, GPSimpleBrainCatalog catalog, int depth) {
		super(parent);
		double modif = 1;
		if(depth > 10){
			modif = 10 / depth;
		}
		
		double rand = generator.nextDouble();
		if(rand < 1.5 - modif ){
			condition = new BooleanNode(generator, catalog);
		}else if(rand < 1.8 - modif){
			condition = new CompareNode(generator, catalog, depth + 1);
		}else{
			condition = new LogicNode(generator, catalog, depth + 1);
		}
		trueBlock = new BlockNode(parent, generator, catalog, depth + 1);
		if(generator.nextDouble() > 0.5 * modif){
			falseBlock = new BlockNode(parent, generator, catalog, depth + 1);
		}
	}

	@Override
	public Node execute(GPSimpleBrainTree bt) {
		
		if(active){
			active = false;
			return parent.execute(bt);
		}else{
			if(condition.execute(bt)){
				active = true;
				return trueBlock.execute(bt);
			}else if(falseBlock != null){
				active = true;
				return falseBlock.execute(bt);
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
		System.out.println("Con");
		condition.print(d+1);
		trueBlock.print(d+1);
		if(falseBlock != null){
			falseBlock.print(d+1);
		}
	}
	
}
