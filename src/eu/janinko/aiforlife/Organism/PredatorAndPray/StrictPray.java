package eu.janinko.aiforlife.Organism.PredatorAndPray;

import java.security.InvalidParameterException;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.Organism.DullOrganism.GeneticInformation;
import eu.janinko.aiforlife.World.MovableWorld;
import eu.janinko.aiforlife.World.SensableWorld;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.World.WorldObject;
import eu.janinko.aiforlife.World.SensableWorld.UnsupportedSenseException;

public class StrictPray extends Pray {

	public StrictPray(World world) {
		super(world);
		if(!(world instanceof SensableWorld))
			throw new InvalidParameterException("World must be SensableWorld");
	}
	
	public StrictPray(World world, GeneticInformation dna) {
		super(world, dna);
		if(!(world instanceof SensableWorld))
			throw new InvalidParameterException("World must be SensableWorld");
	}
	
	@Override
	public void prepareNextState(){
		if(statepointer + 1 >= this.dna.getLength()){
			statepointer = 0;
		}
			
		SensableWorld w = (SensableWorld) world;
		MovableWorld mw = (MovableWorld) world;
		
		WorldObject wo;
		try {
			wo = w.senseAhead(this);
		} catch (UnsupportedSenseException e) {
			e.printStackTrace();
			return;
		}
		double[] gen = this.dna.getGen(statepointer);
		if(wo == null){
			mw.moveForward(this, gen[1]);
		}else if(wo.isOrganism()){
			Organism o = wo.getOrganism();
			if(o instanceof Pray){
				if(gen[0] > 0){
					mw.moveForward(this, 1);
				}
			}
		}
		mw.rotate(this, gen[2], 0, 0);
	}
	

}
