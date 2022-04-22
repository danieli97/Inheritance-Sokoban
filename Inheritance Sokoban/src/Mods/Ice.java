package Mods;

import Files.*;


// Player class
public class Ice extends Modification {
	
	// Attributes
	// No Ice specific attributesIce.java
	
	// Constructor
	public Ice(Location loc) {
		
		this.initLoc(loc);
		this.onFloor = true;
		this.letter = "I";
		this.img = "Ice";
		this.canPush = false;
		this.tag = "Ice";
		
	}
	
	// Methods
	@Override
	public Modification makeCopy() {
		
		Ice Object = null;
		
		try {
			Object = (Ice) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return Object;
	}

	@Override
	public String update(String cmd) {
		switch(cmd){
		case "PLEFT":
			for (Modification mod : getModsAt(this.getCoord())) {
				if (mod.getTag().equals("Player") ) {
					return "LEFT";
				}
			}
			return null;
		case "PRIGHT":
			for (Modification mod : getModsAt(this.getCoord())) {
				if (mod.getTag().equals("Player") ) {
					return "RIGHT";
				}
			}
			return null;
		case "PUP":
			for (Modification mod : getModsAt(this.getCoord())) {
				if (mod.getTag().equals("Player") ) {
					return "UP";
				}
			}
			return null;
		case "PDOWN":
			for (Modification mod : getModsAt(this.getCoord())) {
				if (mod.getTag().equals("Player") ) {
					return "DOWN";
				}
			}
			return null;
		default:
			return null;
		}
	}
}