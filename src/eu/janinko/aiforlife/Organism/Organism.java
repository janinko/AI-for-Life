package eu.janinko.aiforlife.Organism;

import java.util.Collection;
import java.util.Set;

import eu.janinko.aiforlife.Effector.Effector;
import eu.janinko.aiforlife.Sensor.Sensor;

public interface Organism {
	
	/* Make the organism dead.
	 */
	void die();
	
	/* Check if the organism is alive or not.
	 */
	boolean isAlive();
	
	Collection<Sensor> getSensors();
	
	Collection<Effector> getEffectors();

	void prepareNextState();

	void damage(int damage);

	void gotoNextState();

	boolean wantBreed();
	
	void onCollision(Set<Organism> organisms);
	
	void onDamage(int damage);

}
