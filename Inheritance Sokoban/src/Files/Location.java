package Files;
import Mods.*;

// Location class
public class Location implements Cloneable {
	
	// Attributes
	private int x;
	private int y;
	
	// Constructor
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// Methods
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public String compareLocs(Location initialLoc) {
		
		int deltaX = this.getX() - initialLoc.getX();
		int deltaY = this.getY() - initialLoc.getY();
		if (deltaX > 0 && deltaY == 0) {
			return "RIGHT";
		} else if (deltaX < 0 && deltaY == 0) {
			return "LEFT";
		} else if (deltaX == 0 && deltaY > 0) {
			return "UP";
		} else if (deltaX == 0 && deltaY < 0) {
			return "DOWN";
		} else { // might not need this
			return "OTHER";
		}
	}
	
	public boolean equals(Location loc) {
		if (this.x == loc.x && this.y == loc.y) {
			return true;
		} else {
			return false;
		}
	}
	
	public Location makeCopy() {

		Location locObject = null;
		
		try {
			locObject = (Location) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return locObject;
	}

}
