package eu.janinko.aiforlife.World;

import java.util.EnumSet;

import eu.janinko.aiforlife.Organism.Organism;

public interface SensableWorld {
	
	public enum SenseStyle{
		AHEAD,
		INFRONT,
		NEAR
	}
	
	public class UnsupportedSenseException extends Exception{
		private static final long serialVersionUID = 7335113944190177609L;

		public UnsupportedSenseException(){
			super();
		}
	}

	EnumSet<SenseStyle> getSenseStyle();

	WorldObject senseAhead(Organism o) throws UnsupportedSenseException;
	WorldObject[] senseInFront(Organism o) throws UnsupportedSenseException;
	WorldObject[] senseNear(Organism o) throws UnsupportedSenseException;
	

}
