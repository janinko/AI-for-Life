package eu.janinko.aiforlife;

import eu.janinko.aiforlife.BreedManager.PredatorAndPrayBreedManager;
import eu.janinko.aiforlife.Organism.PredatorAndPray.PredatorAndPrayOrganismManager;
import eu.janinko.aiforlife.World.FlatWorld.DrawableFlatWorld;
import eu.janinko.aiforlife.World.FlatWorld.FlatWorld;

public class ProfilingTester {
	
	public static void main(String[] args){

		FlatWorld world = new DrawableFlatWorld(30,30);
		PredatorAndPrayOrganismManager organismManager = new PredatorAndPrayOrganismManager();
		PredatorAndPrayBreedManager breedManager = new PredatorAndPrayBreedManager(world);
		world.setOrganismManager(organismManager);
		world.setBreedManager(breedManager);
		
		organismManager.setPrayProbability(0.80);
		organismManager.setUseStrictPray(true);
		organismManager.setUseStrictPredator(true);
		
		world.generate(240);
		
		System.out.println("START");
		for(int i=0; i<700; i++){
			System.out.print('.');
			world.nextTick();
		}
		System.out.println("STOP");
	}
}
