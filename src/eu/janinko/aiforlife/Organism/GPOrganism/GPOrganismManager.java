package eu.janinko.aiforlife.Organism.GPOrganism;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.Organism.OrganismManager;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainCatalog;

public class GPOrganismManager implements OrganismManager {
	World world;
	GPSimpleBrainCatalog catalog;
	
	public GPOrganismManager(World w){
		world = w;
		catalog = new GPSimpleBrainCatalog(w);
	}

	@Override
	public Organism buildOrganism(World w) {
		return new GPOrganism(catalog, w);
	}

}
