package sokoban;

/**
 * A class that represents the location of a storage spot in the
 * game Sokoban.
 */
public class Storage implements Ambiguous {

	/**
	 * Initialize a storage spot with the specified location.
	 * 
	 * @param loc the location of this storage spot
	 */
	public Storage(Location loc) {
		this.loc = loc;
		this.iconFile = "Storage";
		this.movable = false;
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
		Storage other = (Storage) obj;
		if (loc == null) {
			if (other.loc != null)
				return false;
		} else if (!loc.equals(other.loc))
			return false;
		return true;
	}

	@Override
	public void playerInteractWithThisObject() {
		// TODO Auto-generated method stub
		
	}

}
