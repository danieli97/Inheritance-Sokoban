package Mods;

import Files.*;

// Player class
public class Player extends Modification {

	// Attributes
	// Inherited from Parent Modification Class

	// Constructor
	public Player(Location loc) {

		this.initLoc(loc);
		this.onFloor = false;
		this.letter = "P";
		this.img = "Player";
		this.canPush = false;
		this.tag = "Player";

	}

	// Methods

	// Overrides
	@Override
	public Modification makeCopy() {

		Player Object = null;

		try {
			Object = (Player) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return Object;
	}

	@Override
	public String update(String cmd) {
		switch (cmd) {
			case "LEFT":
				if (this.move(cmd)){
					return "PLEFT";
				}
				break;
			case "RIGHT":
				if (this.move(cmd)){
					return "PRIGHT";
				}
				break;
			case "UP":
				if (this.move(cmd)){
					return "PUP";
				}
				break;
			case "DOWN":
				if (this.move(cmd)){
					return "PDOWN";
				}
		}
		return null;
	}
}
