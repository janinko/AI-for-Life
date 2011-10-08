package eu.janinko.aiforlife.brain.GPSimpleBrain;

import java.security.InvalidParameterException;

import eu.janinko.aiforlife.Organism.AttackingOrganism;
import eu.janinko.aiforlife.Organism.MattingOrganism;
import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.World.World;

public class GPSimpleBrainCatalog {
	GPSimpleBrainFunctions functions;
	
	public GPSimpleBrainCatalog(World w){
		functions = new GPSimpleBrainFunctions(w);
	}
	
	public int getBooleanFunctions(){
		return 1;
	}
	public boolean doBooleanFunction(int i, Organism o){
		if(i < 0 || i >= getBooleanFunctions())
			throw new InvalidParameterException("Wrong function number, got " + i + " excepted <0," + getBooleanFunctions() + ")");
		
		switch(i){
		case 0: o.gainScore(-1); return functions.ScanOrganism(o);
		}
		
		throw new RuntimeException("We should never got here!");
	}
	
	public int getDoubleFunctions(){
		return 3;
	}
	public double doDoubleFunction(int i, Organism o){
		if(i < 0 || i >= getDoubleFunctions())
			throw new InvalidParameterException("Wrong function number, got " + i + " excepted <0," + getDoubleFunctions() + ")");
		
		switch(i){
		case 0: o.gainScore(-1); return functions.ScanRed(o);
		case 1: o.gainScore(-1); return functions.ScanGreen(o);
		case 2: o.gainScore(-1); return functions.ScanBlue(o);
		}
		
		throw new RuntimeException("We should never got here!");
	}

	public int getVoidFunctions(){
		return 6;
	}
	public void doVoidFunction(int i, Organism o){
		if(i < 0 || i >= getVoidFunctions())
			throw new InvalidParameterException("Wrong function number, got " + i + " excepted <0," + getVoidFunctions() + ")");
		
		switch(i){
		case 0: o.gainScore(-5); functions.Attack((AttackingOrganism) o); return;
		case 1: o.gainScore(-5); functions.Mate((MattingOrganism) o); return;
		case 2: o.gainScore(-1); functions.MoveForward(o); return;
		case 3: o.gainScore(-1); functions.MoveBackward(o); return;
		case 4: o.gainScore(-1); functions.RotateLeft(o); return;
		case 5: o.gainScore(-1); functions.RotateRight(o); return;
		case 6: o.gainScore(0); functions.nope(o); return;
		}
		
		throw new RuntimeException("We should never got here!");
	}
}
