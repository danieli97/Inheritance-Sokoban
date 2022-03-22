package Files;

public abstract class Modification implements Cloneable, Observer {

	// Attributes
	protected Location loc;
	protected String letter;
	protected Board theBoard;
	protected boolean onFloor;
	
	// Regular Methods
	public Location getLoc() {
		
		return this.loc;
		
	}
	
	public void setLoc(Location loc) {
		
		this.loc.setX(loc.getX());
		this.loc.setY(loc.getY());
		
	}
	
	public String getLetter() {
		return this.letter;
	}
	
	// Abstract Methods
	public abstract Modification makeCopy();
	public abstract void update(Location playerLoc, Location newPlayerLoc);
	
}
