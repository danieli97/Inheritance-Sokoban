package Files;

import java.util.ArrayList;

public abstract class Modification implements Cloneable {

	// Attributes
	protected Location loc;			// Location object for this Modification
	protected String letter;		// Letter associated with this Modification on level map
	protected boolean onFloor;		// true if object may be moved on top of
	protected String img;			// name of file containing icon for this Modification
	protected boolean canPush;		// true if the object can be pushed\
	protected String tag; 			// 
	protected static Board board;	// holds current board (static = shared between all Modifications)

	// Methods
	
	// Static
	public static int getCoord(int x, int y) {
		/*
		calculate and return coordinate for a given x and y value
		eg. 
		for board:
			WWW
			WWW
			WWW
		coordinates are as follows:
			678
			345
			012
		*/
		return Modification.board.getWidth() * y + x;
	}
	
	public static ArrayList<Modification> getModsAt(int coord) {
		/*
		given a coordinate, return a list of Modifications found at that coordinate
		*/
		ArrayList<Modification> mods = new ArrayList<Modification>(); 	// ArrayList of Modifications called mods
		for (Location loc : board.getLocs().get(coord)) {				// for each Location object found on the 
																		// board at coordinate
			mods.add(Modification.board.getModLocs().get(loc));			// add Modification for Location to mods
		}
		return mods;
	}

	// Getters
	public String getTag() {
		return this.tag;
	}
	
	public Location getLoc() {
		return this.loc;
	}

	public String getLetter() {
		return this.letter;
	}

	public int getCoord() {
		// return getCoord for this Modification
		return getCoord(this.loc.getX(), this.loc.getY());
	}

	public boolean isOnFloor() {
		return this.onFloor;
	}

	public boolean canBePushed() {
		return this.canPush;
	}

	public boolean isBoxStored(){
		return false;
	}

	// Setters
	public void initLoc(Location loc) {
		/*
		sets Location Object to parameter Object 
		*/
		this.loc = loc;
	}

	public void setLoc(int x, int y) {
		/*
		sets values x and y in Location object 
		with parameter x and y respectively
		*/
		this.loc.setX(x);
		this.loc.setY(y);
	}

	// Functions
	public boolean canMove(String cmd) {
		/*
		check if this object can move according to the command given
		return true if the current mod can move
		return false if the mod cannot move
		*/

		// get coordinate that the mod will be moved to
		int coord = 0;
		if (cmd.equals("LEFT")) {
			coord = Modification.getCoord(this.loc.getX() - 1, this.loc.getY());
		} else if (cmd.equals("RIGHT")) {
			coord = Modification.getCoord(this.loc.getX() + 1, this.loc.getY());
		} else if (cmd.equals("UP")) {
			coord = Modification.getCoord(this.loc.getX(), this.loc.getY() - 1);
		} else if (cmd.equals("DOWN")) {
			coord = Modification.getCoord(this.loc.getX(), this.loc.getY() + 1);
		} else {
			System.out.println(cmd + " is not a valid movement");
			return false;
		}

		for (Modification mod : Modification.getModsAt(coord)) { 	// for each mod at new position
			if (!mod.isOnFloor()) { 								// if the mod is not on the floor
				if (mod.canPush) { 									// if the mod is not on the floor but can be pushed
					return mod.canMove(cmd); 						// check if the mod can be moved with cmd
				} else { 											// if mod is not on floor and cannot be moved (eg. wall)
					return false; 									// return false
				}
			}
		}

		return true;	// return true if no mods have returned false
	}
	
	public boolean move(String cmd){
		switch (cmd) {
			case "LEFT":
				if (this.canMove(cmd)) {
					board.changeLoc(this.getCoord(), getCoord(this.loc.getX() - 1, this.loc.getY()), this.loc);
					this.loc.setX(this.loc.getX() - 1);
					return true;
				}
				break;
			case "RIGHT":
				if (this.canMove(cmd)) {
					board.changeLoc(this.getCoord(), getCoord(this.loc.getX() + 1, this.loc.getY()), this.loc);
					this.loc.setX(this.loc.getX() + 1);
					return true;
				}
				break;
			case "UP":
				if (this.canMove(cmd)) {
					board.changeLoc(this.getCoord(), getCoord(this.loc.getX(), this.loc.getY() - 1), this.loc);
					this.loc.setY(this.loc.getY() - 1);
					return true;
				}
				break;
			case "DOWN":
				if (this.canMove(cmd)) {
					board.changeLoc(this.getCoord(), getCoord(this.loc.getX(), this.loc.getY() + 1), this.loc);
					this.loc.setY(this.loc.getY() + 1);
					return true;
				}
				break;
			default:
				return false;
		}
		return false;
	}

	// Abstract Methods
	public abstract Modification makeCopy();	// return a copy of this Object

	public abstract String update(String cmd);	// for some command perform some update

}
