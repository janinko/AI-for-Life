package eu.janinko.aiforlife.Effector;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.World.MovableWorld;
import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.World.MovableWorld.UnsupportedMoveException;

public class MoveEffector implements Effector {
	private Organism organism;
	private MovableWorld world;
	
	public MoveEffector(Organism organism, World world) {
		this.organism = organism;

		if(!(world instanceof MovableWorld)) throw new IllegalArgumentException("Excepting MovableWorld instance");
		this.world = (MovableWorld) world;
	}

	@Override
	public void effect(double[] in) throws WrongEffectorInputCount {
		if(in.length != 3) throw new WrongEffectorInputCount(3, in.length);
		
		try {
			world.moveFree(organism, in[0], in[1], in[2]);
		} catch (UnsupportedMoveException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
