package eu.janinko.aiforlife.Organism;

import java.util.Set;

import eu.janinko.aiforlife.World.World;

public interface OrganismManager {

	Organism buildOrganism(World w);
	
	void saveBestOrganism(Set<Organism> organisms);
}
