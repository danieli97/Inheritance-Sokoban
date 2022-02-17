package sokoban;

public class Box extends Ambiguous{

	/**
	 * Initialize a box with the specified location.
	 * 
	 * @param loc the location of this box
	 */
	public Box(Location loc) {
		this.loc = loc;
		this.name = "Box";
		this.floorObject = false;
		this.movable = true;
	}

	/**
	 * Move the box to the specified location, changing the box's location,
	 * only if the specified location is adjacent to the box's current location.
	 * If the specified location is not adjacent to the box's current location
	 * then the box's location does not change and false is returned.
	 * 
	 * @param to a location to move the box to
	 * @return true if the box moves to the specified location, false otherwise
	 */
	public boolean moveTo(Location to) {
		if (this.loc.isAdjacentTo(to)) {
			this.loc = to;
			return true;
		}
		return false;
	}

	/**
	 * Move the box to the adjacent location to the left, changing
	 * the box's location.
	 */
	public void moveLeft() {
		this.loc = this.loc.left();
	}

	/**
	 * Move the box to the adjacent location to the right, changing
	 * the box's location.
	 */
	public void moveRight() {
		this.loc = this.loc.right();
	}

	/**
	 * Move the box to the adjacent location above, changing
	 * the box's location.
	 */
	public void moveUp() {
		this.loc = this.loc.up();
	}

	/**
	 * Move the box to the adjacent location below, changing
	 * the box's location.
	 */
	public void moveDown() {
		this.loc = this.loc.down();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loc == null) ? 0 : loc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Box other = (Box) obj;
		if (loc == null) {
			if (other.loc != null)
				return false;
		} else if (!loc.equals(other.loc))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "box at " + this.loc;
	}

	@Override
	public void playerInteractWithThisObject() {
		// TODO Auto-generated method stub
		
	}
	
}
