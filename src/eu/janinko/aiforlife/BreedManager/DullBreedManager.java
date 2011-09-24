package eu.janinko.aiforlife.BreedManager;

import java.util.Random;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.Organism.OrganismManager;
import eu.janinko.aiforlife.Organism.DullOrganism.DullOrganism;
import eu.janinko.aiforlife.Organism.DullOrganism.DullOrganismManager;
import eu.janinko.aiforlife.Organism.DullOrganism.GeneticInformation;
import eu.janinko.aiforlife.Organism.DullOrganism.InvalidGeneticCodeException;
import eu.janinko.aiforlife.World.World;

public class DullBreedManager implements BreedManager {
	Random generator;
	World world;
	
	public DullBreedManager(World world){
		generator = new Random();
		this.world = world;
	}

	@Override
	public Organism breed(Organism[] parents) throws UnsupportedOrganismException {
		if(parents.length != 2) throw new UnsupportedOrganismException();
		if(!(parents[0] instanceof DullOrganism)) throw new UnsupportedOrganismException();
		if(!(parents[1] instanceof DullOrganism)) throw new UnsupportedOrganismException();
		
		OrganismManager om = world.getOrganismManager();
		if(!(om instanceof DullOrganismManager)) throw new UnsupportedOrganismException();
				
		DullOrganism o1 = (DullOrganism) parents[0];
		DullOrganism o2 = (DullOrganism) parents[1];

		DullOrganismManager dom = (DullOrganismManager) om;

		GeneticInformation gc1 = o1.getGeneticCode();
		GeneticInformation gc2 = o2.getGeneticCode();
		int min = gc1.getLength();
		int max = gc2.getLength();
		if(max < min){
			int h = min;
			min = max;
			max = h;
		}

		int genlen = gc1.getGenLen();
		GeneticInformation gc = new GeneticInformation(genlen,0);
		
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
		
		Organism o = dom.buildOrganism(world, gc);
		if(generator.nextFloat() < 0.1){
			o = mutate(o);
		}
		
		return o;
	}

	@Override
	public Organism mutate(Organism individual) throws UnsupportedOrganismException {
		if(!(individual instanceof DullOrganism)) throw new UnsupportedOrganismException();
		DullOrganism o = (DullOrganism) individual;

		GeneticInformation gc = o.getGeneticCode();
		
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
		
		
		try {
			o.setGeneticCode(gc);
		} catch (InvalidGeneticCodeException e) {
			e.printStackTrace();
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
