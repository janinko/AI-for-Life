package eu.janinko.aiforlife.brain.GPSimpleBrain.Tree;

import java.util.Random;

import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainCatalog;
import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainTree;

public class DoubleNode{
	private int type;
	private double c;
	private int fnumber;
	private DoubleNode d1 = null;
	private DoubleNode d2 = null;
	
	public DoubleNode(Random generator, GPSimpleBrainCatalog catalog, int depth) {
		double rand = generator.nextDouble();
		
		double modif = 1;
		if(depth > 10){
			modif = 10 / depth;
		}
		
		if(rand < 1.4 - modif){
			type = 0;
			c = generator.nextDouble() * 4 - 2;
		}else if(rand < 1.8 - modif){
			type = 1;
			fnumber = generator.nextInt(catalog.getDoubleFunctions());
		}else{
			type = 2;
			rand = generator.nextDouble();
			if(rand < 0.35){
				fnumber = 0;
			}else if (rand < 0.7){
				fnumber = 1;
			}else if (rand < 0.85){
				fnumber = 2;
			}else{
				fnumber = 3;
			}
			d1 = new DoubleNode(generator, catalog, depth + 1);
			d2 = new DoubleNode(generator, catalog, depth + 1);
		}
	}

	public double execute(GPSimpleBrainTree bt){
		switch(type){
		case 0: // Constant
			return c;
		case 1: // Function
			return bt.doDoubleFunction(fnumber);
		case 2: // Operation
		{
			switch(fnumber){
			case 0: // +
				return d1.execute(bt) + d2.execute(bt);
			case 1: // -
				return d1.execute(bt) - d2.execute(bt);
			case 2: // *
				return d1.execute(bt) * d2.execute(bt);
			case 3: // /
				double d = d2.execute(bt);
				if(d == 0){
					return 0;
				}
				return d1.execute(bt) / d;
			}
		}
		}
		throw new RuntimeException("We shouldn't got here!");
	}

	public void print(int d) {
		for(int i=0; i<d; i++){
			System.out.print('\t');
		}
		System.out.print("d");
		switch(type){
		case 0: System.out.println("C" + c); break;
		case 1: System.out.println("F" + fnumber); break;
		case 2: // Operation
		{
			switch(fnumber){
			case 0: // +
				System.out.println("+"); break;
			case 1: // -
				System.out.println("-"); break;
			case 2: // *
				System.out.println("*"); break;
			case 3: // /
				System.out.println("/"); break;
			}
			d1.print(d+1);
			d2.print(d+1);
		}
		}
	}
}