package eu.janinko.aiforlife.Organism.PredatorAndPray;

import java.security.InvalidParameterException;
import java.util.Random;
import java.util.Set;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.World.MovableWorld;
import eu.janinko.aiforlife.World.SensableWorld;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.World.WorldObject;
import eu.janinko.aiforlife.World.MovableWorld.MoveStyle;
import eu.janinko.aiforlife.World.SensableWorld.SenseStyle;
import eu.janinko.aiforlife.World.SensableWorld.UnsupportedSenseException;
import eu.janinko.aiforlife.brain.DullGeneticInformation;

public class Pray implements Organism {
	private boolean alive;
	private boolean willDie;
	private int age;
	
	protected DullGeneticInformation dna;
	protected int statepointer;
	
	protected World world;
	
	private int r;
	private int g;
	private int b;
	
	private static final int durability = 100;
	
	Random generator = new Random();
	
	public Pray(World world){
		this(world,null);
		dna = new DullGeneticInformation(5);
	}

	public Pray(World world, DullGeneticInformation geneticCode) {
		if(!(world instanceof MovableWorld))
			throw new InvalidParameterException("The world mus be MovableWorld");
		
		alive = true;
		willDie = false;
		statepointer = 0;
		this.world = world;

		dna = geneticCode;
		age = 0;
		
		r = generator.nextInt(80);
		g = generator.nextInt(30)+226;
		b = generator.nextInt(80);
	}

	@Override
	public void damage(int damage) {
		willDie = true;
	}

	@Override
	public void die() {
		alive = false;
		world.onDie(this);
	}

	@Override
	public void gotoNextState() {
		age++;
		if(willDie || age > durability){
			this.die();
			return;
		}
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public void onCollision(Set<Organism> organisms) {
		for(Organism o : organisms){
			if(!o.isAlive()) continue;
			
			if(o instanceof Pray){
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
	
	public DullGeneticInformation getDNA(){
		return this.dna;
	}

	private int hashcode=-1;
	@Override
	public int hashCode() {
		if(hashcode == -1){
			genHashCode();
		}
		return hashcode;
	}
	
	private void genHashCode(){
		final int prime = 31;
		hashcode = 1;
		hashcode = prime * hashcode + b;
		hashcode = prime * hashcode + g;
		hashcode = prime * hashcode
				+ ((generator == null) ? 0 : generator.hashCode());
		hashcode = prime * hashcode + r;
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

	@Override
	public String toString() {
		return "Pray [age=" + age + ", hashCode()=" + hashCode() + "]";
	}

	@Override
	public void gainScore(int i) {
	}

	@Override
	public int getScore() {
		return 0;
	}
}
