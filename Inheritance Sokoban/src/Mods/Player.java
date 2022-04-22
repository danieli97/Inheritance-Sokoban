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
	
	/**
	* Returns a string variable, which can be set to any of the follwoing values: "PLEFT", "PRIGHT", "PUP", "PDOWN".
	* The cmd argument must be one of the following: "UP", "DOWN", "LEFT", "RIGHT".
	* Based on the cmd given, this method returns a value that specifies which direction the player has moved.
	* This return value is then given as a cmd to the other observer objects, which will be notified that the player has moved.
	* @param 	cmd	a string that represents the direction the player has moved
	* @return		a boolean value that represents whether or not the modification is able to move in the specified direction
	*/
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
