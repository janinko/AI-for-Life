package eu.janinko.aiforlife.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import eu.janinko.aiforlife.World.World;
import eu.janinko.aiforlife.World.DrawableWorld;

public class PaintedWorld extends JPanel {
	private static final long serialVersionUID = -1602993392823705345L;
	
	private DrawableWorld world;
	
	public PaintedWorld(World w){
		super();
		if(!(w instanceof DrawableWorld)) throw new IllegalArgumentException("Excepting DrawableWorld instance");
		world = (DrawableWorld) w;
	}
	
	@Override
	public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        Graphics2D g = (Graphics2D) graphic.create();
        
        world.draw(g);
   }

}
