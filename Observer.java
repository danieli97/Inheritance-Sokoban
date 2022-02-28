/* 
 * Observer Interface
 * 
 * Used as Observer for Observer Design Pattern
 * To be implemented by any child of the Modification class
 */
public interface Observer {
	
	public void update(Location playerLoc, Location newPlayerLoc);
	
}
