package Files;

import java.util.*;

/* 
 * Observer Interface
 * 
 * Used as Observer for Observer Design Pattern
 * To be implemented by any child of the Modification class
 */
public interface Observer {

    public void update(String cmd, Map<int[], ArrayList<Location>> locs, Map<Location, ArrayList<Modification>> modLocs);
	
}