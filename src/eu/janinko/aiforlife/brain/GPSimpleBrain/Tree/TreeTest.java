package eu.janinko.aiforlife.brain.GPSimpleBrain.Tree;

import java.util.Random;

import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.World.FlatWorld.NoninteractFlatWorld;
import eu.janinko.aiforlife.brain.GPSimpleBrain.GPSimpleBrainCatalog;

public class TreeTest {
	public static void main(String[] args){
		Random generator = new Random();
		World w = new NoninteractFlatWorld(20, 20);
		GPSimpleBrainCatalog catalog = new GPSimpleBrainCatalog(w);
		
		BlockNode root = new BlockNode(null, generator, catalog, 0);
		root.print(0);
	}

}
