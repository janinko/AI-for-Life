package eu.janinko.aiforlife.BreedManager;

import eu.janinko.aiforlife.Organism.Organism;

public interface BreedManager {

	/* Breed parents together and produce a child.
	 */
	Organism breed(Organism[] parents) throws UnsupportedOrganismException;
	
	/* Mutate an individual and produce mutated organism.
	 */
	Organism mutate(Organism individual) throws UnsupportedOrganismException;

	void maybeMutate(Organism o2) throws UnsupportedOrganismException;

	void maybeMutate(Organism o, double p) throws UnsupportedOrganismException;
}
