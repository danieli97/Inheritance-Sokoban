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
		// can check the change in loc using the og location and Location object
		// maybe this returns a cmd and null if none back to board which can recursively call notify with the cmd
		// could return mod and cmd
		// change to switch
		switch(cmd){
			case "LEFT":
				Modification.board.changeLoc(this.getCoord(), this.getCoord(this.loc.getX()-1, this.loc.getY()), this.loc);
				this.loc.setX(this.loc.getX()-1);
				return "PLEFT";
			case "RIGHT":
				Modification.board.changeLoc(this.getCoord(), this.getCoord(this.loc.getX()+1, this.loc.getY()), this.loc);
				this.loc.setX(this.loc.getX()+1);
				return "PRIGHT";
			case "UP":
				Modification.board.changeLoc(this.getCoord(), this.getCoord(this.loc.getX(), this.loc.getY()-1), this.loc);
				this.loc.setY(this.loc.getY()-1);
				return "PUP";
			case "DOWN":
				Modification.board.changeLoc(this.getCoord(), this.getCoord(this.loc.getX(), this.loc.getY()+1), this.loc);
				this.loc.setY(this.loc.getY()+1);
				return "PDOWN";
			default:
				return null;
		}
	}
}
