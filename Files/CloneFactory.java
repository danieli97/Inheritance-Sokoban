package Files;
import Mods.*;

/* 
 * CloneFactory Class
 * 
 * Used to clone objects when creating the board for various levels
 */
public class CloneFactory {
	
	// Clones Modification Objects
	public Modification getClone(Modification modificationSample) {
		
		return modificationSample.makeCopy(); 
		
	}
	
	// Clones Location Objects
	public Location getClone(Location locationSample) {
		
		return locationSample.makeCopy();
		
	}
}
