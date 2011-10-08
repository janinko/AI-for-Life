package eu.janinko.aiforlife.brain.GPSimpleBrain;

import java.security.InvalidParameterException;

import eu.janinko.aiforlife.Organism.AttackingOrganism;
import eu.janinko.aiforlife.Organism.MattingOrganism;
import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.World.MovableWorld;
import eu.janinko.aiforlife.World.SensableWorld;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.World.WorldObject;
import eu.janinko.aiforlife.World.MovableWorld.MoveStyle;
import eu.janinko.aiforlife.World.SensableWorld.SenseStyle;
import eu.janinko.aiforlife.World.SensableWorld.UnsupportedSenseException;


public class GPSimpleBrainFunctions {
	private World world;
	private SensableWorld sworld;
	private MovableWorld mworld;
	
	public GPSimpleBrainFunctions(World w){
		if(!(w instanceof MovableWorld))
			throw new InvalidParameterException("Excepted MovableWorld world");
		if(!(w instanceof SensableWorld))
			throw new InvalidParameterException("Excepted SensableWorld world");
		
		world = w;
		sworld = (SensableWorld) w;
		mworld = (MovableWorld) w;

		if(!sworld.getSenseStyle().contains(SenseStyle.AHEAD))
			throw new InvalidParameterException("Excepted SensableWorld world with SenseStyle.AHEAD");
		
		if(!mworld.getMoveStyle().contains(MoveStyle.FORWARD))
			throw new InvalidParameterException("Excepted MovableWorld world with MoveStyle.FORWARD");
		
		if(!mworld.getMoveStyle().contains(MoveStyle.ROTATE))
			throw new InvalidParameterException("Excepted MovableWorld world with MoveStyle.ROTATE");
	}
	
	
	public boolean ScanOrganism(Organism o){
		WorldObject wo;
		try {
			wo = sworld.senseAhead(o);
		} catch (UnsupportedSenseException e) {
			throw new RuntimeException(e);
		}
		if(wo == null) return false;
		return wo.isOrganism();
	}
	
	public double ScanRed(Organism o){
		WorldObject wo;
		try {
			wo = sworld.senseAhead(o);
		} catch (UnsupportedSenseException e) {
			throw new RuntimeException(e);
		}
		if(wo == null) return 0;
		if(!wo.isOrganism()){
			return 0;
		}
		return wo.getOrganism().color(0) / 255;
	}
	
	public double ScanGreen(Organism o){
		WorldObject wo;
		try {
			wo = sworld.senseAhead(o);
		} catch (UnsupportedSenseException e) {
			throw new RuntimeException(e);
		}
		if(wo == null) return 0;
		if(!wo.isOrganism()){
			return 0;
		}
		return wo.getOrganism().color(1) / 255;
	}
	
	public double ScanBlue(Organism o){
		WorldObject wo;
		try {
			wo = sworld.senseAhead(o);
		} catch (UnsupportedSenseException e) {
			throw new RuntimeException(e);
		}
		if(wo == null) return 0;
		if(!wo.isOrganism()){
			return 0;
		}
		return wo.getOrganism().color(2) / 255;
	}

	public void MoveForward(Organism o){
		mworld.moveForward(o, 1);
	}
	
	public void MoveBackward(Organism o){
		mworld.moveForward(o, -1);
	}

	public void RotateRight(Organism o){
		mworld.rotate(o, 1, 0, 0);
	}
	
	public void RotateLeft(Organism o){
		mworld.rotate(o, -1, 0, 0);
	}
	
	public void nope(Organism o){}
	
	public void Attack(AttackingOrganism o){
		WorldObject wo;
		try {
			wo = sworld.senseAhead(o);
		} catch (UnsupportedSenseException e) {
			throw new RuntimeException(e);
		}
		if(wo == null) return;
		
		o.attack(wo);
	}
	
	public void Mate(MattingOrganism o){
		WorldObject wo;
		try {
			wo = sworld.senseAhead(o);
		} catch (UnsupportedSenseException e) {
			throw new RuntimeException(e);
		}
		if(wo == null) return;
		if(!wo.isOrganism()) return;
		o.mate(wo.getOrganism());
	}
}
