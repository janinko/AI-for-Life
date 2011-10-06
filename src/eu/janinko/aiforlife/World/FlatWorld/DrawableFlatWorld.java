package eu.janinko.aiforlife.World.FlatWorld;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import eu.janinko.aiforlife.Organism.Organism;
import eu.janinko.aiforlife.World.DrawableWorld;

public class DrawableFlatWorld extends FlatWorld implements DrawableWorld {
	
	private int gridsize = 20;
	private int space = 6;
	
	public DrawableFlatWorld() {
	}

	public DrawableFlatWorld(int sizeX, int sizeY) {
		super(sizeX, sizeY);
	}

	@Override
	public void draw(Graphics graphic) {
		this.paintGrid((Graphics2D) graphic);
		
		for(Organism o : this.getOrganisms()){
			paintOrganism((Graphics2D) graphic,o);
		}
	}
	
	private void paintGrid(Graphics2D g) {
		int sizex = this.getSizeX();
		int sizey = this.getSizeY();
		
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(1));
		for(int i=0; i<=sizex; i++){
			g.drawLine(space, space + i*gridsize, space + sizey*gridsize, space + i*gridsize);
		}
		for(int i=0; i<=sizey; i++){
			g.drawLine(space+ i*gridsize, space , space + i*gridsize, space + sizex*gridsize);
		}
		
	}
	
	private void paintOrganism(Graphics2D g, Organism o){
		Position pos = this.getOrganismPosition(o);
		
		g.setColor(new Color(o.color(0), o.color(1), o.color(2)));
		g.fillOval((int)(pos.getPosX() * gridsize + space + 2), (int)(pos.getPosY()*gridsize + space + 2), gridsize - 4, gridsize - 4);
		g.setColor(Color.BLACK);
		int x = pos.getPosX() * gridsize + space;
		int y = pos.getPosY() * gridsize + space;
		switch(pos.getRot()){
		case 0:{
			x += gridsize / 2 - 3;
			y += 4;
			break;
		}
		case 1: {
			x += gridsize - 8;
			y += gridsize / 2 - 3;
			break;
		}
		case 2: {
			x += gridsize / 2 - 3;
			y += gridsize - 8;
			break;
		}
		case 3: {
			x += 4;
			y += gridsize / 2 - 3;
			break;
		}
		}
		g.fillOval(x, y, 6, 6);
	}
}
