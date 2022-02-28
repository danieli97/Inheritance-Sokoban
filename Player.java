// Player class
public class Player extends Modification{
	
	// Attributes
	// Inherited from Parent Modification Class
	
	// Constructor
	public Player(Location loc) {
		
		setLoc(loc);
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
	public void update(Location playerLoc, Location newPlayerLoc) {
		
		if (theBoard.isFree(newPlayerLoc)) {
			
			setLoc(newPlayerLoc);
			
		}
	}
}
