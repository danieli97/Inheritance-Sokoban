package Mods;

import Files.*;
import java.util.*;


// Player class
public class Player extends Modification {
	
	// Attributes
	public static String letter = "P";
	// Inherited from Parent Modification Class
	
	// Constructor
	public Player(Location loc) {
		
		this.initLoc(loc);
		this.onFloor = false;
		
	}
	
	// Methods
	@Override
	public Modification makeCopy() {
		
		Player playerObject = null;
		
		try {
			playerObject = (Player) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return playerObject;
	}

	@Override
	public String update(String cmd, Map<Integer, ArrayList<Location>> locs, Map<Location, ArrayList<Modification>> modLocs) {
		// can check the change in loc using the og location and Location object
		// maybe this returns a cmd and null if none back to board which can recursively call notify with the cmd
		// could return mod and cmd
		// change to switch
		switch(cmd){
			case "LEFT":
				int[] newLoc = {this.loc.getX()-1, this.loc.getY()};
				ArrayList<Location> spot = locs.get();

				this.loc.setX(this.loc.getX()-1);
				return "PLEFT";
			case "RIGHT":
				this.loc.setX(this.loc.getX()+1);
				return "PRIGHT";
			case "UP":
				this.loc.setY(this.loc.getY()+1);
				return "PUP";
			case "DOWN":
				this.loc.setY(this.loc.getY()-1);
				return "PDOWN";
		}

		return null;
	}
}
