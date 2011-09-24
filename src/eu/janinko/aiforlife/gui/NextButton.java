package eu.janinko.aiforlife.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import eu.janinko.aiforlife.World.World;

public class NextButton extends JButton implements ActionListener {
	private static final long serialVersionUID = -6863219464013234324L;
	
	World world;
	MainWindow window;

	public NextButton(World w, MainWindow window){
		super("Next");
		world = w;
		this.window = window;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Clicked Next.");
		world.nextTick();
		window.worldUpdated();
	}

}
