package Mods;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import Files.Location;
import Files.Modification;

public class Mod_Config {

    public static Map<String, Modification> getClonables(){
		Map<String, Modification> clonables = new HashMap<String, Modification>();
		LinkedList<Modification> cloneTemplates = new LinkedList<Modification>();
		Location tempLoc = new Location(-1, -1);	// placeholder Location for each mod
        
		// initialize a single instance of each modification to be copied
		cloneTemplates.add(new Box(tempLoc));
		cloneTemplates.add(new Player(tempLoc));
		cloneTemplates.add(new Wall(tempLoc));
		cloneTemplates.add(new Storage(tempLoc));
	    	cloneTemplates.add(new Ice(tempLoc));
	    	cloneTemplates.add(new PullBox(tempLoc));
		// cloneTemplates.add(new YOUR_MOD_NAME(tempLoc));

		// map each Modification letter to their respective instance
		for (Modification mod : cloneTemplates) {
			clonables.put(mod.getLetter(), mod);
		}

        return clonables;
    }

}
