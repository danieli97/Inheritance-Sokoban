// Location class
public class Location {
	
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

}
