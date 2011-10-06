package eu.janinko.aiforlife.Organism.PredatorAndPray;

import java.security.InvalidParameterException;
import java.util.Random;
import java.util.Set;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.Organism.DullOrganism.GeneticInformation;
import eu.janinko.aiforlife.World.MovableWorld;
import eu.janinko.aiforlife.World.SensableWorld;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.World.WorldObject;
import eu.janinko.aiforlife.World.MovableWorld.MoveStyle;
import eu.janinko.aiforlife.World.SensableWorld.SenseStyle;
import eu.janinko.aiforlife.World.SensableWorld.UnsupportedSenseException;

public class Pray implements Organism {
	private boolean alive;
	
	protected GeneticInformation dna;
	protected int statepointer;
	
	protected World world;
	
	private int r;
	private int g;
	private int b;
	
	Random generator = new Random();
	
	public Pray(World world){
		this(world,null);
		dna = new GeneticInformation(5);
	}

	public Pray(World world, GeneticInformation geneticCode) {
		if(!(world instanceof MovableWorld))
			throw new InvalidParameterException("The world mus be MovableWorld");
		
		alive = true;
		statepointer = 0;
		this.world = world;

		dna = geneticCode;
		
		r = generator.nextInt(80);
		g = generator.nextInt(30)+226;
		b = generator.nextInt(80);
	}

	@Override
	public void damage(int damage) {
		die();
	}

	@Override
	public void die() {
		alive = false;
		world.onDie(this);
	}

	@Override
	public void gotoNextState() {
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public void onCollision(Set<Organism> organisms) {
		for(Organism o : organisms){
			if(o instanceof Pray){
				if(!o.isAlive()) continue;
				
				Pray pray = (Pray) o;
				
				if(pray.wantBreed() && this.wantBreed()){
					Organism[] wb = new Organism[2];
					wb[0] = this;
					wb[1] = pray;
					this.world.breed(wb);
				}
			}
		}
	}

	@Override
	public void onDamage(int damage) {
		die();
	}

	@Override
	public void prepareNextState() {
		if(statepointer + 1 >= this.dna.getLength()){
			statepointer = 0;
		}
			
		if(isMoving()){
			this.move();
		}

	}
	
	private boolean isMoving(){
		if(!(world instanceof SensableWorld)) return true;
		
		SensableWorld sw = (SensableWorld) world;

		double gi = dna.getGenInfo(statepointer, 0);
		
		if(sw.getSenseStyle().contains(SenseStyle.AHEAD)){
			WorldObject wo;
			try {
				wo = sw.senseAhead(this);
			} catch (UnsupportedSenseException e) {
				e.printStackTrace();
				return true;
			}

			if(wo != null && wo.isOrganism()){
				Organism o = wo.getOrganism();
				if( o instanceof Predator ){
					return gi > 1;
				}
				if( o instanceof Pray ){
					return gi > -1;
				}
			}
		}
		return true;
	}

	private void move() {
		MovableWorld mw = (MovableWorld) world;
		
		if(mw.getMoveStyle().contains(MoveStyle.FORWARD) && mw.getMoveStyle().contains(MoveStyle.ROTATE)){
			mw.moveForward(this, dna.getGenInfo(statepointer, 1));
			mw.rotate(this, dna.getGenInfo(statepointer, 2), 0, 0);
		}else if(mw.getMoveStyle().contains(MoveStyle.FREE)){
			mw.moveFree(this, dna.getGenInfo(statepointer, 1), dna.getGenInfo(statepointer, 2), 0);
		}
	}

	@Override
	public boolean wantBreed() {
		return generator.nextDouble() > dna.getBreedery();
	}

	@Override
	public int color(int color) {
		if(color == 0) return r;
		if(color == 1) return g;
		if(color == 2) return b;
		return 0;
	}
	
	public GeneticInformation getDNA(){
		return this.dna;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + b;
		result = prime * result + g;
		result = prime * result
				+ ((generator == null) ? 0 : generator.hashCode());
		result = prime * result + r;
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
		Pray other = (Pray) obj;
		if (b != other.b)
			return false;
		if (g != other.g)
			return false;
		if (generator == null) {
			if (other.generator != null)
				return false;
		} else if (!generator.equals(other.generator))
			return false;
		if (r != other.r)
			return false;
		return true;
	}

}
