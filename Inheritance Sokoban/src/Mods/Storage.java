package Mods;

import Files.*;
import java.util.*;


// Player class
public class Storage extends Modification {
	
	// Attributes
	public static String letter = "S";
	// Inherited from Parent Modification Class
	
	// Constructor
	public Storage(Location loc) {
		
		this.initLoc(loc);
		this.onFloor = false;
		
	}
	
	// Methods
	@Override
	public Modification makeCopy() {
		
		Storage storageObject = null;
		
		try {
			storageObject = (Storage) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return storageObject;
	}

	@Override
	public String update(String cmd, Map<Integer, ArrayList<Location>> locs, Map<Location, ArrayList<Modification>> modLocs) {
		return null;
	}
}