package sokoban;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that represents a Sokoban level board.
 * 
 * <p>
 * The class can read a Sokoban level from a plain text file where the following
 * symbols are used:
 * 
 * <ul>
 * <li>space is an empty square
 * <li># is a wall
 * <li>@ is the player
 * <li>$ is a box
 * <li>. is a storage location
 * <li>+ is the player on a storage location
 * <li>* is a box on a storage location
 * </ul>
 * 
 * <p>
 * The class manages a single {@code Player} object and a number of {@code Box},
 * {@code Wall}, and {@code Storage} objects. The class determines whether the
 * player can move to a specified square depending on the configuration of boxes
 * and walls.
 * 
 * <p>
 * The level is won when every box is moved to a storage location.
 * 
 * <p>
 * The class provides several methods that return information about a location
 * on the board .
 *
 */
public class BoardSolution {
	private Map<Location, Wall> walls;
	private Map<Location, Box> boxes;
	private Map<Location, Storage> storage;
	private Player player;
	private int width;
	private int height;

	/**
	 * Initialize a board of width 11 and height 11 with a {@code Player} located at
	 * (4, 5), a {@code Box} located at (5, 5), and a storage location located at
	 * (6, 5).
	 */
	public BoardSolution() {
		this.walls = new HashMap<>();
		this.boxes = new HashMap<>();
		this.storage = new HashMap<>();
		this.player = new Player(new Location(4, 5));
		Box box = new Box(new Location(5, 5));
		this.boxes.put(box.location(), box);
		Storage storage = new Storage(new Location(6, 5));
		this.storage.put(storage.location(), storage);
		this.width = 11;
		this.height = 11;
	}

	/**
	 * Initialize a board by reading a level from the file with the specified
	 * filename.
	 * 
	 * <p>
	 * The height of the board is determined by the number of lines in the file. The
	 * width of the board is determined by the longest line in the file where
	 * trailing spaces in a line are ignored.
	 * 
	 * @param filename the filename of the level
	 * @throws IOException if the level file cannot be read
	 */
	public BoardSolution(String filename) throws IOException {
		this.walls = new HashMap<>();
		this.boxes = new HashMap<>();
		this.storage = new HashMap<>();
		this.readLevel(filename);
	}

	private final void readLevel(String filename) throws IOException {
		Path path = FileSystems.getDefault().getPath("src", "sokoban", filename);
		List<String> level = Files.readAllLines(path);
		this.height = level.size();
		this.width = 0;
		for (int y = 0; y < this.height; y++) {
			String row = level.get(y);
			if (row.length() > this.width) {
				this.width = row.length();
			}
			for (int x = 0; x < row.length(); x++) {
				Location loc = new Location(x, y);
				char c = row.charAt(x);
				if (c == ' ') {
					continue;
				} else if (c == '#') {
					Wall w = new Wall(loc);
					this.walls.put(loc, w);
				} else if (c == '@') {
					Player p = new Player(loc);
					this.player = p;
				} else if (c == '$') {
					Box box = new Box(loc);
					this.boxes.put(loc, box);
				} else if (c == '.') {
					Storage storage = new Storage(loc);
					this.storage.put(loc, storage);
				} else if (c == '+') {
					Player p = new Player(loc);
					this.player = p;
					Storage storage = new Storage(loc);
					this.storage.put(loc, storage);
				} else if (c == '*') {
					Box box = new Box(loc);
					this.boxes.put(loc, box);
					Storage storage = new Storage(loc);
					this.storage.put(loc, storage);
				}
			}
		}
	}

	/**
	 * Returns the width of this board.
	 * 
	 * @return the width of this board
	 */
	public int width() {
		return this.width;
	}

	/**
	 * Returns the height of this board.
	 * 
	 * @return the height of this board
	 */
	public int height() {
		return this.height;
	}

	/**
	 * Returns a list of the walls in this board. The order of the walls is
	 * unspecified in the returned list.
	 * 
	 * @return a list of the walls in this board
	 */
	public List<Wall> getWalls() {
		return new ArrayList<>(this.walls.values());
	}

