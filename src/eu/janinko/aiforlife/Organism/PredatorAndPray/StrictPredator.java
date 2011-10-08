package eu.janinko.aiforlife.Organism.PredatorAndPray;

import java.security.InvalidParameterException;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.Organism.DullOrganism.GeneticInformation;
import eu.janinko.aiforlife.World.MovableWorld;
import eu.janinko.aiforlife.World.SensableWorld;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.World.WorldObject;
import eu.janinko.aiforlife.World.SensableWorld.UnsupportedSenseException;

public class StrictPredator extends Predator {

	public StrictPredator(World world, GeneticInformation geneticCode) {
		super(world, geneticCode);
		if(!(world instanceof SensableWorld))
			throw new InvalidParameterException("World must be SensableWorld");
	}
	
	public StrictPredator(World world) {
		super(world);
		if(!(world instanceof SensableWorld))
			throw new InvalidParameterException("World must be SensableWorld");
	}
	
	public StrictPredator(StrictPredator o) {
		super(o);
	}

	@Override
	public void prepareNextState(){
		if(statepointer + 1 >= this.dna.getLength()){
			statepointer = 0;
		}
		nextScore = score;

		SensableWorld w = (SensableWorld) world;
		MovableWorld mw = (MovableWorld) world;

		WorldObject wo;
		try {
			wo = w.senseAhead(this);
		} catch (UnsupportedSenseException e) {
			e.printStackTrace();
			return;
		}
		if(wo == null){
			mw.moveForward(this, this.dna.getGenInfo(statepointer, 1));
		}else if(wo.isOrganism()){
			Organism o = wo.getOrganism();
			if(o instanceof Pray){
				mw.moveForward(this, 1);
			}else{
				if(this.dna.getGenInfo(statepointer, 0) > 0){
					mw.moveForward(this, 1);
				}
			}
		}
		mw.rotate(this, this.dna.getGenInfo(statepointer, 2), 0, 0);
	}
	
	

}
