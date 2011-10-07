package eu.janinko.aiforlife.World.FlatWorld;

import java.util.Random;

public class Position{
	
	int x;
	int y;
	
	int r;
	
	int sizeX;
	int sizeY;
	
	public Position(){
		this(10,10);
	}
	
	public Position(int sizeX, int sizeY){
		this(sizeX,sizeY,0,0,0);
		Random generator = new Random();
		this.x = generator.nextInt(sizeX);
		this.y = generator.nextInt(sizeY);
		this.r = generator.nextInt(4);
	}
	
	public Position(int sizeX, int sizeY, int x, int y, int r){
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public Position(Position old){
		this(old.getSizeX(), old.getSizeY(), old.getPosX(), old.getPosY(), old.getRot());
	}
	
	public void moveForward(int x){
		switch(r){
		case 0: this.move(0,-x); break;
		case 1: this.move(x,0); break;
		case 2: this.move(0,x); break;
		case 3: this.move(-x,0); break;
		}
	}
	
	public void move(int x, int y){
		this.x = (this.x + x) % sizeX;
		this.y = (this.y + y) % sizeY;

		if(this.x < 0){
			this.x += sizeX;
		}
		
		if(this.y < 0){
			this.y += sizeY;
		}
	}
	
	public void rotate(int r){
		this.r = (this.r + r) % 4;
		if(this.r < 0){
			this.r += 4;
		}
	}

	public int getPosX() {
		return x;
	}

	public int getPosY() {
		return y;
	}

	public int getRot() {
		return r;
	}

	public void setPosX(int d) {
		this.x = d % sizeX;

		if(this.x < 0){
			this.x += sizeX;
		}
	}

	public void setPosY(int d) {
		this.y = d % sizeY;

		if(this.y < 0){
			this.y += sizeY;
		}
	}

	public void setRotX(int d) {
		this.r = d % 4;

		if(this.r < 0){
			this.r += 4;
		}
	}
	
	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		throw new UnsupportedOperationException();
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + sizeX;
		result = prime * result + sizeY;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Position))
			return false;
		Position other = (Position) obj;
		if (sizeX != other.sizeX)
			return false;
		if (sizeY != other.sizeY)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public String toString(){
		return this.x + ", " + this.y + ": " + this.r;
	}
	

}
