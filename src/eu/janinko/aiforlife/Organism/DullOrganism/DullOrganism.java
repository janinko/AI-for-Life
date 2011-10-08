package eu.janinko.aiforlife.Organism.DullOrganism;

import java.util.Random;
import java.util.Set;

import eu.janinko.aiforlife.BreedManager.UnsupportedOrganismException;
import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.World.MovableWorld;
import eu.janinko.aiforlife.World.SensableWorld;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.World.WorldObject;
import eu.janinko.aiforlife.World.MovableWorld.MoveStyle;
import eu.janinko.aiforlife.World.SensableWorld.SenseStyle;
import eu.janinko.aiforlife.World.SensableWorld.UnsupportedSenseException;
import eu.janinko.aiforlife.brain.DullGeneticInformation;

public class DullOrganism implements Organism {
	World world;
	boolean alive;

	int hits;
	
	int nextHits;
	
	DullGeneticInformation dna;
	int statepointer;
	
	int name;
	Random generator;
	
	private int r;
	private int g;
	private int b;
	

	DullOrganism(World world, int hits) {
		this(world, hits, null);
		dna = new DullGeneticInformation(3);
	}

	DullOrganism(World world, int hits, DullGeneticInformation dna) {
		this.world = world;
		this.hits = hits;
		this.dna = dna;
		
		this.alive = true;
		this.statepointer = 1;

		generator = new Random();
		this.name = generator.nextInt(10000);

		r = generator.nextInt(176)+80;
		g = generator.nextInt(176)+80;
		b = generator.nextInt(176)+80;
	}

	@Override
	public void damage(int damage) {
		this.nextHits -= damage;
	}

	@Override
	public void onDamage(int damage) {
		try {
			this.world.getBreedManager().maybeMutate(this, (hits / damage));
		} catch (UnsupportedOrganismException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void die() {
		this.alive = false;
		this.hits = 0;
		world.onDie(this);
	}

	@Override
	public void gotoNextState() {
		if(nextHits <= 0){
			this.die();
			return;
		}
		if(this.hits > this.nextHits){
			this.onDamage(hits - nextHits);
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

	public DullGeneticInformation getGeneticCode() {
		return dna;
	}

	public void setGeneticCode(DullGeneticInformation geneticCode) throws InvalidGeneticCodeException {
		this.dna = geneticCode;
	}
	
	public String toString(){
		return this.name + ":" + this.hits;
	}

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

	@Override
	public void onCollision(Set<Organism> organisms) {
		if(this.wantBreed()){
			for(Organism o: organisms){
				if(o.wantBreed()){
					Organism[] wb = new Organism[2];
					wb[0] = this;
					wb[1] = o;
					this.world.breed(wb);
				}
			}
		}else{
			for(Organism o: organisms){
				o.damage(15);
			}
		}
	}

	@Override
	public int color(int color) {
		if(color == 0) return r;
		if(color == 1) return g;
		if(color == 2) return b;
		return 0;
	}

	@Override
	public void gainScore(int i) {		
	}


	
}
