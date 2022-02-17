package sokoban;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.rules.Timeout;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocationTest {

	@Rule
    public Timeout globalTimeout = Timeout.seconds(1);
	
	@Test
	public void test01_ctorNoArg() {
		Location loc = new Location();
		assertEquals(0, loc.x());
		assertEquals(0, loc.y());
	}

	@Test
	public void test02_ctor() {
		Location loc = new Location(99, -98);
		assertEquals(99, loc.x());
		assertEquals(-98, loc.y());
	}

	@Test
	public void test03_ctorCopy() {
		Location loc = new Location(99, -98);
		Location copy = new Location(loc);
		assertEquals(99, loc.x());
		assertEquals(-98, loc.y());
		
		assertEquals(99, copy.x());
		assertEquals(-98, copy.y());
	}


	
	@Test
	public void test04_right() {
		Location loc = new Location(99, -98);
		Location got = loc.right();
		assertEquals(100, got.x());
		assertEquals(-98, got.y());
	}

	@Test
	public void test05_up() {
		Location loc = new Location(99, -98);
		Location got = loc.up();
		assertEquals(99, got.x());
		assertEquals(-99, got.y());
	}

	@Test
	public void test06_down() {
		Location loc = new Location(99, -98);
		Location got = loc.down();
		assertEquals(99, got.x());
		assertEquals(-97, got.y());
	}

	@Test
	public void test07_isAdjacentTo() {
		Location loc = new Location(99, -98);
		
		Location[] X = {
				new Location(99, -99),
				new Location(99, -97),
				new Location(98, -98),
				new Location(100, -98)
		};
		for (Location x : X) {
			assertTrue(loc.isAdjacentTo(x));
		}
	}

	
	@Test
	public void test07_isNotAdjacentTo() {
		Location loc = new Location(99, -98);
		
		Location[] X = {
				new Location(98, -97),
				new Location(100, -97),
				new Location(98, -99),
				new Location(100, -99),
				new Location(99, -96),
				new Location(99, -100),
				new Location(97, -98),
				new Location(101, -98)
		};
		for (Location x : X) {
			assertFalse(loc.isAdjacentTo(x));
		}
	}
	
	@Test
	public void test08_equalsNull() {
		Location loc = new Location();
		assertNotEquals(loc, null);
	}
	
	@Test
	public void test08_equalsString() {
		Location loc = new Location();
		assertNotEquals(loc, "abc");
	}
	
	@Test
	public void test08_equalsSelf() {
		Location loc = new Location();
		assertEquals(loc, loc);
	}
	
	@Test
	public void test08_equals() {
		Location loc = new Location(1, 5);
		assertEquals(loc, new Location(1, 5));
	}
	
	@Test
	public void test08_notEquals() {
		Location loc = new Location(1, 5);
		Location[] X = {
				new Location(1, 4),
				new Location(1, 6),
				new Location(0, 5),
				new Location(2, 5)
		};
		for (Location x : X) {
			assertNotEquals(loc, x);
		}
	}

	@Test
	public void test09_hashCode() {
		Location loc = new Location(1, 5);
		Location eq = new Location(1, 5);
		assertEquals(loc.hashCode(), eq.hashCode());
	}
	
	@Test
	public void test09_hashCodeOther() {
		Location loc = new Location(1, 5);
		Location other = new Location(1, 6);
		assertNotEquals(loc.hashCode(), other.hashCode());
	}

	
	@Test
	public void testToString() {
		Location loc = new Location(-1, -5);
		assertEquals("[-1][-5]", loc.toString());
	}

}
