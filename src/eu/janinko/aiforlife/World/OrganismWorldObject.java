package eu.janinko.aiforlife.World;

import eu.janinko.aiforlife.Organism.Organism;

public class OrganismWorldObject implements WorldObject {
	Organism o;
	
	public OrganismWorldObject(Organism o){
		this.o = o;
	}
	

	@Override
	public Organism getOrganism() {
		return this.o;
	}

	@Override
	public boolean isOrganism() {
		return true;
	}

}
