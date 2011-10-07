package eu.janinko.aiforlife.World.FlatWorld;

public class ConstPosition extends Position {
	public ConstPosition(){
		super();
	}
	
	public ConstPosition(int sizeX, int sizeY){
		super(sizeX,sizeY);
	}
	
	public ConstPosition(int sizeX, int sizeY, int x, int y, int r){
		super(sizeX, sizeY, x, y, r);
	}
	
	public ConstPosition(Position old){
		super(old);
	}
	
	public void moveForward(int x){
		throw new UnsupportedOperationException("ConstPosition can't move.");
	}
	
	public void move(int x, int y){
		throw new UnsupportedOperationException("ConstPosition can't move.");
	}

	public void setPosX(int d) {
		throw new UnsupportedOperationException("ConstPosition can't change position.");
	}

	public void setPosY(int d) {
		throw new UnsupportedOperationException("ConstPosition can't change position.");
	}

}
