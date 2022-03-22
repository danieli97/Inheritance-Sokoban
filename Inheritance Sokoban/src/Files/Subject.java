package Files;

/* 
 * Subject Interface
 * 
 * Used as Subject of Observer Design Pattern
 * To be impleented by the Board class
 */

public interface Subject {
	
	public void register(Observer obs);
	public void unregister(Observer obs);
	public boolean notifyObserver(Location playerLoc, Location newPlayerLoc);
	
}