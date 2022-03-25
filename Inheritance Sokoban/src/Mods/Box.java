package Mods;

import Files.*;

// Box class
public class Box extends Modification {
	
	// Attributes
	// Inherited from Parent Modification Class

	private final String DEFAULT_BOX = "Box";
	private final String STORAGE_BOX = "box_on_storage";
	
	// Constructor
	public Box(Location loc) {
		
		this.initLoc(loc);
		this.onFloor = false;
		this.letter = "B";
		this.img = DEFAULT_BOX;
		this.canPush = true;

	}
	
	// Methods
	@Override
	public Modification makeCopy() {

		Box Object = null;
		
		try {
			Object = (Box) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return Object;
	}

	@Override
	public String update(String cmd) {

		switch(cmd){
			case "PLEFT":
				for (Modification mod : Modification.getModsAt(this.getCoord())){
					if (mod.getLetter().equals("P")){
						Modification.board.changeLoc(this.getCoord(), Modification.getCoord(this.loc.getX()-1, this.loc.getY()), this.loc);
						this.loc.setX(this.loc.getX()-1);
						for (Modification newMod : Modification.getModsAt(this.getCoord())){
							if (newMod.getLetter().equals("S")){
								this.img = STORAGE_BOX;
								return null;
							}
							else {
								this.img = DEFAULT_BOX;
							}
						}
					}
				}
				return null;
			case "PRIGHT":
				for (Modification mod : Modification.getModsAt(this.getCoord())){
					if (mod.getLetter().equals("P")){
						Modification.board.changeLoc(this.getCoord(), Modification.getCoord(this.loc.getX()+1, this.loc.getY()), this.loc);
						this.loc.setX(this.loc.getX()+1);
						for (Modification newMod : Modification.getModsAt(this.getCoord())){
							System.out.println(newMod.getLetter());
							if (newMod.getLetter().equals("S")){
								this.img = STORAGE_BOX;
								return null;
							}
							else {
								this.img = DEFAULT_BOX;
							}
						}
					}
				}
				return null;
			case "PUP":
				for (Modification mod : Modification.getModsAt(this.getCoord())){
					if (mod.getLetter().equals("P")){
						Modification.board.changeLoc(this.getCoord(), Modification.getCoord(this.loc.getX(), this.loc.getY()-1), this.loc);
						this.loc.setY(this.loc.getY()-1);
						for (Modification newMod : Modification.getModsAt(this.getCoord())){
							if (newMod.getLetter().equals("S")){
								this.img = STORAGE_BOX;
								return null;
							}
							else {
								this.img = DEFAULT_BOX;
							}
						}
					}
				}
				return null;
			case "PDOWN":
				for (Modification mod : Modification.getModsAt(this.getCoord())){
					if (mod.getLetter().equals("P")){
						Modification.board.changeLoc(this.getCoord(), Modification.getCoord(this.loc.getX(), this.loc.getY()+1), this.loc);
						this.loc.setY(this.loc.getY()+1);
						for (Modification newMod : Modification.getModsAt(this.getCoord())){
							if (newMod.getLetter().equals("S")){
								this.img = STORAGE_BOX;
								return null;
							}
							else {
								this.img = DEFAULT_BOX;
							}
						}
					}
				}
				return null;
			default:
				return null;			
		}

	}
}
