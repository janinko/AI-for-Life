package eu.janinko.aiforlife.Organism.DullOrganism;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import eu.janinko.aiforlife.Effector.Effector;
import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.Sensor.Sensor;
import eu.janinko.aiforlife.World.MovableWorld;
import eu.janinko.aiforlife.World.SensableWorld;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.World.WorldObject;
import eu.janinko.aiforlife.World.MovableWorld.MoveStyle;
import eu.janinko.aiforlife.World.SensableWorld.SenseStyle;
import eu.janinko.aiforlife.World.SensableWorld.UnsupportedSenseException;

public class DullOrganism implements Organism {
	World world;
	boolean alive;

	int hits;
	
	int nextHits;
	
	GeneticInformation dna;
	int statepointer;
	
	int name;
	Random generator;
	
	
	

	DullOrganism(World world, int hits) {
		this(world, hits, null);
		dna = new GeneticInformation(3);
	}

	DullOrganism(World world, int hits, GeneticInformation dna) {
		this.world = world;
		this.hits = hits;
		this.dna = dna;
		
		this.alive = true;
		this.statepointer = 1;

		generator = new Random();
		this.name = generator.nextInt(10000);
	}

	@Override
	public void damage(int damage) {
		this.nextHits -= damage;
	}

	@Override
	public void die() {
		this.alive = false;
		this.hits = 0;
		world.onDie(this);
	}

	@Override
	public Collection<Effector> getEffectors() {
		return null;
	}

	@Override
	public Collection<Sensor> getSensors() {
		return new HashSet<Sensor>();
	}

	@Override
	public void gotoNextState() {
		if(nextHits <= 0){
			this.die();
			return;
		}
		this.hits = this.nextHits;
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public void prepareNextState() {
		if(statepointer + 1 >= this.dna.getLength()){
			statepointer = 0;
		}


		if(world instanceof SensableWorld){
			SensableWorld sw = (SensableWorld) world;
			if(sw.getSenseStyle().contains(SenseStyle.AHEAD)){
				double gi = dna.getGenInfo(statepointer, 0);
				WorldObject wo;
				try {
					wo = sw.senseAhead(this);
					if( !((gi < -1 && wo == null) ||
						  (gi > 1 && wo != null))){
						this.move();
					}
				} catch (UnsupportedSenseException e) {
					e.printStackTrace();
				}
			}
		}else{
			this.move();
		}

		this.nextHits = this.hits;
	}

	private void move() {
		if(!(world instanceof MovableWorld)) return;
		MovableWorld mw = (MovableWorld) world;
		
		if(mw.getMoveStyle().contains(MoveStyle.FORWARD) && mw.getMoveStyle().contains(MoveStyle.ROTATE)){
			mw.moveForward(this, dna.getGenInfo(statepointer, 1));
			mw.rotate(this, dna.getGenInfo(statepointer, 2), 0, 0);
		}else if(mw.getMoveStyle().contains(MoveStyle.FREE)){
			mw.moveFree(this, dna.getGenInfo(statepointer, 1), dna.getGenInfo(statepointer, 2), 0);
		}
	}

	public GeneticInformation getGeneticCode() {
		return dna;
	}

	public void setGeneticCode(GeneticInformation geneticCode) throws InvalidGeneticCodeException {
		this.dna = geneticCode;
	}
	
	public String toString(){
		return this.name + ":" + this.hits;
	}

	@Override
	public boolean wantBreed() {
		return generator.nextDouble() > this.dna.getBreedery();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DullOrganism other = (DullOrganism) obj;
		if (name != other.name)
			return false;
		return true;
	}


	
}
