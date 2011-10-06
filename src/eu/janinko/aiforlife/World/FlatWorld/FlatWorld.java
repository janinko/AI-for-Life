package eu.janinko.aiforlife.World.FlatWorld;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import eu.janinko.aiforlife.BreedManager.BreedManager;
import eu.janinko.aiforlife.BreedManager.UnsupportedOrganismException;
import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.Organism.OrganismManager;
import eu.janinko.aiforlife.Organism.DullOrganism.DullOrganism;
import eu.janinko.aiforlife.Organism.DullOrganism.GeneticInformation;
import eu.janinko.aiforlife.Organism.PredatorAndPray.Pray;
import eu.janinko.aiforlife.Organism.PredatorAndPray.Predator;
import eu.janinko.aiforlife.World.MovableWorld;
import eu.janinko.aiforlife.World.OrganismWorldObject;
import eu.janinko.aiforlife.World.SensableWorld;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.World.WorldObject;
import eu.janinko.aiforlife.World.WorldStatistics;

public class FlatWorld implements World, WorldStatistics, MovableWorld, SensableWorld {
	private BreedManager breedManager;
	private OrganismManager organismManager;

	OrganismsInWorld organisms;
	OrganismsInWorld organismsInNextState;
	
	HashMap<Organism, Position> newborns;
	
	int sizeX;
	int sizeY;
	
	private int damaged;
	private int died;
	private int colision;
	private int born;
	
	public FlatWorld(){
		this(10,10);
	}

	public FlatWorld(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		organisms = new OrganismsInWorld();
		organismsInNextState = null;
		newborns = new HashMap<Organism, Position>();
	}

	@Override
	public void generate() {
		this.generate(10);
	}
	
