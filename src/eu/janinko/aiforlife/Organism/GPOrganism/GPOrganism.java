package eu.janinko.aiforlife.Organism.GPOrganism;

import java.util.Random;
import java.util.Set;

import eu.janinko.aiforlife.Organism.AttackingOrganism;
import eu.janinko.aiforlife.Organism.MattingOrganism;
import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.World.WorldObject;
import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrain;
import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainCatalog;

public class GPOrganism implements Organism, AttackingOrganism, MattingOrganism{
	private World world;
	private boolean alive = true;
	
	private GPSimpleBrain brain;
	
	private int score = 0;
	private int attacked = 0;
	private int matted = 0;
	private int hits = 100;
	private double agressivity;
	private Random generator = new Random();
	
	
	public GPOrganism(GPSimpleBrainCatalog catalog, World w){
		brain = new GPSimpleBrain(this, catalog);
		agressivity = generator.nextDouble() - 0.5;
		world = w;
	}

	@Override
	public int color(int color) {
		switch(color){
		case 0:
			return (attacked > 10 ? 255 : 25 * attacked);
		case 1:
			return (matted > 10 ? 255 : 25 * matted);
		case 2:
			return (Math.abs(attacked - matted) > 25 ? 0 : 255 - Math.abs(attacked - matted) * 10);
		}
		return 0;
	}

	@Override
	public void damage(int damage) {
		hits -= damage;
		this.onDamage(damage);
		if(hits < 0){
			this.die();
		}
	}

	@Override
	public void die() {
		this.alive = false;
		world.onDie(this);
	}

	@Override
	public void gotoNextState() {
		// TODO
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public void onCollision(Set<Organism> organisms) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDamage(int damage) {
		score -= damage;
	}

	@Override
	public void prepareNextState() {
		brain.next();
	}

	@Override
	public boolean wantBreed() {
		return generator .nextDouble() < agressivity;
	}

	@Override
	public void attack(WorldObject wo) {
		wo.attacked(this, 10);
		this.attacked++;
		if(wo.isOrganism() && !wo.getOrganism().isAlive()){
			this.score += 50;
			this.hits++;
		}
	}

	@Override
	public void mate(Organism o) {
		if(o.wantBreed()){
			Organism[] wb = new Organism[2];
			wb[0] = this;
			wb[1] = o;
			world.breed(wb);
			this.matted++;
			this.score += 20;
		}
	}

	@Override
	public void damage(int damage, Organism o) {
		this.damage(damage);
		if(generator.nextDouble() < (-1 * agressivity)){
			o.damage(damage/2);
		}
	}

	@Override
	public void gainScore(int i) {
		this.score += i;
	}

	@Override
	public String toString() {
		return "GPOrganism " + generator.hashCode() + " [attacked="
				+ attacked + ", matted=" + matted + ", score=" + score + "]";
	}

	@Override
	public int getScore() {
		return score;
	}

	
}
