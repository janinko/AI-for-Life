package eu.janinko.aiforlife.Organism.DullOrganism;

import java.util.Random;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.Organism.OrganismManager;
import eu.janinko.aiforlife.World.World;

public class DullOrganismManager implements OrganismManager {

	private int startLiveMin = 50;
	private int startLiveMax = 51;
	
	Random generator = new Random();

	@Override
	public Organism buildOrganism(World w) {
		DullOrganism o = new DullOrganism(w, generator.nextInt(startLiveMax - startLiveMin) + startLiveMin);

		return o;
	}
	
	public Organism buildOrganism(World w, GeneticInformation geneticCode) {
		DullOrganism o = new DullOrganism(w, generator.nextInt(startLiveMax - startLiveMin) + startLiveMin, geneticCode);

		return o;
	}

	public void setStartLive(int startLive) {
		this.startLiveMin = startLive;
		this.startLiveMax = startLive+1;
	}
	
	public void setStartLive(int startLiveMin, int startLiveMax) {
		startLiveMax++;
		if(startLiveMax <= startLiveMin) return;
		this.startLiveMin = startLiveMin;
		this.startLiveMax = startLiveMax;
	}

	public int getStartLiveMin() {
		return startLiveMin;
	}
	
	public int getStartLiveMax() {
		return startLiveMax-1;
	}


}