	/**
	 * Returns a list of the boxes in this board. The order of the boxes is
	 * unspecified in the returned list.
	 * 
	 * @return a list of the boxes in this board
	 */
	public List<Box> getBoxes() {
		return new ArrayList<>(this.boxes.values());
	}

	/**
	 * Returns a list of the storage locations in this board. The order of the
	 * storage locations is unspecified in the returned list.
	 * 
	 * @return a list of the storage locations in this board
	 */
	public List<Storage> getStorage() {
		return new ArrayList<>(this.storage.values());
	}

	/**
	 * Returns the player.
	 * 
	 * @return the player
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Returns the {@code Box} object located at the specified location on the
	 * board, or {@code null} if there is no such object.
	 * 
	 * @param loc a location
	 * @return the box object located at the specified location on the board, or
	 *         {@code null} if there is no such object
	 */
	public Box getBox(Location loc) {
		return this.boxes.get(loc);
	}

	/**
	 * Returns {@code true} if there is a wall, player, or box at the specified
	 * location, {@code false} otherwise. Note that storage locations are considered
	 * unoccupied if there is no player or box on the location.
	 * 
	 * @param loc a location
	 * @return {@code true} if there is a wall, player, or box at the specified
	 *         location, {@code false} otherwise
	 */
	public boolean isOccupied(Location loc) {
		return this.walls.containsKey(loc) || this.boxes.containsKey(loc) || this.player.location().equals(loc);
	}

	/**
	 * Returns {@code true} if the specified location is unoccupied or has only a
	 * storage location, {@code false} otherwise
	 * 
	 * @param loc a location
	 * @return {@code true} if the specified location is unoccupied or has only a
	 *         storage location, {@code false} otherwise
	 */
	public boolean isFree(Location loc) {
		return !this.isOccupied(loc);
	}

	/**
	 * Returns {@code true} if the specified location has a wall on it,
	 * {@code false} otherwise.
	 * 
	 * @param loc a location
	 * @return {@code true} if the specified location has a wall on it,
	 *         {@code false} otherwise
	 */
	public boolean hasWall(Location loc) {
		return this.walls.containsKey(loc);
	}

	/**
	 * Returns {@code true} if the specified location has a box on it, {@code false}
	 * otherwise.
	 * 
	 * @param loc a location
	 * @return {@code true} if the specified location has a box on it, {@code false}
	 *         otherwise
	 */
	public boolean hasBox(Location loc) {
		return this.boxes.containsKey(loc);
	}

	/**
	 * Returns {@code true} if the specified location has a storage location on it,
	 * {@code false} otherwise.
	 * 
	 * @param loc a location
	 * @return {@code true} if the specified location has a storage location on it,
	 *         {@code false} otherwise
	 */
	public boolean hasStorage(Location loc) {
		return this.storage.containsKey(loc);
	}

	/**
	 * Returns {@code true} if the specified location has a player on it,
	 * {@code false} otherwise.
	 * 
	 * @param loc a location
	 * @return {@code true} if the specified location has a player on it,
	 *         {@code false} otherwise
	 */
	public boolean hasPlayer(Location loc) {
		return this.player.location().equals(loc);
	}

