package Files;

import Mods.*;
import Levels.*;

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
	// this might be able to be an array/arraylist
	private Map<Integer, ArrayList<Location>> locs;	// get location object from coordinate
	private Map<Location, ArrayList<Modification>> modLocs;	// get mod from location object
	private ArrayList<Modification> mods;					// list of mods
	private int width;
	private int height;
	
	// General Constructor
	public Board(String fileName) {
		if (fileName == null){
			fileName = "level00.txt";
		}

		this.mods = new ArrayList<Modification>();
		this.locs = new HashMap<Integer, ArrayList<Location>>();
		this.modLocs = new HashMap<Location, ArrayList<Modification>>();
		this.observers = new ArrayList<Observer>();

		Map<String, Modification> clonables = new HashMap<String, Modification>();
		Location tempLoc = new Location(-1,-1);
		// instantiate one of each mod to be cloned	STUDENT ADDS THEIR MODS
		clonables.put(Box.letter, new Box(tempLoc));
		clonables.put(Player.letter, new Player(tempLoc));
		clonables.put(Storage.letter, new Storage(tempLoc));
		clonables.put(Wall.letter, new Wall(tempLoc));

        //	create modifications
		try {
			ArrayList<String[]> levelArray = getLevelArray(fileName);	// get level array
			this.generateClones(levelArray, clonables);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error loading level");
			e.printStackTrace();
		}

		this.registerObservers();	
	}
	
	// Methods
	private void generateClones(ArrayList<String[]> levelArray, Map<String, Modification> clonables){
		for (int y=levelArray.size()-1; y>=0; y--){
			for (int x=0; x<levelArray.get(y).length; x++){
				this.locs.put(this.width * y + x, new ArrayList<Location>());
				if (clonables.get(levelArray.get(y)[x]) != null){
					Location curLoc = new Location(x,y);	// create Location instance at current location
					Modification newMod = clonables.get(levelArray.get(y)[x]).makeCopy();	// create copy of mod with letter found
					newMod.initLoc(curLoc);					// initialize location with instance of Location
					this.mods.add(newMod);					// add mod to list of mods
					ArrayList<Modification> curLocMods = new ArrayList<Modification>();	// create arrayList of Modifications
					curLocMods.add(newMod);					// add mod to arraylist
					this.modLocs.put(curLoc, curLocMods);	// put Location and respective list of mod in modLocs
					this.locs.get(this.width * y + x).add(curLoc);	// put current coordiate and new Location
				} else if (!levelArray.get(y)[x].equals(" ")) {
					// char in levelArray was not found in the modifications
					System.out.println("Could not resolve char: \"" + levelArray.get(y)[x] + "\" to a modification");
				}
			}
		}
		// delete all initial mods from map and tempLoc??
		// mods = null;
		// tempLoc = null;
	}
	
	// given a level file name, method reads through the file and extracts relevant information
	public ArrayList<String[]> getLevelArray(String fileName) throws IOException {
		
		URL fileURL = Board.class.getClassLoader().getResource("Levels//" + fileName);
		
		BufferedReader read = new BufferedReader(new InputStreamReader(fileURL.openStream()));
		String line;
		
		int heightCount = 0;
		int widthCount = 0;
		ArrayList<String[]> levelArray = new ArrayList<String[]>();
		
		while ((line = read.readLine()) != null) {
			if (line.length() > widthCount) {
				widthCount = line.length();
			}
			heightCount++;
			levelArray.add(line.split("(?!^)"));
		}
		read.close();
		
		this.setWidth(widthCount);
		this.setHeight(heightCount);

		return levelArray;
		
	}
	
	// registers all the observers from the mods in modsList
	public void registerObservers() {
		
		for (Modification mod : this.mods) {
			this.register(mod);
		}
		
	}

	// public void updateLocs() {

	// 	for (int y : this.locs.keySet()){
	// 		for (int x : this.locs.get(y).keySet())
	// 			for (Location loc : this.locs.get(x).get(y)){
	// 				if (x != loc.getX() || y != loc.getY()){
	// 					this.locs.get(loc.getX()).get(loc.getY()).add(loc);
	// 					this.locs.get(x).get(y).remove(loc);
	// 				}
	// 			}
	// 	}

	// }
	
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
	
	
	public Modification getPlayer() {
		
		for (Modification mod : this.mods) {
			if (mod.letter.equals("P")) {
				return mod;
			}
		}
		
		return null;
		
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
		
		this.observers.add(newObserver);
		
	}

	// Removes an existing observer from the ArrayList
	@Override
	public void unregister(Observer deleteObserver) {
		
		int observerIndex = this.observers.indexOf(deleteObserver);
		this.observers.remove(observerIndex);
		
	}

	// Notifies all registered observers found in the ArrayList that there was an update
	@Override
	public boolean notifyObservers(String cmd) {
		
		for (Observer obs : this.observers) {
			String newCmd = obs.update(cmd, locs, modLocs);
			if (newCmd != null){
				this.notifyObservers(cmd);
			}
		}
		
		return true;
	
	}

}


