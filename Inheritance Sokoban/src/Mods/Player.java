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
	public void update(String cmd, Map<int[], ArrayList<Location>> locs, Map<Location, ArrayList<Modification>> modLocs) {
		if (cmd.equals("LEFT")) {
			int[] newLoc = {this.loc.getX()-1, this.loc.getY()};
			ArrayList<Location> spot = locs.get(newLoc);

			this.loc.setX(this.loc.getX()-1);
		}
		else if (cmd.equals("RIGHT")) {
			this.loc.setX(this.loc.getX()+1);
		}
		else if (cmd.equals("UP")) {
			this.loc.setY(this.loc.getY()+1);
		}
		else if (cmd.equals("DOWN")) {
			this.loc.setY(this.loc.getY()-1);
		}
	}
}
