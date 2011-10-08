package eu.janinko.aiforlife.World.FlatWorld;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import eu.janinko.aiforlife.BreedManager.BreedManager;
import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.Organism.OrganismManager;
import eu.janinko.aiforlife.World.MovableWorld;
import eu.janinko.aiforlife.World.OrganismWorldObject;
import eu.janinko.aiforlife.World.SensableWorld;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.World.WorldObject;

public abstract class AbstractFlatWorld implements World, MovableWorld, SensableWorld {
	protected OrganismManager organismManager;
	protected BreedManager breedManager;
	
	protected OrganismsInWorld organisms;
	protected int sizeX;
	protected int sizeY;
	
	AbstractFlatWorld(int sizeX, int sizeY){
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		organisms = new OrganismsInWorld();
	}

	@Override
	public void breed(Organism[] wb) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void setOrganismManager(OrganismManager om) {
		this.organismManager = om;
	}

	@Override
	public OrganismManager getOrganismManager() {
		return organismManager;
	}
	
	@Override
	public void setBreedManager(BreedManager bm) {
		this.breedManager = bm;
	}
	
	@Override
	public BreedManager getBreedManager() {
		return breedManager;
	}

	@Override
	public Collection<Organism> getOrganisms() {
		return organisms.getOrganisms();
	}

	@Override
	public void killAll() {
		for(Organism o : organisms.getOrganisms()){
			o.die();
		}
		organisms.clear();
	}

	@Override
	public void onDie(Organism o) {
		organisms.remove(o);
	}

	@Override
	public EnumSet<MoveStyle> getMoveStyle() {
		return EnumSet.of(MoveStyle.FORWARD, MoveStyle.FREE, MoveStyle.ROTATE);
	}

	@Override
	public void moveDirection(Organism o, double f, double s, double v)
			throws UnsupportedMoveException {
		throw new UnsupportedMoveException();
	}

	@Override
	public void moveForward(Organism o, double f)
			throws UnsupportedMoveException {
		if(!organisms.contains(o)) return;
		
		Position p = organisms.getCopyOfPosition(o);
		p.moveForward((int) Math.round(f));
		
		organisms.move(o, p);
	}

	@Override
	public void moveFree(Organism o, double x, double y, double z)
			throws UnsupportedMoveException {
		if(!organisms.contains(o)) return;
		
		Position p = organisms.getCopyOfPosition(o);
		p.move((int) Math.round(x),(int) Math.round(y));
		
		organisms.move(o, p);
	}

	@Override
	public void rotate(Organism o, double x, double y, double z)
			throws UnsupportedMoveException {
		if(!organisms.contains(o)) return;
		
		Position p = organisms.getPosition(o);
		p.rotate((int) Math.round(x));
	}
	
	@Override
	public EnumSet<SenseStyle> getSenseStyle() {
		return EnumSet.of(SenseStyle.AHEAD);
	}
	
	@Override
	public WorldObject[] senseInFront(Organism o) throws UnsupportedSenseException {
		throw new UnsupportedSenseException();
	}

	@Override
	public WorldObject[] senseNear(Organism o) throws UnsupportedSenseException {
		throw new UnsupportedSenseException();
	}
	
	@Override
	public WorldObject senseAhead(Organism o) throws UnsupportedSenseException {
		Position pos = organisms.getCopyOfPosition(o);
		Set<Organism> orgs = organisms.getOrganisms(pos);
		
		pos.moveForward(1);
		if(orgs.isEmpty()) return null;
		return new OrganismWorldObject((Organism) orgs.toArray()[0]);
	}

	
	
	public int getSizeX() {
		return sizeX;
	}
	
	public int getSizeY() {
		return sizeY;
	}

}
