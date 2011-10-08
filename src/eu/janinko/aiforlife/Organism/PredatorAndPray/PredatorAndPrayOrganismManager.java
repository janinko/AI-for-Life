package eu.janinko.aiforlife.Organism.PredatorAndPray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.Organism.OrganismManager;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.brain.DullGeneticInformation;

public class PredatorAndPrayOrganismManager implements OrganismManager {

	private double prayProbability = 0.8;

	private boolean useStrictPray = false;
	private boolean useStrictPredator = false;
	
	private Random generator = new Random();

	private ArrayList<Pray> bestPrays = new ArrayList<Pray>();
	private ArrayList<Predator> bestPredators = new ArrayList<Predator>();

	@Override
	public Organism buildOrganism(World w) {
		Organism o;
		if(generator.nextDouble() < prayProbability){
			if(bestPrays.size() > 0){
				return bestPrays.remove(0);
			}
			if(useStrictPray){
				o = new StrictPray(w);
			}else{
				o = new Pray(w);
			}
		}else{
			if(bestPredators.size() > 0){
				return bestPredators.remove(0);
			}
			if(useStrictPredator){
				o = new StrictPredator(w);
			}else{
				o = new Predator(w);
			}
		}

		return o;
	}
	
	public Organism buildOrganism(World w, DullGeneticInformation geneticCode) {
		Organism o;
		if(generator.nextDouble() < prayProbability){
			if(useStrictPray){
				o = new StrictPray(w, geneticCode);
			}else{
				o = new Pray(w, geneticCode);
			}
		}else{
			if(useStrictPredator){
				o = new StrictPredator(w, geneticCode);
			}else{
				o = new Predator(w, geneticCode);
			}
		}

		return o;
	}

	public Pray buildPray(World w){
		if(useStrictPray){
			return new StrictPray(w);
		}else{
			return new Pray(w);
		}
	}
	
	public Pray buildPray(World w, DullGeneticInformation geneticCode){
		if(useStrictPray){
			return new StrictPray(w,geneticCode);
		}else{
			return new Pray(w,geneticCode);
		}
	}

	public Predator buildPredator(World w){
		if(useStrictPredator){
			return new StrictPredator(w);
		}else{
			return new Predator(w);
		}
	}
	
	public Predator buildPredator(World w, DullGeneticInformation geneticCode){
		if(useStrictPredator){
			return new StrictPredator(w,geneticCode);
		}else{
			return new Predator(w,geneticCode);
		}
	}

	public double getPrayProbability() {
		return prayProbability;
	}

	public void setPrayProbability(double prayProbability) {
		if(prayProbability < 0){
			prayProbability = 0;
		}else if(prayProbability > 1){
			prayProbability = 1;
		}
		this.prayProbability = prayProbability;
	}

	public boolean isUseStrictPray() {
		return useStrictPray;
	}

	public void setUseStrictPray(boolean useStrictPray) {
		this.useStrictPray = useStrictPray;
	}

	public boolean isUseStrictPredator() {
		return useStrictPredator;
	}

	public void setUseStrictPredator(boolean useStrictPredator) {
		this.useStrictPredator = useStrictPredator;
	}

	@Override
	public void saveBestOrganism(Set<Organism> organisms) {
		for(Organism o : organisms){
			if(o instanceof StrictPray){
				int size = bestPrays.size();
				int i;
				for(i=0 ; i < size && (o.getScore() > bestPrays.get(i).getScore()) ; i++);
				if(size + 1 >= 80){
					if(i == 0) continue;
					bestPrays.add(i,new StrictPray((StrictPray) o));
					bestPrays.remove(0);
				}else{
					bestPrays.add(i,new StrictPray((StrictPray) o));
				}
			}else if(o instanceof StrictPredator){
				int size = bestPredators.size();
				int i;
				for(i=0 ; i < size && (o.getScore() > bestPredators.get(i).getScore()) ; i++);
				if(size + 1 >= 20){
					if(i == 0) continue;
					bestPredators.add(i,new StrictPredator((StrictPredator) o));
					bestPredators.remove(0);
				}else{
					bestPredators.add(i,new StrictPredator((StrictPredator) o));
				}
			}
		}
	}
}
