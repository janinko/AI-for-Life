package eu.janinko.aiforlife.gui;

import javax.swing.SwingUtilities;

import eu.janinko.aiforlife.BreedManager.DullBreedManager;
import eu.janinko.aiforlife.BreedManager.PredatorAndPrayBreedManager;
import eu.janinko.aiforlife.Organism.DullOrganism.DullOrganismManager;
import eu.janinko.aiforlife.Organism.GPOrganism.GPOrganismBreedManager;
import eu.janinko.aiforlife.Organism.GPOrganism.GPOrganismManager;
import eu.janinko.aiforlife.Organism.PredatorAndPray.PredatorAndPrayOrganismManager;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.World.FlatWorld.DrawableFlatWorld;
import eu.janinko.aiforlife.World.FlatWorld.FlatWorld;
import eu.janinko.aiforlife.World.FlatWorld.NoninteractFlatWorld;

public class TestMain {
	
	public static void main(String[] args){
		

		
		SwingUtilities.invokeLater(new MainWindowRunnable(GPTest()));

	}

	@SuppressWarnings("unused")
	private static World dullTest(){
		FlatWorld world = new FlatWorld(30,30);
		DullOrganismManager organismManager = new DullOrganismManager();
		DullBreedManager breedManager = new DullBreedManager(world);
		world.setOrganismManager(organismManager);
		world.setBreedManager(breedManager);
		
		organismManager.setStartLive(60);
		
		world.generate(120);
		
		return world;
	}

	private static World GPTest(){
		NoninteractFlatWorld world = new NoninteractFlatWorld(30,30);
		GPOrganismManager organismManager = new GPOrganismManager(world);
		GPOrganismBreedManager breedManager = new GPOrganismBreedManager(world);
		world.setOrganismManager(organismManager);
		world.setBreedManager(breedManager);
				
		world.generate(125);
		
		return world;
	}

	@SuppressWarnings("unused")
	private static World predatorAndPrayTest(){
		FlatWorld world = new FlatWorld(30,30);
		PredatorAndPrayOrganismManager organismManager = new PredatorAndPrayOrganismManager();
		PredatorAndPrayBreedManager breedManager = new PredatorAndPrayBreedManager(world);
		world.setOrganismManager(organismManager);
		world.setBreedManager(breedManager);
		
		organismManager.setPrayProbability(0.70);
		organismManager.setUseStrictPray(true);
		organismManager.setUseStrictPredator(true);
		
		world.generate(120);
		
		return world;
	}

}

class MainWindowRunnable implements Runnable{
	World world;
	
	MainWindowRunnable(World world){
		this.world = world;
	}
	
	public void run() {
    	MainWindow window = new MainWindow(world);
    	window.setVisible(true);
   }
}
