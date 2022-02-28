import java.util.*;

// Board Class
public class Board implements Subject {
	
	// Attributes
	private ArrayList<Observer> observers;
	private Map<Modification, Location> modLocs;
	
	// Constructors
	// Default Constructor
	public Board() {
		
		observers = new ArrayList<Observer>();
	
	}
	
	// General Constructor
	public Board(String filename) {
		
		// readfile
		// run through file and create 
		observers = new ArrayList<Observer>();
		// create objects & add them to list
		// create observer list -
		
		System.out.print(filename);
	
	}
	
	// Methods
	
	public boolean isFree(Location loc) {
		// check location on Map if there is an object there with onFloor == true, return true else return false
		return true;
	}
	
	// Adds a new observer to the ArrayList
	@Override
	public void register(Observer newObserver) {
		
		observers.add(newObserver);
		
	}

	// Removes an existing observer from the ArrayList
	@Override
	public void unregister(Observer deleteObserver) {
		
		int observerIndex = observers.indexOf(deleteObserver);
		observers.remove(observerIndex);
		
	}

	// Notifies all registered observers found in the ArrayList that there was an update
	@Override
	public void notifyObserver(Location playerLoc, Location newPlayerLoc) {
		
		for (Observer obs : observers) {
			obs.update(playerLoc, newPlayerLoc);
		}
		
	}

}


