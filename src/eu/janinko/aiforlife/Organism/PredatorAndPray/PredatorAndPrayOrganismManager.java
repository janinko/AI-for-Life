package eu.janinko.aiforlife.Organism.PredatorAndPray;

import java.util.Random;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.Organism.OrganismManager;
import eu.janinko.aiforlife.Organism.DullOrganism.GeneticInformation;
import eu.janinko.aiforlife.World.World;

public class PredatorAndPrayOrganismManager implements OrganismManager {

	private double prayProbability = 0.8;

	private boolean useStrictPray = false;
	private boolean useStrictPredator = false;
	
	private Random generator = new Random();

	@Override
	public Organism buildOrganism(World w) {
		Organism o;
		if(generator.nextDouble() < prayProbability){
			if(useStrictPray){
				o = new StrictPray(w);
			}else{
				o = new Pray(w);
			}
		}else{
			if(useStrictPredator){
				o = new StrictPredator(w);
			}else{
				o = new Predator(w);
			}
		}

		return o;
	}
	
	public Organism buildOrganism(World w, GeneticInformation geneticCode) {
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
	
	public Pray buildPray(World w, GeneticInformation geneticCode){
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
	
	public Predator buildPredator(World w, GeneticInformation geneticCode){
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
}
