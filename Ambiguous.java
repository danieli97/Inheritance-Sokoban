package sokoban;

public abstract class Ambiguous {
	// Attributes
	protected Location loc;
	protected String name;
	protected boolean floorObject;
	protected boolean movable;
	
	// Methods
	// Getters
	public Location location() {
		return this.loc;
	}
	
	public String name() {
		return this.name;
	}
	
	public boolean floorObject() {
		return this.floorObject;
	}
	
	public boolean movable() {
		return this.movable;
	}
	
	// Setters
	public void setLocation(Location loc) {		// may need to make private or get rid of it all together
		this.loc = loc;
	}
	
	// method that defines what happens when a player tries to move onto an object
	public abstract void playerInteractWithThisObject();
}
