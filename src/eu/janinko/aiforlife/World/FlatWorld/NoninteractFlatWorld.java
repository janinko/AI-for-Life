package eu.janinko.aiforlife.World.FlatWorld;

import java.util.Set;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.World.MovableWorld.UnsupportedMoveException;

public class NoninteractFlatWorld extends DrawableFlatWorld {
	private int borned = 0;
	private int dead;

	public NoninteractFlatWorld(int sizeX, int sizeY) {
		super(sizeX, sizeY);
	}

	@Override
	public void generate() {
		generate((int) (sizeX * sizeY * 0.4));
	}

	public void generate(int count) {
		if(count > sizeX * sizeY){
			count = sizeX * sizeY;
		}
		for(int i=0; i<count; i++){
			Organism o = organismManager.buildOrganism(this);
			Position p = new Position(sizeX, sizeY);
			organisms.add(o, p);
		}
	}

	@Override
	public void nextTick() {
		int minscore = 10000;
		for(Organism o : organisms.getCopyOfOrganisms()){
			if(!o.isAlive()) continue;
			
			o.prepareNextState();
			if(o.getScore() < minscore){
				minscore = o.getScore();
			}
		}
		int c = 0;
		for(Organism o : organisms.getCopyOfOrganisms()){
			if(this.borned <= this.dead) break;
			if(o.getScore() <= minscore){
				o.die();
				c++;
			}
		}
	}

	@Override
	public void onCollision(Set<Organism> organisms) {
	}
	
	@Override
	public void moveForward(Organism o, double f)
			throws UnsupportedMoveException {
		if(!organisms.contains(o)) return;
		
		Position p = organisms.getCopyOfPosition(o);
		p.moveForward((int) Math.round(f));
		if(!organisms.contains(p)){
			organisms.move(o, p);
		}
	}

	@Override
	public void breed(Organism[] wb){
		super.breed(wb);
		borned++;
	}
	
	@Override
	public void onDie(Organism o){
		super.onDie(o);
		dead++;
	}
	

}
