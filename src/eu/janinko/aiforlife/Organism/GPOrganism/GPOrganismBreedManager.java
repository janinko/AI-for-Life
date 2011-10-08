package eu.janinko.aiforlife.Organism.GPOrganism;

import eu.janinko.aiforlife.BreedManager.BreedManager;
import eu.janinko.aiforlife.BreedManager.UnsupportedOrganismException;
import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.World.World;

public class GPOrganismBreedManager implements BreedManager {
	
	World w;
	
	public GPOrganismBreedManager(World w){
		this.w = w;
	}

	@Override
	public Organism breed(Organism[] parents)
			throws UnsupportedOrganismException {
		return w.getOrganismManager().buildOrganism(w);
	}

	@Override
	public void maybeMutate(Organism o2) throws UnsupportedOrganismException {
		// TODO Auto-generated method stub

	}

	@Override
	public void maybeMutate(Organism o, double p)
			throws UnsupportedOrganismException {
		// TODO Auto-generated method stub

	}

	@Override
	public Organism mutate(Organism individual)
			throws UnsupportedOrganismException {
		return individual;
	}

}
