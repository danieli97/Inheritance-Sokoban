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
							Location loc = new Location(mod.getLoc().getX(),mod.getLoc().getY()); // at box coord
							Player temp = new Player(loc);		// Add a player to the right of the box, and push it left, as if
							this.board.mods.add(temp);				// PullyPlayer pulled it
							cmd = mod.update("PLEFT");
							for (Modification theMod : this.board.getMods()) {
								if (theMod == mod) {
									theMod = mod;		// set the 
								} 
								break;
							}
							this.board.mods.remove(temp);		// remove temp Player from mods, since we dont want it to get drawn
							return cmd;
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
							Location loc = new Location(mod.getLoc().getX(),mod.getLoc().getY()); // at box coord
							Player temp = new Player(loc);		// Add a player to the right of the box, and push it left, as if
							this.board.mods.add(temp);				// PullyPlayer pulled it
							cmd = mod.update("PRIGHT");
							for (Modification theMod : this.board.getMods()) {
								if (theMod == mod) {
									theMod = mod;		// set the 
								} 
								break;
							}			
							this.board.mods.remove(temp);		// remove temp Player from mods, since we dont want it to get drawn
							return cmd;
						}		
					}	
					return "PRIGHT";
				}
				break;
			case "UP":
				if (this.move(cmd)){
					int coord = this.getCoord() + 2*this.board.getWidth();
					for (Modification mod :getModsAt(coord)) {		// runs through the mods to the right of PullyPlayer
						if (mod.getTag().equals("Box")) {
							Location loc = new Location(mod.getLoc().getX(),mod.getLoc().getY()); // at box coord
							Player temp = new Player(loc);		// Add a player to the right of the box, and push it left, as if
							this.board.mods.add(temp);				// PullyPlayer pulled it
							cmd = mod.update("PUP");
							for (Modification theMod : this.board.getMods()) {
								if (theMod == mod) {
									theMod = mod;		// set the 
								} 
								break;
							}
							this.board.mods.remove(temp);		// remove temp Player from mods, since we dont want it to get drawn
							return cmd;
						}		
					}	
					return "PUP";
				}
				break;
			case "DOWN":
				if (this.move(cmd)){
					int coord = this.getCoord() - 2*this.board.getWidth();
					for (Modification mod :getModsAt(coord)) {		// runs through the mods to the right of PullyPlayer
						if (mod.getTag().equals("Box")) {
							Location loc = new Location(mod.getLoc().getX(),mod.getLoc().getY()); // at box coord
							Player temp = new Player(loc);		// Add a player to the right of the box, and push it left, as if
							this.board.mods.add(temp);				// PullyPlayer pulled it
							cmd = mod.update("PDOWN");
							for (Modification theMod : this.board.getMods()) {
								if (theMod == mod) {
									theMod = mod;		// set the 
								} 
								break;
							}	
							this.board.mods.remove(temp);		// remove temp Player from mods, since we dont want it to get drawn
							return cmd;
						}		
					}	
					return "PDOWN";
				}
		}
		return null;
	}
}