	public void generate(int count){
		if(count > sizeX * sizeY){
			count = sizeX * sizeY;
		}
		for(int i=0; i<count; i++){
			Organism newOrganism = this.organismManager.buildOrganism(this);
			Position pos = new Position(sizeX, sizeY);
			while(organisms.contains(pos)){
				pos = new Position(sizeX, sizeY);
			}
			organisms.add(newOrganism, pos);
		}
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
	public void setBreedManager(BreedManager bm) {
		this.breedManager = bm;
	}

	@Override
	public void setOrganismManager(OrganismManager om) {
		if(om == null) throw new NullPointerException();
		this.organismManager = om;
	}

	@Override
	public void nextTick() {
		damaged = 0;
		died = 0;
		colision = 0;
		born = 0;
		
		this.organismsInNextState = new OrganismsInWorld(organisms);
		for(Organism o : organisms.getOrganisms()){
			o.prepareNextState();
			try {
				this.breedManager.maybeMutate(o);
			} catch (UnsupportedOrganismException e1) {
				e1.printStackTrace();
			}
		}
		
		handleColisions();
		
		this.organisms = this.organismsInNextState;
		this.organismsInNextState = null;
		
		for(Object o : organisms.getOrganisms().toArray()){
			((Organism) o).gotoNextState();
			try {
				breedManager.maybeMutate((Organism) o, 0.1);
			} catch (UnsupportedOrganismException e) {
				e.printStackTrace();
			}
		}
		
		int bornable = (int) ((sizeX * sizeY * 1) - organisms.getOrganisms().size());
		for(Entry<Organism, Position> e : newborns.entrySet()){
			if( born >= bornable){
				break;
			}
			organisms.addNear(e.getKey(), e.getValue());
			born++;
		}
		
		newborns.clear();
	}

	private void handleColisions() {
		boolean solved = true;
		do{
			solved = true;;
			HashSet<Organism> colidedOrganisms = new HashSet<Organism>();
			Set<Position> positions = organismsInNextState.getPositions();
			for(Object obj : positions.toArray()){
				Position pos = (Position) obj;
				HashSet<Organism> orgs = organismsInNextState.getOrganisms(pos);
				if(orgs.size() > 1){
					solved = false;
					colidedOrganisms.addAll(orgs);
					this.onCollision(orgs);
				}
			}
			for(Organism o : colidedOrganisms){
				if(!o.isAlive()) continue;
				Position pos = organisms.getPosition(o);
				if(pos == null){
					pos = new Position(sizeX,sizeY);
				}
				organismsInNextState.move(o, pos);
			}
		}while(!solved);
	}

	@Override
	public void onDie(Organism o) {
		//System.out.println("Organism " + o + " died at " + organisms.getPosition(o));
		organisms.remove(o);
		organismsInNextState.remove(o);
		died++;
	}

	@Override
	public void onCollision(Set<Organism> organisms) {
		colision++;
		
		for(Organism o : organisms){
			HashSet<Organism> orgs = new HashSet<Organism>(organisms);
			orgs.remove(o);
			o.onCollision(orgs);
		}
	}

	@Override
	public OrganismManager getOrganismManager() {
		return this.organismManager;
	}

	public int getSizeX() {
		return sizeX;
	}
	
	public int getSizeY() {
		return sizeY;
	}

	protected Position getOrganismPosition(Organism o) {
		return organisms.getPosition(o);
	}

	@Override
	public int getDamagedCount() {
		return this.damaged;
	}

	@Override
	public int getDiedCount() {
		return this.died;
	}

	@Override
	public int getNewbornCount() {
		return this.born;
	}

	@Override
	public double getProperty(String propertyname) {
		double d = 0;
		if(propertyname.equals("minbreed")){
			d = 1;
			for(Organism o : organisms.getOrganisms()){
				GeneticInformation gi = null;
				if(o instanceof DullOrganism){
					gi = ((DullOrganism)o).getGeneticCode();
				}
				if(o instanceof Pray){
					gi = ((Pray)o).getDNA();
				}
				if(o instanceof Predator){
					gi = ((Predator)o).getDNA();
				}
				if(gi.getBreedery() < d){
					d = gi.getBreedery();
				}
			}
		}else if(propertyname.equals("avgbreed")){
			d = 0;
			for(Organism o : organisms.getOrganisms()){
				GeneticInformation gi = null;
				if(o instanceof DullOrganism){
					gi = ((DullOrganism)o).getGeneticCode();
				}
				if(o instanceof Pray){
					gi = ((Pray)o).getDNA();
				}
				if(o instanceof Predator){
					gi = ((Predator)o).getDNA();
				}
				d += gi.getBreedery();
			}
			d /= organisms.getOrganisms().size();
		}else if(propertyname.equals("maxbreed")){
			d = 0;
			for(Organism o : organisms.getOrganisms()){
				GeneticInformation gi = null;
				if(o instanceof DullOrganism){
					gi = ((DullOrganism)o).getGeneticCode();
				}
				if(o instanceof Pray){
					gi = ((Pray)o).getDNA();
				}
				if(o instanceof Predator){
					gi = ((Predator)o).getDNA();
				}
				if(gi.getBreedery() > d){
					d = gi.getBreedery();
				}
			}
		}else{
			//throw new UnknowParameterException();
		}
		return d;
	}

	@Override
	public EnumSet<MoveStyle> getMoveStyle() {
		return EnumSet.of(MoveStyle.FREE, MoveStyle.FORWARD, MoveStyle.ROTATE);
	}

	@Override
	public void moveDirection(Organism o, double f, double s, double v)
			throws UnsupportedMoveException {
		throw new UnsupportedMoveException();
	}

	@Override
	public void moveForward(Organism o, double f) throws UnsupportedMoveException {
		int m = 0;
		if(f > 0){
			m = 1;
		}

		Position newpos = organismsInNextState.getPosition(o);
		newpos.moveForward(m);

		this.organismsInNextState.move(o, newpos);
	}

	@Override
	public void moveFree(Organism o, double x, double y, double z)
			throws UnsupportedMoveException {
		int movex = 0;
		int movey = 0;

		if(x > 1){
			movex = 1;
		}else if(x < -1){
			movex = -1;
		}

		if(y > 1){
			movey = 1;
		}else if(y < -1){
			movey = -1;
		}

		Position newpos = organismsInNextState.getPosition(o);
		newpos.move(movex, movey);

		this.organismsInNextState.move(o, newpos);
	}

	@Override
	public void rotate(Organism o, double x, double y, double z)
			throws UnsupportedMoveException {
		int r = 0;
		if(x > 1){
			r = 1;
		}
		if(x < -1){
			r = -1;
		}
		
		Position newpos = organismsInNextState.getPosition(o);
		newpos.rotate(r);

		this.organismsInNextState.move(o, newpos);
	}

	@Override
	public EnumSet<SenseStyle> getSenseStyle() {
		return EnumSet.of(SenseStyle.AHEAD);
	}

	@Override
	public WorldObject senseAhead(Organism o) throws UnsupportedSenseException {
		Position pos = organisms.getPosition(o);
		pos.moveForward(1);
		HashSet<Organism> orgs = organisms.getOrganisms(pos);
		if(orgs.isEmpty()) return null;
		return new OrganismWorldObject((Organism) orgs.toArray()[0]);
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
	public BreedManager getBreedManager() {
		return this.breedManager;
	}

	@Override
	public void breed(Organism[] wb) {
		try {
			Organism o = this.breedManager.breed(wb);
			newborns.put(o,organismsInNextState.getPosition(wb[0]));
		} catch (UnsupportedOrganismException e) {
			e.printStackTrace();
		}
		
	}
}



