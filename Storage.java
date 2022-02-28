// Storage class
public class Storage extends Modification{
	
	// Attributes
	// Inherited from Parent Modification Class
	
	// Constructor
	public Storage(Location loc) {
		
		setLoc(loc);
		this.onFloor = true;
		
	}
	
	// Methods
	@Override
	public Modification makeCopy() {
		
		Storage storageObject = null;
		
		try {
			storageObject = (Storage) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return storageObject;
	}

	@Override
	public void update(Location playerLoc, Location newPlayerLoc) {
		
		// update image if a box is on top of storage -- this might be the GUI's job
		
	}
	
}
