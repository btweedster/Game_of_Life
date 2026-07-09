package life;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.junit.jupiter.api.Test;

class TestCoord {

	@Test
	void testCoord() {
		Coord fixture = new Coord(1L, 2L);
		assertEquals(
			1L,
			fixture.get_x(),
			"X should be 1L"
		);
		assertEquals(
			2L,
			fixture.get_y(),
			"Y should be 2L"
		);
		
	}
	
	@Test
	void testEquals() {
		Coord fixture1 = new Coord(1L, 2L);
		Coord fixture2 = new Coord(1L, 2L);
		Coord fixture3 = new Coord(1L, 1L);
		assertEquals(fixture1, fixture1, "Fixture 1 and Fixture 1 should be equal");
		assertEquals(fixture1, fixture2, "Fixture 1 and Fixture 2 should be equal");
		assertNotEquals(fixture1, fixture3, "Fixture 1 and Fixture 3 should not be equal");
		assertNotEquals(fixture2, fixture3, "Fixture 2 and Fixture 3 should not be equal");
		assertNotEquals(null, fixture1, "Fixture 1 is not null");
		assertNotEquals(null, fixture2, "Fixture 2 is not null");
		assertNotEquals(null, fixture3, "Fixture 3 is not null");
	}
	
	@Test
	void testHashCode() {
		Coord fixture = new Coord(1L, 1L);
		assertEquals(
			Objects.hash(1L,1L),
			fixture.hashCode(),
			"Hash should match hashing X and Y as objects"
		);
	}
	
	@Test
	void testToString() {
		Coord fixture = new Coord(1L, 2L);
		assertEquals("<Coord x: 1 y: 2>", fixture.toString(), "toString should match expected");
	}

}
