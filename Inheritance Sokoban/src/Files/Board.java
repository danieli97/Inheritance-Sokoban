package Files;

import Mods.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

// Board Class
public class Board {

	// Attributes
	private Map<Integer, ArrayList<Location>> locs;			// Map coordinate to list of Locations at that coordinate
	private Map<Location, Modification> modLocs; 			// Map Location object to Modification
															// with that Location object
	private ArrayList<Modification> mods; 					// array list of mods
	private int width;										// width of the board
	private int height;										// height of the board
	private boolean isWon;									// true if game has been won

	// Constructor
	public Board(String fileName) {
		// check level file name
		if (fileName == null) {
			fileName = "level00.txt";
		}
		this.isWon = false;

		// instantiate data structures
		this.mods = new ArrayList<Modification>();
		this.locs = new HashMap<Integer, ArrayList<Location>>();
		this.modLocs = new HashMap<Location, Modification>();

		// Cloning

		// instantiate data structures and temporary Modifications to clone
		Map<String, Modification> clonables = new HashMap<String, Modification>();
		LinkedList<Modification> cloneTemplates = new LinkedList<Modification>();
		Location tempLoc = new Location(-1, -1);	// placeholder Location for each mod
		Player.board = this;						// set static Modification value for board to this board

		// initialize a single instance of each modification to be copied
		cloneTemplates.add(new Box(tempLoc));
		cloneTemplates.add(new Player(tempLoc));
		cloneTemplates.add(new Wall(tempLoc));
		cloneTemplates.add(new Storage(tempLoc));
		// cloneTemplates.add(new YOUR_MOD_NAME(tempLoc));

		// map each Modification letter to their respective instance
		for (Modification mod : cloneTemplates) {
			clonables.put(mod.letter, mod);
		}

		// create modifications
		try {
			ArrayList<String[]> levelArray = getLevelArray(fileName); 	// get level array
			this.generateClones(levelArray, clonables);					// generate clones
		} catch (IOException e) {
			System.out.println("Error loading level");
			e.printStackTrace();
		}
	}

	// Methods

	// Getters
	public ArrayList<Modification> getMods() {
		return this.mods;
	}

	public Map<Integer, ArrayList<Location>> getLocs() {
		return this.locs;
	}

	public Map<Location, Modification> getModLocs() {
		return this.modLocs;
	}

	public boolean getIsWon() {
		return this.isWon;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	// Setup
	private ArrayList<String[]> getLevelArray(String fileName) throws IOException {
		/*
		given a level file name, method reads through the file and extracts relevant
		information
		*/
		URL fileURL = Board.class.getClassLoader().getResource("Levels//" + fileName);			// get fileURL using level file name

		BufferedReader read = new BufferedReader(new InputStreamReader(fileURL.openStream()));	// open buffer reader for file

		ArrayList<String[]> levelArray = new ArrayList<String[]>();	// 2d array holding each character in level at coordinate
		LinkedList<String> lines = new LinkedList<String>(); 
		String line;

		this.width = 0;		// initialize width
		this.height = 0;	// initialize height
		while ((line = read.readLine()) != null) {	// for each line in the level
			if (line.length() > this.width) {		// if length of line is longer than the longest length found
				this.width = line.length();
			}
			this.height++;							// increase height count
			lines.add(line);						// add line to list of lines
		}
		read.close();		// close file

		for (String l : lines){									// for each line
			l = String.format("%1$-" + this.width + "s", l);	// pad right side up to board width with spaces
			levelArray.add(l.split(""));						// split line into array of single character strings and add to levelArray
		}

		return levelArray;
	}

	private void generateClones(ArrayList<String[]> levelArray, Map<String, Modification> clonables) {
		/*
		take level array and clonable modifications and generate respective clone at each location in level array
		*/

		for (int y = levelArray.size() - 1; y >= 0; y--) {									// for each x,y coordinate in the level (bottom left is 0,0)
			for (int x = 0; x < levelArray.get(y).length; x++) {
				this.locs.put(Modification.getCoord(x, y), new ArrayList<Location>());		// Map coordinate value to new array list
				if (clonables.get(levelArray.get(y)[x]) != null) {							// get letter from level array and check for clonable mod
					// create clone
					Location curLoc = new Location(x, y); 									// create Location instance at current location
					Modification newMod = clonables.get(levelArray.get(y)[x]).makeCopy(); 	// create copy of mod with
					newMod.initLoc(curLoc); 												// initialize mod location with instance of Location
					// add values to data
					this.mods.add(newMod); 													// add mod to list of mods
					this.modLocs.put(curLoc, newMod); 										// map Location to list of mods
					this.locs.get(this.width * y + x).add(curLoc); 							// put current coordiate and new Location
				} else if (!levelArray.get(y)[x].equals(" ")) {
					// letter in levelArray was not found in the modifications
					System.out.println("Could not resolve symbol: \"" + levelArray.get(y)[x] + "\" to a modification");
				}
			}
		}
	}

	// Functions
	public void changeLoc(int startCoord, int newCoord, Location loc) {
		/*
		changes the coordinate associated with a Location in HashMap locs
		*/
		this.locs.get(startCoord).remove(loc);
		this.locs.get(newCoord).add(loc);
	}

	protected boolean isSolved() {
		/*
		checks to see if there are any boxes not on storage
		returns true if the current state of the board is solved
		returns false if the state is unsolved
		*/
		for (Modification mod : this.mods) {
			if (mod.getTag().equals("Box") && !mod.isBoxStored()){
				return false;
			}
		}
		this.isWon = true;
		return true;
	}
	
	protected void notifyObservers(String cmd) {
		/*
		notifies all observers (modifications) of a command
		for each observer, execute update with command and get return
		if return is not null then notify observers again with new command
		*/
		LinkedList<String> newCmds = new LinkedList<String>();

		for (Modification obs : this.mods) {
			newCmds.add(obs.update(cmd));
		}

		for (String newCmd : newCmds){
			if (newCmd != null){
				this.notifyObservers(newCmd);
			}
		}

	}

}
