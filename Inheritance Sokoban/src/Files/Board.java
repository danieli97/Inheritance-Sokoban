package Files;

import Mods.*;
//import Levels.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

// Board Class
public class Board implements Subject {
	
	// Attributes
	private ArrayList<Observer> observers;
	private Map<Modification, Location> modLocs;
	private int width;
	private int height;
	
	
	// Constructors
	// Default Constructor
	public Board() {
		
		String fileName = "level00.txt";
		
		try {
			String[] levelArray = getLevelArray(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// use level array to get the locations of all the objects
		// make sure 
		
		this.observers = new ArrayList<Observer>();
		// registerObservers() -> for mod in mods, this.observers.register(mod)
		
		// replace this with this.readFile(defaultFile);
	
	}
	
	// General Constructor
	public Board(String fileName) {
		
		// read file
		// run through file and create a list of objects in the file
		
		// create x instances of each object and add them to array list
		
		this.observers = new ArrayList<Observer>();
		// create objects & add them to list
		// create observer list 
	
	}
	
	// Methods
	
	// given a level file name, method reads through the file and extracts relevant information
	public String[] getLevelArray(String fileName) throws IOException {
		
		URL fileURL = Board.class.getClassLoader().getResource("Levels//" + fileName);
		
		BufferedReader read = new BufferedReader(new InputStreamReader(fileURL.openStream()));
		String line;
		
		int heightCount = 0;
		int longestWidth = 0;
		
		while ((line = read.readLine()) != null) {
			if (line.length() > longestWidth) {
				longestWidth = line.length();
			}
			heightCount++;
		}
		read.close();
		
		this.setWidth(longestWidth);
		this.setHeight(heightCount);
		
		String[] levelArray = new String [heightCount];
		int idx = 0;
		
		while ((line = read.readLine()) != null) {
			levelArray[idx] = line;
		}
		
		return levelArray;
		
	}
	
	// gets the width of the board
	public int getWidth() {
		return this.width;
	}
	
	// gets the height of the board
	public int getHeight() {
		return this.height;
	}
	
	// Sets the board width to the appropriate width based on the level file
	public void setWidth(int width) {
		this.width = width;
	}
	
	// Sets the board height to the appropriate height based on the level file
	public void setHeight(int height) {
		this.height = height;
	}
	
	// checks if the object at a given location is on the floor
	public boolean hasFloorObject(Location loc){
		
		// checks if the object at a given location is a floor object
		
		return true;
	}
	
	// checks if the current state of the board is solved
	public boolean isSolved() {
		// return true if all boxes are on storages, and return false if not
		return true;
	}
	
	// checks if a given location is free
	public boolean isFree(Location loc) {
		// check location on Map if there is an object there with onFloor == true, return true else return false
		// if the object is a box, checks if "loc + 1" is free
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
	public boolean notifyObserver(Location playerLoc, Location newPlayerLoc) {
		
		for (Observer obs : observers) {
			obs.update(playerLoc, newPlayerLoc);
		}
		
		return true;
		
	}

}


