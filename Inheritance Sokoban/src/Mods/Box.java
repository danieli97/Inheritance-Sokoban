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

	}
	
	// Methods
	@Override
	public Modification makeCopy() {

		Box boxObject = null;
		
		try {
			boxObject = (Box) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return boxObject;
	}

	@Override
	public String update(String cmd) {
		
		// Location boxLoc = this.getLoc();
		
		// if (boxLoc.equals(newPlayerLoc)) {
			
		// 	String direction = newPlayerLoc.compareLocs(playerLoc);
			
		// 	if (direction.equals("RIGHT")) {
				
		// 		Location newLoc = new Location(boxLoc.getX() + 1, boxLoc.getY());
		// 		if (theBoard.isFree(newLoc)) {
		// 			this.setLoc(newLoc);
		// 		}
		// 	} else if (direction.equals("LEFT")) {
				
		// 		Location newLoc = new Location(boxLoc.getX() - 1, boxLoc.getY());
		// 		if (theBoard.isFree(newLoc)) {
		// 			this.setLoc(newLoc);
		// 		}
				
		// 	} else if (direction.equals("UP")) {
				
		// 		Location newLoc = new Location(boxLoc.getX(), boxLoc.getY() + 1);
		// 		if (theBoard.isFree(newLoc)) {
		// 			this.setLoc(newLoc);
		// 		}
				
		// 	} else if (direction.equals("DOWN")) {
				
		// 		Location newLoc = new Location(boxLoc.getX(), boxLoc.getY() - 1);
		// 		if (theBoard.isFree(newLoc)) {
		// 			this.setLoc(newLoc);
		// 		}
				
		// 	}
		// }

		return null;

	}
}
