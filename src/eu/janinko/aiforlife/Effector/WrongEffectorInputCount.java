package eu.janinko.aiforlife.Effector;

public class WrongEffectorInputCount extends Exception {

	public WrongEffectorInputCount(int excepted, int got) {
		super("Excepted " + excepted + " inputs, got " + got );
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3076763587403261560L;
	
	

}
