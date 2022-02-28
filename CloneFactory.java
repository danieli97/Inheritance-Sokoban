/* 
 * CloneFactory Class
 * 
 * Used to clone objects when creating the board for various levels
 */
public class CloneFactory {
	
	public Modification getClone(Modification modificationSample) {
		
		return modificationSample.makeCopy(); 
	}
	
}