	/**
	 * Returns {@code true} if every storage location has a box on it, {@code false}
	 * otherwise.
	 * 
	 * @return {@code true} if every storage location has a box on it, {@code false}
	 *         otherwise
	 */
	public boolean isSolved() {
		for (Storage s : this.storage.values()) {
			if (!this.hasBox(s.location())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Moves a box from a specified location to a specified location. Assumes that
	 * there is a box at the specified starting location and that there is no box at
	 * the specified destination location
	 * 
	 * @param from the starting location
	 * @param to   the destination location
	 */
	private void moveBoxTo(Location from, Location to) {
		Box box = this.boxes.remove(from);
		box.moveTo(to);
		this.boxes.put(to, box);
	}

	/**
	 * Moves the player to the specified adjacent position if possible. If there is
	 * a box in the specified adjacent location then the box is pushed to the next
	 * adjacent location.
	 * 
	 * <p>
	 * Returns {@code false} if the player cannot move to the adjacent location
	 * (leaving the player location unchanged).
	 * 
	 * <p>
	 * This method assumes that {@code adj} is actually adjacent to the player. Also
	 * assumes that {@code adjNext} is adjacent to {@code adj} and is in the same
	 * direction relative to the player as {@code adj}.
	 * 
	 * @param adj     a location adjacent to the player
	 * @param adjNext the location two positions away from the player in the same
	 *                direction as {@code adj}
	 * @return true if the player is moved, false otherwise
	 */
	private boolean movePlayerTo(Location adj, Location adjNext) {
		if (this.isFree(adj)) {
			this.player.moveTo(adj);
			return true;
		} else if (this.hasBox(adj) && this.isFree(adjNext)) {
			this.player.moveTo(adj);
			this.moveBoxTo(adj, adjNext);
			return true;
		}
		return false;
	}

	/**
	 * Moves the player to the left adjacent location if possible. If there is a box
	 * in the left adjacent location then the box is pushed to the adjacent location
	 * left of the box.
	 * 
	 * <p>
	 * Returns {@code false} if the player cannot move to the left adjacent location
	 * (leaving the player location unchanged).
	 * 
	 * @return true if the player is moved to the left adjacent location, false
	 *         otherwise
	 */
	public boolean movePlayerLeft() {
		Location left = this.player.location().left();
		return this.movePlayerTo(left, left.left());
	}

	/**
	 * Moves the player to the right adjacent location if possible. If there is a
	 * box in the right adjacent location then the box is pushed to the adjacent
	 * location right of the box.
	 * 
	 * <p>
	 * Returns {@code false} if the player cannot move to the right adjacent
	 * location (leaving the player location unchanged).
	 * 
	 * @return true if the player is moved to the right adjacent location, false
	 *         otherwise
	 */
	public boolean movePlayerRight() {
		Location right = this.player.location().right();
		return this.movePlayerTo(right, right.right());
	}

	/**
	 * Moves the player to the above adjacent location if possible. If there is a
	 * box in the above adjacent location then the box is pushed to the adjacent
	 * location above the box.
	 * 
	 * <p>
	 * Returns {@code false} if the player cannot move to the above adjacent
	 * location (leaving the player location unchanged).
	 * 
	 * @return true if the player is moved to the above adjacent location, false
	 *         otherwise
	 */
	public boolean movePlayerUp() {
		Location up = this.player.location().up();
		return this.movePlayerTo(up, up.up());
	}

	/**
	 * Moves the player to the below adjacent location if possible. If there is a
	 * box in the below adjacent location then the box is pushed to the adjacent
	 * location below of the box.
	 * 
	 * <p>
	 * Returns {@code false} if the player cannot move to the below adjacent
	 * location (leaving the player location unchanged).
	 * 
	 * @return true if the player is moved to the below adjacent location, false
	 *         otherwise
	 */
	public boolean movePlayerDown() {
		Location down = this.player.location().down();
		return this.movePlayerTo(down, down.down());
	}
	
	
	/**
	 * Returns a string representation of this board. The string representation
	 * is identical to file format describing Sokoban levels.
	 * 
	 * @return a string representation of this board
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				Location loc = new Location(x, y);
				if (this.isFree(loc) ) {
					if (this.hasStorage(loc)) {
						b.append(".");					
					}
					else {
						b.append(" ");
					}
				}
				else if (this.hasWall(loc)) {
					b.append("#");
				}
				else if (this.hasBox(loc)) {
					if (this.hasStorage(loc)) {
						b.append("*");
					}
					else {
						b.append("$");
					}
				}
				else if (this.hasPlayer(loc)) {
					if (this.hasStorage(loc)) {
						b.append("+");
					}
					else {
						b.append("@");
					}
				}
			}
			b.append('\n');
		}
		return b.toString();
	}
	
	public static void main(String[] args) throws IOException {
		BoardSolution b = new BoardSolution();
		System.out.println(b);
	}
}
