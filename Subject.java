/* 
 * Subject Interface
 * 
 * Used as Subject of Observer Design Pattern
 * To be implemented by the Board class
 */

public interface Subject {
	
	public void register(Observer obs);
	public void unregister(Observer obs);
	public void notifyObserver(Location playerLoc, Location newPlayerLoc);
	
}
