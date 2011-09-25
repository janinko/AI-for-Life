package eu.janinko.aiforlife.World;

import java.util.Collection;
import java.util.Set;

import eu.janinko.aiforlife.BreedManager.BreedManager;
import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.Organism.OrganismManager;

public interface World {
	
	/* Kill all organisms.
	 */
	void killAll();
	
	Collection<Organism> getOrganisms();
	
	void generate();

	OrganismManager getOrganismManager();
	void setOrganismManager(OrganismManager om);
	BreedManager getBreedManager();
	void setBreedManager(BreedManager bm);
	
	void nextTick();
	
	void onDie(Organism o);

	void onCollision(Set<Organism> organisms);

	void breed(Organism[] wb);


}
