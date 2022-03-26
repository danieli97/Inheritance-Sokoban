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

	}

	// Methods
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
				if (this.canMove(cmd)) {
					board.changeLoc(this.getCoord(), getCoord(this.loc.getX() - 1, this.loc.getY()), this.loc);
					this.loc.setX(this.loc.getX() - 1);
					return "PLEFT";
				}
			case "RIGHT":
				if (this.canMove(cmd)) {
					board.changeLoc(this.getCoord(), getCoord(this.loc.getX() + 1, this.loc.getY()), this.loc);
					this.loc.setX(this.loc.getX() + 1);
					return "PRIGHT";
				}
			case "UP":
				if (this.canMove(cmd)) {
					board.changeLoc(this.getCoord(), getCoord(this.loc.getX(), this.loc.getY() - 1), this.loc);
					this.loc.setY(this.loc.getY() - 1);
					return "PUP";
				}
			case "DOWN":
				if (this.canMove(cmd)) {
					board.changeLoc(this.getCoord(), getCoord(this.loc.getX(), this.loc.getY() + 1), this.loc);
					this.loc.setY(this.loc.getY() + 1);
					return "PDOWN";
				}
			default:
				return null;
		}
	}
}
