package Mods;

import Files.*;


// Player class
public class PullyPlayer extends Modification {
	
	// Attributes
	// No Ice specific attributesIce.java
	
	// Constructor
	public PullyPlayer(Location loc) {
		
		this.initLoc(loc);
		this.onFloor = false;
		this.letter = "Q";
		this.img = "Player";
		this.canPush = false;
		this.tag = "Player";
		
	}
	
	// Methods
	@Override
	public Modification makeCopy() {
		
		PullyPlayer Object = null;
		
		try {
			Object = (PullyPlayer) super.clone();
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
					int coord = this.getCoord() + 2;	// use coord to right of player, to see if a box is there to be pulled
					for (Modification mod :getModsAt(coord)) {		// runs through the mods to the right of PullyPlayer
						if (mod.getTag().equals("Box")) {
							Location loc = new Location(mod.getLoc().getX(), mod.getLoc().getY());	// create location, add it to locs
							this.board.locs.get(coord).add(loc);
							Player temp = new Player(loc);		// create new player at location, add it to modLocs
							this.board.modLocs.put(loc, temp);
						}		
					}	
					return "PLEFT";
				}
				break;
			case "RIGHT":
				if (this.move(cmd)){
					int coord = this.getCoord() - 2;
					for (Modification mod :getModsAt(coord)) {		// runs through the mods to the right of PullyPlayer
						if (mod.getTag().equals("Box")) {
							Location loc = new Location(mod.getLoc().getX(), mod.getLoc().getY());	// create location, add it to locs
							this.board.locs.get(coord).add(loc);
							Player temp = new Player(loc);		// create new player at location, add it to modLocs
							this.board.modLocs.put(loc, temp);
						}		
					}	
					return "PRIGHT";
				}
				break;
			case "UP":
				if (this.move(cmd)){
					int coord = this.getCoord() + 2 * this.board.getWidth();
					for (Modification mod :getModsAt(coord)) {
						if (mod.getTag().equals("Box")) {
							Location loc = new Location(mod.getLoc().getX(), mod.getLoc().getY());	// create location, add it to locs
							this.board.locs.get(coord).add(loc);
							Player temp = new Player(loc);		// create new player at location, add it to modLocs
							this.board.modLocs.put(loc, temp);
						}		
					}	
					return "PUP";
				}
				break;
			case "DOWN":
				if (this.move(cmd)){
					int coord = this.getCoord() - 2 * this.board.getWidth();
					for (Modification mod :getModsAt(coord)) {		// runs through the mods to the right of PullyPlayer
						if (mod.getTag().equals("Box")) {
							Location loc = new Location(mod.getLoc().getX(), mod.getLoc().getY());	// create location, add it to locs
							this.board.locs.get(coord).add(loc);
							Player temp = new Player(loc);		// create new player at location, add it to modLocs
							this.board.modLocs.put(loc, temp);
						}		
					}	
					return "PDOWN";
				}
		}
		return null;
	}
}