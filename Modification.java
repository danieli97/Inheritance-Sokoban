
public abstract class Modification implements Cloneable, Observer {

	// Attributes
	protected Location loc;
	protected Board theBoard;
	protected boolean onFloor;
	
	// Regular Methods
	public Location getLoc() {
		
		return this.loc;
		
	}
	
	public void setLoc(Location newLoc) {

		this.loc = newLoc;
		
	}
	
	// Abstract Methods
	public abstract Modification makeCopy();
	public abstract void update(Location playerLoc, Location newPlayerLoc);
	
}
