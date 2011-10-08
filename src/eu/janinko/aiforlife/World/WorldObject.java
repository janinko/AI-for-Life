package eu.janinko.aiforlife.World;

import eu.janinko.aiforlife.Organism.Organism;

public interface WorldObject {
	
	boolean isOrganism();
	
	Organism getOrganism();

	void attacked(Organism o, int attack);
	
	

}
