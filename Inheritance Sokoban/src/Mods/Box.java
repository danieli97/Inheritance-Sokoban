package Mods;

import Files.*;

// Box class
public class Box extends Modification {
	
	// Attributes
	// Inherited from Parent Modification Class

	private final String DEFAULT_BOX = "Box";
	private final String STORAGE_BOX = "box_on_storage";
	
	// Constructor
	public Box(Location loc) {
		
		this.initLoc(loc);
		this.onFloor = false;
		this.letter = "B";
		this.img = DEFAULT_BOX;

	}
	
	// Methods
	@Override
	public Modification makeCopy() {

		Box Object = null;
		
		try {
			Object = (Box) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return Object;
	}

	@Override
	public String update(String cmd) {

		return null;

	}
}
