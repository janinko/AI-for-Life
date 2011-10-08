package eu.janinko.aiforlife.World.FlatWorld;

import java.util.Set;

import eu.janinko.aiforlife.Organism.Organism;

public class NoninteractFlatWorld extends DrawableFlatWorld {

	public NoninteractFlatWorld(int sizeX, int sizeY) {
		super(sizeX, sizeY);
	}

	@Override
	public void generate() {
		generate((int) (sizeX * sizeY * 0.4));
	}

	public void generate(int count) {
		if(count > sizeX * sizeY){
			count = sizeX * sizeY;
		}
		for(int i=0; i<count; i++){
			Organism o = organismManager.buildOrganism(this);
			Position p = new Position(sizeX, sizeY);
			organisms.add(o, p);
		}
	}

	@Override
	public void nextTick() {
		for(Organism o : organisms.getCopyOfOrganisms()){
			o.prepareNextState();
		}
	}

	@Override
	public void onCollision(Set<Organism> organisms) {
	}

}
