package Files;

public abstract class Modification implements Cloneable, Observer {

	// Attributes
	protected Location loc;
	protected String letter;
	protected boolean onFloor;
	protected String img;
	protected static Board board;
	
	// Regular Methods
	public Location getLoc() {
		return this.loc;
	}

	public void initLoc(Location loc){
		this.loc = loc;
	}
	
	public void setLoc(int x, int y) {
		this.loc.setX(x);
		this.loc.setY(y);
	}
	
	public String getLetter(){
		return this.letter;
	}

	public int getCoord(){
		return Modification.board.getWidth() * this.loc.getY() + this.loc.getY();
	}

	public int getCoord(int x, int y){
		return Modification.board.getWidth() * y + x;
	}

	// Abstract Methods
	public abstract Modification makeCopy();
	public abstract String update(String cmd);
	
}
