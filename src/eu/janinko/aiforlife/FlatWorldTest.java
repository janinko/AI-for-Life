package eu.janinko.aiforlife;

import eu.janinko.aiforlife.BreedManager.DullBreedManager;
import eu.janinko.aiforlife.Organism.DullOrganism.DullOrganismManager;
import eu.janinko.aiforlife.World.FlatWorld.FlatWorld;

public class FlatWorldTest {
	
	public static void main(String[] args){

		FlatWorld world = new FlatWorld(30,30);
		DullOrganismManager organismManager = new DullOrganismManager();
		DullBreedManager breedManager = new DullBreedManager(world);
		world.setOrganismManager(organismManager);
		world.setBreedManager(breedManager);
		
		organismManager.setStartLive(3);
		
		world.generate(20);
		
		for(int i=0; i<15; i++){
			System.out.println();
			world.nextTick();
		}
	}
}
