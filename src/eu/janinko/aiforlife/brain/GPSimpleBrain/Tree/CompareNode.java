package eu.janinko.aiforlife.brain.GPSimpleBrain.Tree;

import java.util.Random;

import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainCatalog;
import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainTree;

public class CompareNode implements BoolNode{
	private DoubleNode child1;
	private DoubleNode child2;
	private int comparator;

	public CompareNode(Random generator, GPSimpleBrainCatalog catalog, int depth) {
		comparator = generator.nextInt(6);
		child1 = new DoubleNode(generator, catalog, depth + 1);
		child2 = new DoubleNode(generator, catalog, depth + 1);
	}

	@Override
	public boolean execute(GPSimpleBrainTree bt){
		switch(comparator){
		case 0: // ==
			return Math.round(child1.execute(bt) * 10) == Math.round(child2.execute(bt)*10);
		case 1: // !=
			return child1.execute(bt) != child2.execute(bt);
		case 2: // <
			return child1.execute(bt) <  child2.execute(bt);
		case 3: // <=
			return child1.execute(bt) <= child2.execute(bt);
		case 4: // >
			return child1.execute(bt) >  child2.execute(bt);
		case 5: // >=
			return child1.execute(bt) >= child2.execute(bt);
		}
		throw new RuntimeException("We shouldn't got here! comparator = " + comparator);
	}

	@Override
	public void print(int d) {
		for(int i=0; i<d; i++){
			System.out.print('\t');
		}
		System.out.print("Com");
		switch(comparator){
		case 0: System.out.println("=="); break;
		case 1: System.out.println("!="); break;
		case 2: System.out.println("<"); break;
		case 3: System.out.println("<="); break;
		case 4: System.out.println(">"); break;
		case 6: System.out.println(">="); break;
		}
		child1.print(d+1);
		child2.print(d+1);
	}
}