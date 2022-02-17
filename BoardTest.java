package sokoban;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.rules.Timeout;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BoardTest {

	@Rule
    public Timeout globalTimeout = Timeout.seconds(1);

	@Test
	public void test01_ctor() {
		Board b = new Board();

		// dimensions
		assertEquals("Board(): wrong width", 
				11, b.width());
		assertEquals("Board(): wrong height", 
				11, b.height());

		// player
		Player p = b.getPlayer();
		assertNotNull("Board() getPlayer() returns null", 
				p);
		assertEquals("Board() player has wrong location", 
				new Location(4, 5), p.location());

		// boxes
		List<Box> boxes = b.getBoxes();
		assertNotNull("Board() getBoxes() returns null", 
				boxes);
		assertEquals("Board() getBoxes() has wrong size", 
				1, boxes.size());
		assertEquals("Board() box has wrong location", 
				new Location(5, 5), boxes.get(0).location());

		// storage
		List<Storage> sto = b.getStorage();
		assertNotNull("Board() getStorage() returns null", 
				sto);
		assertEquals("Board() getStorage() has wrong size", 
				1, sto.size());
		assertEquals("Board() storage has wrong location", 
				new Location(6, 5), sto.get(0).location());
	}

	@Test
	public void test02_ctor() {
		try {
			BoardSolution sol = new BoardSolution("level08.txt");
			Board b = new Board("level08.txt");
			
			// dimensions
			assertEquals("Board(\"level08.txt\"): wrong width", 
					sol.width(), b.width());
			assertEquals("Board(\"level08.txt\"): wrong height", 
					sol.height(), b.height());

			// player
			Player p = b.getPlayer();
			assertNotNull("Board(\"level08.txt\") getPlayer() returns null", 
					p);
			assertEquals("Board(\"level08.txt\") player has wrong location", 
					sol.getPlayer().location(), p.location());

			// boxes
			List<Box> boxes = b.getBoxes();
			List<Box> solBoxes = sol.getBoxes();
			assertNotNull("Board(\"level08.txt\") getBoxes() returns null", 
					boxes);
			assertEquals("Board(\"level08.txt\") getBoxes() has wrong size", 
					solBoxes.size(), boxes.size());
			assertTrue("Board(\"level08.txt\") box has wrong location", 
					boxes.containsAll(solBoxes));

			// storage
			List<Storage> sto = b.getStorage();
			List<Storage> solSto = sol.getStorage();
			assertNotNull("Board(\"level08.txt\") getStorage() returns null", 
					sto);
			assertEquals("Board(\"level08.txt\") getStorage() has wrong size", 
					solSto.size(), sto.size());
			assertTrue("Board(\"level08.txt\") storage has wrong location", 
					sto.containsAll(solSto));
		}
		catch (IOException x) {
			fail("file level08.txt is missing");
		}
		catch (Exception x) {
			throw x;
		}
	}

	@Test
	public void test03_getBox() {
		Board b = new Board();
		Box got = b.getBox(new Location(5, 5));
		assertEquals("getBox() box has wrong location", 
				new Location(5, 5), got.location());
		assertNull("getBox() returned a non-existant box",
				b.getBox(new Location(1, 1)));
	}

	@Test
	public void test04_isOccupied1() {
		Board b = new Board();
		assertTrue("isOccupied() returned false for square with player",
				b.isOccupied(new Location(4, 5)));
		assertTrue("isOccupied() returned false for square with box",
				b.isOccupied(new Location(5, 5)));
		
		assertFalse("isOccupied() returned true for square with storage location",
				b.isOccupied(new Location(6, 5)));
		assertFalse("isOccupied() returned true for an empty square",
				b.isOccupied(new Location(1, 1)));
	}
	
	@Test
	public void test04_isOccupied2() {
		try {
			Board b = new Board("level08.txt");
			BoardSolution sol = new BoardSolution("level08.txt");
			for (int x = 0; x < sol.width(); x++) {
				for (int y = 0; y < sol.height(); y++) {
					Location loc = new Location(x, y);
					assertEquals("isOccupied() returned different value than solution", 
							sol.isOccupied(loc), b.isOccupied(loc));
				}
			}
		}
		catch (IOException x) {
			fail("file level08.txt is missing");
		}
		catch (Exception x) {
			throw x;
		}
	}

	@Test
	public void test04_isFree1() {
		Board b = new Board();
		assertFalse("isFree() returned true for square with player",
				b.isFree(new Location(4, 5)));
		assertFalse("isFree() returned false for square with box",
				b.isFree(new Location(5, 5)));
		
		assertTrue("isFree() returned false for square with storage location",
				b.isFree(new Location(6, 5)));
		assertTrue("isFree() returned false for an empty square",
				b.isFree(new Location(1, 1)));
	}
	
	@Test
	public void test05_isFree2() {
		try {
			Board b = new Board("level08.txt");
			BoardSolution sol = new BoardSolution("level08.txt");
			for (int x = 0; x < sol.width(); x++) {
				for (int y = 0; y < sol.height(); y++) {
					Location loc = new Location(x, y);
					assertEquals("isFree() returned different value than solution", 
							sol.isFree(loc), b.isFree(loc));
				}
			}
		}
		catch (IOException x) {
			fail("file level08.txt is missing");
		}
		catch (Exception x) {
			throw x;
		}
	}

	@Test
	public void test06_hasWall1() {
		Board b = new Board();
		for (int x = 0; x < 11; x++) {
			for (int y = 0; y < 11; y++) {
				Location loc = new Location(x, y);
				assertFalse("hasWall() returned true for board with no walls", 
						b.hasWall(loc));
			}
		}
	}
	
	@Test
	public void test06_hasWall2() {
		try {
			Board b = new Board("level08.txt");
			BoardSolution sol = new BoardSolution("level08.txt");
			for (int x = 0; x < sol.width(); x++) {
				for (int y = 0; y < sol.height(); y++) {
					Location loc = new Location(x, y);
					assertEquals("hasWall() returned different value than solution", 
							sol.hasWall(loc), b.hasWall(loc));
				}
			}
		}
		catch (IOException x) {
			fail("file level08.txt is missing");
		}
		catch (Exception x) {
			throw x;
		}
	}

	@Test
	public void test07_hasBox1() {
		Board b = new Board();
		for (int x = 0; x < 11; x++) {
			for (int y = 0; y < 11; y++) {
				if (x == 5 && y == 5) {
					continue;
				}
				Location loc = new Location(x, y);
				assertFalse("hasBox() returned true for square with no box", 
						b.hasBox(loc));
			}
		}
	}
	
	@Test
	public void test07_hasBox2() {
		try {
			Board b = new Board("level08.txt");
			BoardSolution sol = new BoardSolution("level08.txt");
			for (int x = 0; x < sol.width(); x++) {
				for (int y = 0; y < sol.height(); y++) {
					Location loc = new Location(x, y);
					assertEquals("hasBox() returned different value than solution", 
							sol.hasBox(loc), b.hasBox(loc));
				}
			}
		}
		catch (IOException x) {
			fail("file level08.txt is missing");
		}
		catch (Exception x) {
			throw x;
		}
	}

	@Test
	public void test08_hasStorage1() {
		Board b = new Board();
		for (int x = 0; x < 11; x++) {
			for (int y = 0; y < 11; y++) {
				if (x == 6 && y == 5) {
					continue;
				}
				Location loc = new Location(x, y);
				assertFalse("hasStorage() returned true for square with no storage", 
						b.hasStorage(loc));
			}
		}
	}
	
	@Test
	public void test08_hasStorage2() {
		try {
			Board b = new Board("level08.txt");
			BoardSolution sol = new BoardSolution("level08.txt");
			for (int x = 0; x < sol.width(); x++) {
				for (int y = 0; y < sol.height(); y++) {
					Location loc = new Location(x, y);
					assertEquals("hasStorage() returned different value than solution", 
							sol.hasStorage(loc), b.hasStorage(loc));
				}
			}
		}
		catch (IOException x) {
			fail("file level08.txt is missing");
		}
		catch (Exception x) {
			throw x;
		}
	}

	@Test
	public void test09_hasPlayer1() {
		Board b = new Board();
		for (int x = 0; x < 11; x++) {
			for (int y = 0; y < 11; y++) {
				if (x == 4 && y == 5) {
					continue;
				}
				Location loc = new Location(x, y);
				assertFalse("hasPlayer() returned true for square with no player", 
						b.hasPlayer(loc));
			}
		}
	}
	
	@Test
	public void test09_hasPlayer2() {
		try {
			Board b = new Board("level08.txt");
			BoardSolution sol = new BoardSolution("level08.txt");
			for (int x = 0; x < sol.width(); x++) {
				for (int y = 0; y < sol.height(); y++) {
					Location loc = new Location(x, y);
					assertEquals("hasPlayer() returned different value than solution", 
							sol.hasPlayer(loc), b.hasPlayer(loc));
				}
			}
		}
		catch (IOException x) {
			fail("file level08.txt is missing");
		}
		catch (Exception x) {
			throw x;
		}
	}

	@Test
	public void test10_isSolved() {
		Board b = new Board();
		assertFalse("isSolved() returned true for an unsolved board",
				b.isSolved());
	}
	
	@Test
	public void test10_isSolved2() {
		try {
			Board b = new Board("solved1.txt");
			assertTrue("isSolved() returned false for a solved board",
					b.isSolved());
		}
		catch (IOException x) {
			fail("file solved1.txt is missing");
		}
		catch (Exception x) {
			throw x;
		}
	}

	@Test
	public void test11_movePlayerLeft1() {
		Board b = new Board();
		b.movePlayerLeft();
		Player got = b.getPlayer();
		assertEquals("movePlayerLeft() returned wrong location", 
				new Location(3, 5), got.location());
		
		b.movePlayerLeft();
		got = b.getPlayer();
		assertEquals("movePlayerLeft() returned wrong location", 
				new Location(2, 5), got.location());
	}
	
	@Test
	public void test11_movePlayerLeft2() {
		try {
			// player and box should move
			Board b = new Board("move_left.txt");
			b.movePlayerLeft();
			Player got = b.getPlayer();
			assertEquals("movePlayerLeft() returned wrong location", 
					new Location(2, 2), got.location());
			Box box = b.getBoxes().get(0);
			assertEquals("movePlayerLeft() did not move the box", 
					new Location(1, 2), box.location());

			// player and box should not move (because of a wall)
			b.movePlayerLeft();
			got = b.getPlayer();
			assertEquals("movePlayerLeft() player should not have moved", 
					new Location(2, 2), got.location());
			box = b.getBoxes().get(0);
			assertEquals("movePlayerLeft() box should not have moved", 
					new Location(1, 2), box.location());
		} 
		catch (IOException x) {
			fail("file move_left.txt is missing");
		} 
		catch (Exception x) {
			throw x;
		}
	}
	
	@Test
	public void test11_movePlayerLeft3() {
		try {
			// player and box should not move
			Board b = new Board("move_left2.txt");
			b.movePlayerLeft();
			Player got = b.getPlayer();
			assertEquals("movePlayerLeft() returned wrong location", 
					new Location(3, 2), got.location());
			
			// boxes
			List<Box> exp = new ArrayList<>();
			exp.add(new Box(new Location(1, 2)));
			exp.add(new Box(new Location(2, 2)));
			List<Box> boxes = b.getBoxes();
			assertEquals("movePlayerLeft() wrong number of boxes",
					2, boxes.size());
			assertTrue("movePlayerLeft() box locations are incorrect", 
					boxes.containsAll(exp));
		} 
		catch (IOException x) {
			fail("file move_left2.txt is missing");
		} 
		catch (Exception x) {
			throw x;
		}
	}

	@Test
	public void test12_movePlayerRight1() {
		Board b = new Board();
		b.movePlayerRight();
		Player got = b.getPlayer();
		assertEquals("movePlayerRight() returned wrong location", 
				new Location(5, 5), got.location());
		
		b.movePlayerRight();
		got = b.getPlayer();
		assertEquals("movePlayerRight() returned wrong location", 
				new Location(6, 5), got.location());
	}
	
	@Test
	public void test11_movePlayerRight2() {
		try {
			// player and box should move
			Board b = new Board("move_right.txt");
			b.movePlayerRight();
			Player got = b.getPlayer();
			assertEquals("movePlayerRight() returned wrong location", 
					new Location(2, 2), got.location());
			Box box = b.getBoxes().get(0);
			assertEquals("movePlayerRight() did not move the box", 
					new Location(3, 2), box.location());

			// player and box should not move (because of a wall)
			b.movePlayerRight();
			got = b.getPlayer();
			assertEquals("movePlayerRight() player should not have moved", 
					new Location(2, 2), got.location());
			box = b.getBoxes().get(0);
			assertEquals("movePlayerRight() box should not have moved", 
					new Location(3, 2), box.location());
		} 
		catch (IOException x) {
			fail("file move_right.txt is missing");
		} 
		catch (Exception x) {
			throw x;
		}
	}
	
	@Test
	public void test11_movePlayerRight3() {
		try {
			// player and box should not move
			Board b = new Board("move_right2.txt");
			b.movePlayerRight();
			Player got = b.getPlayer();
			assertEquals("movePlayerRight() returned wrong location", 
					new Location(1, 2), got.location());
			
			// boxes
			List<Box> exp = new ArrayList<>();
			exp.add(new Box(new Location(2, 2)));
			exp.add(new Box(new Location(3, 2)));
			List<Box> boxes = b.getBoxes();
			assertEquals("movePlayerRight() wrong number of boxes",
					2, boxes.size());
			assertTrue("movePlayerRight() box locations are incorrect", 
					boxes.containsAll(exp));
 		} 
		catch (IOException x) {
			fail("file move_right2.txt is missing");
		} 
		catch (Exception x) {
			throw x;
		}
	}

	@Test
	public void test13_movePlayerUp1() {
		Board b = new Board();
		b.movePlayerUp();
		Player got = b.getPlayer();
		assertEquals("movePlayerUp() returned wrong location", 
				new Location(4, 4), got.location());
		
		b.movePlayerUp();
		got = b.getPlayer();
		assertEquals("movePlayerUp() returned wrong location", 
				new Location(4, 3), got.location());
	}
	
	@Test
	public void test11_movePlayerUp2() {
		try {
			// player and box should move
			Board b = new Board("move_up.txt");
			b.movePlayerUp();
			Player got = b.getPlayer();
			assertEquals("movePlayerUp() returned wrong location", 
					new Location(2, 2), got.location());
			Box box = b.getBoxes().get(0);
			assertEquals("movePlayerUp() did not move the box", 
					new Location(2, 1), box.location());

			// player and box should not move (because of a wall)
			b.movePlayerUp();
			got = b.getPlayer();
			assertEquals("movePlayerUp() player should not have moved", 
					new Location(2, 2), got.location());
			box = b.getBoxes().get(0);
			assertEquals("movePlayerUp() box should not have moved", 
					new Location(2, 1), box.location());
		} 
		catch (IOException x) {
			fail("file move_up.txt is missing");
		} 
		catch (Exception x) {
			throw x;
		}
	}
	
	@Test
	public void test11_movePlayerUp3() {
		try {
			// player and box should move
			Board b = new Board("move_up2.txt");
			b.movePlayerUp();
			Player got = b.getPlayer();
			assertEquals("movePlayerUp() returned wrong location", 
					new Location(2, 3), got.location());
			Box box = b.getBoxes().get(0);
			assertEquals("movePlayerUp() did not move the box", 
					new Location(2, 2), box.location());
			
			// boxes
			List<Box> exp = new ArrayList<>();
			exp.add(new Box(new Location(2, 2)));
			exp.add(new Box(new Location(2, 1)));
			List<Box> boxes = b.getBoxes();
			assertEquals("movePlayerUp() wrong number of boxes", 
					2, boxes.size());
			assertTrue("movePlayerUp() box locations are incorrect", 
					boxes.containsAll(exp));
		} 
		catch (IOException x) {
			fail("file move_up2.txt is missing");
		} 
		catch (Exception x) {
			throw x;
		}
	}

	@Test
	public void test_movePlayerDown1() {
		Board b = new Board();
		b.movePlayerDown();
		Player got = b.getPlayer();
		assertEquals("movePlayerDown() returned wrong location", 
				new Location(4, 6), got.location());
		
		b.movePlayerDown();
		got = b.getPlayer();
		assertEquals("movePlayerDown() returned wrong location", 
				new Location(4, 7), got.location());
	}

	@Test
	public void test11_movePlayerDown2() {
		try {
			// player and box should move
			Board b = new Board("move_down.txt");
			b.movePlayerDown();
			Player got = b.getPlayer();
			assertEquals("movePlayerDown() returned wrong location", 
					new Location(2, 2), got.location());
			Box box = b.getBoxes().get(0);
			assertEquals("movePlayerDown() did not move the box", 
					new Location(2, 3), box.location());

			// player and box should not move (because of a wall)
			b.movePlayerDown();
			got = b.getPlayer();
			assertEquals("movePlayerDown() player should not have moved", 
					new Location(2, 2), got.location());
			box = b.getBoxes().get(0);
			assertEquals("movePlayerDown() box should not have moved", 
					new Location(2, 3), box.location());
		} 
		catch (IOException x) {
			fail("file move_down.txt is missing");
		} 
		catch (Exception x) {
			throw x;
		}
	}
	
	@Test
	public void test11_movePlayerDown3() {
		try {
			// player and box should move
			Board b = new Board("move_down2.txt");
			b.movePlayerDown();
			Player got = b.getPlayer();
			assertEquals("movePlayerDown() returned wrong location", 
					new Location(2, 1), got.location());
			// boxes
			List<Box> exp = new ArrayList<>();
			exp.add(new Box(new Location(2, 2)));
			exp.add(new Box(new Location(2, 3)));
			List<Box> boxes = b.getBoxes();
			assertEquals("movePlayerDown() wrong number of boxes", 
					2, boxes.size());
			assertTrue("movePlayerDown() box locations are incorrect", 
					boxes.containsAll(exp));
		} 
		catch (IOException x) {
			fail("file move_down2.txt is missing");
		} 
		catch (Exception x) {
			throw x;
		}
	}
}
