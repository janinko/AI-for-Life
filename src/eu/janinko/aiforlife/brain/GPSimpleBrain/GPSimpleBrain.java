package eu.janinko.aiforlife.brain.GPSimpleBrain;

import eu.janinko.aiforlife.Organism.Organism;

public class GPSimpleBrain {
	GPSimpleBrainTree brain;

	private GPSimpleBrainCatalog catalog;
	private Organism organism;
	
	public GPSimpleBrain(Organism o, GPSimpleBrainCatalog catalog){
		brain = new GPSimpleBrainTree(o, catalog);
		this.catalog = catalog;
		organism = o;
	}


	public void next() {
		try{
			brain.execute();
		}catch(StackOverflowError e){
			this.brain = new GPSimpleBrainTree(organism, catalog);
			System.err.println("StackOverflowError: throwing brain away");
			this.next();
		}
	}

}
