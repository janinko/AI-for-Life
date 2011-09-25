package eu.janinko.aiforlife.Organism.DullOrganism;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.World.FlatWorld.Position;

public class OrganismsInWorld{
	HashMap<Position, HashSet<Organism>> posmap;
	HashMap<Organism, Position> organisms;
	
	public OrganismsInWorld(){
		posmap = new HashMap<Position, HashSet<Organism>>();
		organisms = new HashMap<Organism, Position>();
	}

	public boolean contains(Position pos) {
		return posmap.containsKey(pos);
	}
	
	public boolean contains(Organism o) {
		return organisms.containsKey(o);
	}

	public OrganismsInWorld(OrganismsInWorld oiw) {
		posmap = new HashMap<Position, HashSet<Organism>>();
		organisms = new HashMap<Organism, Position>();
		
		Iterator<Entry<Organism, Position>> it = oiw.iterator();
		while(it.hasNext()){
			Entry<Organism, Position> e = it.next();
			this.add(e.getKey(), e.getValue());
		}
	}

	public void clear() {
		posmap.clear();
		organisms.clear();
	}

	public void add(Organism o, Position pos){
		if(organisms.containsKey(o)) return;
		organisms.put(o, pos);
		putOrganismIntoPosmap(o, pos);
	}
	
	public void remove(Organism o){
		Position pos = organisms.remove(o);
		System.out.println("Removing organism " + o + " from " + pos);
		removeOrganismFromPosmap(o,pos);
	}
	
	public Position getPosition(Organism o){
		return organisms.get(o);
	}
	
	public void move(Organism o, Position newPos){
		System.out.println("Moving organism " + o + " from " + organisms.get(o) + " to " + newPos);
		removeOrganismFromPosmap(o,organisms.get(o));
		putOrganismIntoPosmap(o,newPos);
		organisms.put(o, newPos);
	}
	
	private void removeOrganismFromPosmap(Organism o, Position pos){
		if(posmap.get(pos) == null){
			throw new NullPointerException();
		}
		if(posmap.get(pos).size() <= 1){
			posmap.remove(pos);
		}else{
			posmap.get(pos).remove(o);
		}
	}
	
	private void putOrganismIntoPosmap(Organism o, Position pos){
		if(!posmap.containsKey(pos)){
			posmap.put(pos, new HashSet<Organism>());
		}
		posmap.get(pos).add(o);
	}
	
	public Set<Organism> getOrganisms(){
		return organisms.keySet();
	}

	public HashSet<Organism> getOrganisms(Position pos){
		if(!posmap.containsKey(pos)){
			return new HashSet<Organism>();
		}
		return posmap.get(pos);
	}
	
	public Set<Position> getPositions(){
		return posmap.keySet();
	}
	
	public Iterator<Entry<Organism, Position>> iterator(){
		return organisms.entrySet().iterator();
	}

	public void addNear(Organism o, Position position) {
		Position pos = position;
		int i = 0;
		int p = 0;
		int c = 1;
		while(posmap.containsKey(pos)){
			if(p<c){
				pos.moveForward(1);
				p++;
			}else{
				pos.rotate(1);
				pos.moveForward(1);
				p = 1;
				i++;
			}
			if(i >= 2){
				c++;
				i = 0;
			}
		}
		this.organisms.put(o, pos);
		this.putOrganismIntoPosmap(o, pos);
	}
}