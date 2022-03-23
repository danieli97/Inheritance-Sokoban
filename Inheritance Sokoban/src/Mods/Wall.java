package Mods;

import Files.*;
import java.util.*;


// Wall class
public class Wall extends Modification {
	
	// Attributes
	public static String letter = "W";
	// Inherited from Parent Modification Class
	
	// Constructor
	public Wall(Location loc) {
		
		this.initLoc(loc);
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
	public String update(String cmd, Map<Integer, ArrayList<Location>> locs, Map<Location, ArrayList<Modification>> modLocs) {return null;}
	
}