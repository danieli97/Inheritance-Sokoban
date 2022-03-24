package Mods;

import Files.*;

// Wall class
public class Wall extends Modification {
	
	// Attributes
	// Inherited from Parent Modification Class
	
	// Constructor
	public Wall(Location loc) {
		
		this.initLoc(loc);
		this.onFloor = false;
		this.letter = "W";
		this.img = "Wall";

	}
	
	// Methods
	@Override
	public Modification makeCopy() {
		
		Wall wallObject = null;
		
		try {
			wallObject = (Wall) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return wallObject;
	}

	@Override
	public String update(String cmd) {
		return null;
	}
	
}