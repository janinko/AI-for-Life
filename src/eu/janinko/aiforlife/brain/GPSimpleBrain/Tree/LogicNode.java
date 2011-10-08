package eu.janinko.aiforlife.brain.GPSimpleBrain.Tree;

import java.util.Random;

import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainCatalog;
import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainTree;

public class LogicNode implements BoolNode{
	private BoolNode child1;
	private BoolNode child2 = null;
	private int logic;

	public LogicNode(Random generator, GPSimpleBrainCatalog catalog, int depth) {
		logic = generator.nextInt(4);
		double modif = 1;
		if(depth > 10){
			modif = 10 / depth;
		}
		
		double rand = generator.nextDouble();
		if(rand < 1.5 - modif){
			child1 = new BooleanNode(generator, catalog);
		}else if(rand < 1.8 - modif){
			child1 = new CompareNode(generator, catalog, depth + 1);
		}else{
			child1 = new LogicNode(generator, catalog, depth + 1);
		}
		if(logic > 1){
			rand = generator.nextDouble();
			if(rand < 1.5 - modif){
				child2 = new BooleanNode(generator, catalog);
			}else if(rand < 1.8 - modif){
				child2 = new CompareNode(generator, catalog, depth + 1);
			}else{
				child2 = new LogicNode(generator, catalog, depth + 1);
			}
		}
	}

	@Override
	public boolean execute(GPSimpleBrainTree bt){
		switch(logic){
		case 0: // nothing
			return child1.execute(bt);
		case 1: // negation
			return !child1.execute(bt);
		case 2: // and
			return !child1.execute(bt) && child2.execute(bt);
		case 3: // or
			return !child1.execute(bt) || child2.execute(bt);
		}
		throw new RuntimeException("We shouldn't got here!");
	}

	@Override
	public void print(int d) {
		for(int i=0; i<d; i++){
			System.out.print('\t');
		}
		System.out.print("L");
		switch(logic){
		case 0: System.out.println(" "); break;
		case 1: System.out.println("!"); break;
		case 2: System.out.println("&&"); break;
		case 3: System.out.println("||"); break;
		}
		child1.print(d+1);
		if(child2 != null){
			child2.print(d+1);
		}
	}
}

