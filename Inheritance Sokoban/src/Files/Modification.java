package Files;

import java.util.*;

public abstract class Modification implements Cloneable, Observer {

	// Attributes
	protected Location loc;
	public static String letter;
	protected Board theBoard;
	protected boolean onFloor;
	
	// Regular Methods
	public Location getLoc() {
		return this.loc;
	}

	public void initLoc(Location loc){
		this.loc = loc;
	}
	
	public void setLoc(Location loc) {
		this.loc.setX(loc.getX());
		this.loc.setY(loc.getY());
	}
	
	// Abstract Methods
	public abstract Modification makeCopy();
	public abstract String update(String cmd, Map<Integer, ArrayList<Location>> locs, Map<Location, ArrayList<Modification>> modLocs);
	
}
