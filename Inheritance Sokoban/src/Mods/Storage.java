package Mods;

import Files.*;

// Player class
public class Storage extends Modification {

	// Attributes
	// Inherited from Parent Modification Class

	// Constructor
	public Storage(Location loc) {

		this.initLoc(loc);
		this.onFloor = true;
		this.letter = "S";
		this.img = "Storage";
		this.canPush = false;

	}

	// Methods
	@Override
	public Modification makeCopy() {

		Storage Object = null;

		try {
			Object = (Storage) super.clone();
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