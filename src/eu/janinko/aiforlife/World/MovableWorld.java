package eu.janinko.aiforlife.World;

import java.util.EnumSet;

import eu.janinko.aiforlife.Organism.Organism;

public interface MovableWorld {
	
	public enum MoveStyle{
		FREE,
		FORWARD,
		ROTATE,
		DIRECTION
	}
	
	public class UnsupportedMoveException extends UnsupportedOperationException{
		private static final long serialVersionUID = 7335113944190177609L;

		public UnsupportedMoveException(){
			super();
		}
	}

	EnumSet<MoveStyle> getMoveStyle();

	void moveFree(Organism o, double x, double y, double z) throws UnsupportedMoveException;
	
	void moveForward(Organism o, double f) throws UnsupportedMoveException;

	void rotate(Organism o, double x, double y, double z) throws UnsupportedMoveException;
	
	void moveDirection(Organism o, double f, double s, double v) throws UnsupportedMoveException;
}




