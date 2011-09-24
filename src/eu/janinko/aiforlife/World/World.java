package eu.janinko.aiforlife.World;

import java.util.Collection;

import eu.janinko.aiforlife.BreedManager.BreedManager;
import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.Organism.OrganismManager;

public interface World {
	
	/* Kill all organisms.
	 */
	void killAll();
	
	Collection<Organism> getOrganisms();
	
	void generate();
	
	void setOrganismManager(OrganismManager om);
	void setBreedManager(BreedManager bm);
	
	void nextTick();
	
	void onDie(Organism o);

	void onCollision(Organism o1, Organism o2);

	OrganismManager getOrganismManager();
}
