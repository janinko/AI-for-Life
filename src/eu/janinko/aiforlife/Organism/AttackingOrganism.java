package eu.janinko.aiforlife.Organism;

import eu.janinko.aiforlife.World.WorldObject;

public interface AttackingOrganism extends Organism {

	void attack(WorldObject wo);
}
