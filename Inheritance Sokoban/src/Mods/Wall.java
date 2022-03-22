package Mods;

import Files.*;


// Wall class
public class Wall extends Modification {
	
	// Attributes
	// Inherited from Parent Modification Class
	
	// Constructor
	public Wall(Location loc) {
		
		setLoc(loc);
		this.onFloor = false;
		
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
	public void update(Location playerLoc, Location newPlayerLoc) { }
	
}