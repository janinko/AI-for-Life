package eu.janinko.aiforlife.World;

public interface WorldStatistics{

	int getNewbornCount();

	int getDiedCount();
	
	int getDamagedCount();
	
	double getProperty(String propertyname);
}
