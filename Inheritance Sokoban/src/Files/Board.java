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
	private Map<Integer, ArrayList<Location>> locs; // get location object from coordinate
	private Map<Location, ArrayList<Modification>> modLocs; // get mod from location object
	private ArrayList<Modification> mods; // list of mods
	private int width;
	private int height;

	// Constructor
	public Board(String fileName) {
		if (fileName == null) {
			fileName = "level00.txt";
		}

		this.mods = new ArrayList<Modification>();
		this.locs = new HashMap<Integer, ArrayList<Location>>();
		this.modLocs = new HashMap<Location, ArrayList<Modification>>();

		Map<String, Modification> clonables = new HashMap<String, Modification>();
		ArrayList<Modification> cloneTemplates = new ArrayList<Modification>();
		Location tempLoc = new Location(-1, -1);

		Player.board = this;
		cloneTemplates.add(new Box(tempLoc));
		cloneTemplates.add(new Player(tempLoc));
		cloneTemplates.add(new Wall(tempLoc));
		cloneTemplates.add(new Storage(tempLoc));
		// cloneTemplates.add(new {YOUR MOD NAME}(tempLoc));

		for (Modification mod : cloneTemplates) {
			clonables.put(mod.letter, mod);
		}

		// create modifications
		try {
			ArrayList<String[]> levelArray = getLevelArray(fileName); // get level array
			this.generateClones(levelArray, clonables);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

	public Map<Location, ArrayList<Modification>> getModLocs() {
		return this.modLocs;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	// Setup
	public ArrayList<String[]> getLevelArray(String fileName) throws IOException {
		/*
		given a level file name, method reads through the file and extracts relevant
		information
		*/

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

		this.width = widthCount;
		this.height = heightCount;

		for (int i = 0; i < levelArray.size(); i++) {
			if (levelArray.get(i).length < widthCount) {
				String[] newLine = new String[widthCount];
				for (int j = 0; j < levelArray.get(i).length; j++) {
					newLine[j] = levelArray.get(i)[j];
				}
				for (int k = levelArray.get(i).length; k < widthCount; k++) {
					newLine[k] = " ";
				}
				levelArray.set(i, newLine);
			}
		}

		return levelArray;

	}

	private void generateClones(ArrayList<String[]> levelArray, Map<String, Modification> clonables) {
		for (int y = levelArray.size() - 1; y >= 0; y--) {
			for (int x = 0; x < levelArray.get(y).length; x++) {
				this.locs.put(this.width * y + x, new ArrayList<Location>());
				if (clonables.get(levelArray.get(y)[x]) != null) {
					// create clone
					Location curLoc = new Location(x, y); // create Location instance at current location
					Modification newMod = clonables.get(levelArray.get(y)[x]).makeCopy(); // create copy of mod with
																							// letter found
					newMod.initLoc(curLoc); // initialize location with instance of Location
					// add values to data
					this.mods.add(newMod); // add mod to list of mods
					ArrayList<Modification> curLocMods = new ArrayList<Modification>(); // create arrayList of
																						// Modifications
					curLocMods.add(newMod); // add mod to arraylist
					this.modLocs.put(curLoc, curLocMods); // put Location and respective list of mod in modLocs
					this.locs.get(this.width * y + x).add(curLoc); // put current coordiate and new Location
				} else if (!levelArray.get(y)[x].equals(" ")) {
					// char in levelArray was not found in the modifications
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

	public boolean isSolved() {
		/*
		checks to see if there are any boxes not on storage
		returns true if the current state of the board is solved
		returns false if the state is unsolved
		*/
		for (Modification mod : this.mods) {
			if (mod.img == "Box") {

				return false;

			}
		}
		return true;
	}

	public void notifyObservers(String cmd) {
		/*
		notifies all observers (modifications) of a command
		for each observer, execute update with command and get return
		if return is not null then notify observers again with new command
		*/
		for (Modification obs : this.mods) {
			String newCmd = obs.update(cmd);
			if (newCmd != null) {
				this.notifyObservers(newCmd);
			}
		}

	}

}
