package eu.janinko.aiforlife.World.FlatWorld;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import eu.janinko.aiforlife.Organism.Organism;

public class OrganismsInWorld{
	HashMap<Organism, ConstPosition> organisms;
	HashMap<ConstPosition, HashSet<Organism>> posmap;
	
	public OrganismsInWorld(){
		organisms = new HashMap<Organism, ConstPosition>();
		posmap = new HashMap<ConstPosition, HashSet<Organism>>();
	}

	public OrganismsInWorld(OrganismsInWorld oiw) {
		organisms = new HashMap<Organism, ConstPosition>(oiw.organisms);
		posmap = new HashMap<ConstPosition, HashSet<Organism>>(oiw.posmap);
	}
	
	/** Add organism into OrganismInWorld, if OrganismInWorld doesn't contains it yet.
	 * 
	 * @param o Organism to which will be added
	 * @param p Position to where organism will be added
	 */
	public void add(Organism o, Position p){
		if(o == null || p == null) throw new NullPointerException();
		if(organisms.containsKey(o)) return;
		ConstPosition pos = new ConstPosition(p);
		organisms.put(o, pos);
		addToPosmap(o,pos);
	}
	
	public void remove(Organism o){
		if(o == null) throw new NullPointerException();
		if(!organisms.containsKey(o)) return;
		
		removeFromPosmap(o,organisms.remove(o));
	}
	
	public boolean contains(Organism o){
		return organisms.containsKey(o);
	}
	
	public boolean contains(Position p){
		return posmap.containsKey(p);
	}

	public Set<Organism> getOrganisms(){
		return Collections.unmodifiableSet(organisms.keySet());
	}
	public Set<Organism> getCopyOfOrganisms(){
		return new HashSet<Organism>(organisms.keySet());
	}

	public final Position getPosition(Organism o){
		return organisms.get(o);
	}
	public final Position getCopyOfPosition(Organism o){
		return new Position(organisms.get(o));
	}

	public Set<ConstPosition> getPositions(){
		return Collections.unmodifiableSet(posmap.keySet());
	}
	public Set<Position> getCopyOfPositions(){
		return new HashSet<Position>(posmap.keySet());
	}
	
	public Set<Organism> getOrganisms(Position p){
		if(!posmap.containsKey(p)){
			return new HashSet<Organism>();
		}else{
			return Collections.unmodifiableSet(posmap.get(p));
		}
	}public Set<Organism> getCopyOfOrganisms(Position p){
		if(!posmap.containsKey(p)){
			return new HashSet<Organism>();
		}else{
			return new HashSet<Organism>(posmap.get(p));
		}
	}
	
	public Collection<HashSet<Organism>> getOrganismChunks(){
		return Collections.unmodifiableCollection(posmap.values());
	}
	
	public void clear(){
		organisms.clear();
		posmap.clear();
	}
	
	public void move(Organism o, Position npos){
		if(o == null || npos == null) throw new NullPointerException();
		if(!organisms.containsKey(o)) return;
		
		ConstPosition opos = organisms.get(o);
		if(opos.equals(npos)) return;
		
		
		ConstPosition p = new ConstPosition(npos);
		
		this.removeFromPosmap(o, opos);
		organisms.put(o, p);
		this.addToPosmap(o, p);
	}

	
	public void addNear(Organism o, Position position) {
		Position pos = new Position(position);
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
		this.add(o, pos);
	}
	
	

	
	private void addToPosmap(Organism o, ConstPosition p) {
		if(!posmap.containsKey(p)){
			posmap.put(p, new HashSet<Organism>());
		}
		posmap.get(p).add(o);
	}

	private void removeFromPosmap(Organism o, ConstPosition p) {
		HashSet<Organism> orgs = posmap.get(p);
		if(orgs.size() == 1){
			posmap.remove(p);
		}else{
			orgs.remove(o);
		}
	}
}