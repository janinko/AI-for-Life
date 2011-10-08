package eu.janinko.aiforlife.BreedManager;

import java.util.Random;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.Organism.PredatorAndPray.Pray;
import eu.janinko.aiforlife.Organism.PredatorAndPray.Predator;
import eu.janinko.aiforlife.Organism.PredatorAndPray.PredatorAndPrayOrganismManager;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.brain.DullGeneticInformation;

public class PredatorAndPrayBreedManager implements BreedManager {
	Random generator = new Random();
	World world;
	
	public PredatorAndPrayBreedManager(World world){
		this.world = world;
	}

	@Override
	public Organism breed(Organism[] parents) throws UnsupportedOrganismException {
		if(!(
				(parents.length == 1 && (parents[0] instanceof Predator))
			||	(parents.length == 2 && (parents[0] instanceof Pray) &&  (parents[1] instanceof Pray))
			))  throw new UnsupportedOrganismException();
		
		if(!(world.getOrganismManager() instanceof PredatorAndPrayOrganismManager)) throw new UnsupportedOrganismException();
		PredatorAndPrayOrganismManager om = (PredatorAndPrayOrganismManager) world.getOrganismManager();
		
		if(parents.length == 1){
			Predator p = om.buildPredator(world, new DullGeneticInformation(((Predator) parents[0]).getDNA()));
			mutate(p);
			return p;
		}
				
		Pray o1 = (Pray) parents[0];
		Pray o2 = (Pray) parents[1];

		DullGeneticInformation gc1 = o1.getDNA();
		DullGeneticInformation gc2 = o2.getDNA();
		int min = gc1.getLength();
		int max = gc2.getLength();
		if(max < min){
			int h = min;
			min = max;
			max = h;
		}

		int genlen = gc1.getGenLen();
		DullGeneticInformation gc = new DullGeneticInformation(genlen,0);
		
		int len;
		if(max == min){
			len = min;
		}else{
			len = generator.nextInt((max - min)) + min;
		}
		
		double[] gen = new double[genlen];
		
		for(int g=0; g< min; g++){
			switch(generator.nextInt(3)){
			case 0:{
				for(int i=0; i<genlen; i++){
					gen[i] = (gc1.getGenInfo(g, i) + gc2.getGenInfo(g, i)) / 2;
				}
				gc.appendGen(gen);
				break;
			}
			case 1:{
				gc.appendGen(gc1.getGen(g));
				break;
			}
			case 2:{
				gc.appendGen(gc2.getGen(g));
				break;
			}
			}
		}
		for(int g=min; g < len; g++){
			if(g < gc1.getLength()){
				gc.appendGen(gc1.getGen(g));
			}else{
				gc.appendGen(gc2.getGen(g));
			}
		}
		
		Organism o = om.buildPray(world, gc);
		if(generator.nextFloat() < 0.1){
			o = mutate(o);
		}
		
		return o;
	}

	@Override
	public Organism mutate(Organism o) throws UnsupportedOrganismException {
		DullGeneticInformation gc;
		if(o instanceof Pray){
			gc = ((Pray)o).getDNA();
		}else if(o instanceof Predator){
			gc = ((Predator)o).getDNA();
		}else{
			throw new UnsupportedOrganismException();
		}
		
		switch(generator.nextInt(4)){
		case 0:{
			gc.setBreedery(gc.getBreedery() + generator.nextDouble() / 10 - 0.05);
			break;
		}
		case 1:{
			int g = generator.nextInt(gc.getLength());
			int i = generator.nextInt(gc.getGenLen());
			gc.setGenInfo(g, i, gc.getGenInfo(g, i) + generator.nextDouble() - 0.5);
			break;
		}
		case 2:{
			double[] newgen = new double[gc.getGenLen()];
			for(int i=0; i< gc.getGenLen(); i++){
				newgen[i] = generator.nextDouble()*4-2;
			}
			gc.appendGen(newgen);
			break;
		}
		case 3:{
			gc.removeGen(generator.nextInt(gc.getLength()));
			break;
		} 
		}
	
		return o;
	}

	@Override
	public void maybeMutate(Organism o) throws UnsupportedOrganismException {
		if(generator.nextFloat() < 0.05){
			mutate(o);
		}
	}
	
	public void maybeMutate(Organism o, double p) throws UnsupportedOrganismException {
		if(generator.nextFloat() < (0.05 * p)){
			mutate(o);
		}
	}

}
