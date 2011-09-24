package eu.janinko.aiforlife.Effector;

public interface Effector {
	
	void effect(double[] in) throws WrongEffectorInputCount;

}
