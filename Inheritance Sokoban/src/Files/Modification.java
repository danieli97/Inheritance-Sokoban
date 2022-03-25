package Files;

import java.util.ArrayList;

public abstract class Modification implements Cloneable, Observer {

	// Attributes
	protected Location loc;
	protected String letter;
	protected boolean onFloor;
	protected String img;
	protected static Board board;
	protected boolean canPush;
	
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

	public boolean isOnFloor(){
		return this.onFloor;
	}

	public int getCoord(){
		return Modification.board.getWidth() * this.loc.getY() + this.loc.getX();
	}

	public static int getCoord(int x, int y){
		return Modification.board.getWidth() * y + x;
	}

	public boolean canBePushed(){
		return this.canPush;
	}

	public static ArrayList<Modification> getModsAt(int coord){
		ArrayList<Modification> mods = new ArrayList<Modification>();
		for (Location loc : Modification.board.getLocs().get(coord)){
			mods.addAll(Modification.board.getModLocs().get(loc));
		}
		return mods;
	}

	public boolean canMove(String cmd){
		int coord = 0;

		if (cmd.equals("LEFT")){
			coord = Modification.getCoord(this.loc.getX()-1, this.loc.getY());
		}
		else if (cmd.equals("RIGHT")){
			coord = Modification.getCoord(this.loc.getX()+1, this.loc.getY());
		}
		else if (cmd.equals("UP")){
			coord = Modification.getCoord(this.loc.getX(), this.loc.getY()-1);
		}
		else if (cmd.equals("DOWN")){
			coord = Modification.getCoord(this.loc.getX(), this.loc.getY()+1);
		}

		for (Modification mod : Modification.getModsAt(coord)){	// for each mod at new position
			if (!mod.isOnFloor()){				// if the mod is not on the floor
				if (mod.canPush){				// if the mod is not on the floor but can be pushed
					return mod.canMove(cmd);	// check if the mod can be moved with cmd
				}
				else {							// if mod is not on floor and cannot be moved (eg. wall)
					return false;				// return false
				}
			}
		}

		return true;
	}

	// Abstract Methods
	public abstract Modification makeCopy();
	public abstract String update(String cmd);
	
}
