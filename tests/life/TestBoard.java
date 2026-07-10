package life;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestBoard {

	@Test
	void testGetShape() {
		Board fixture = new Board();
		Coord one = new Coord(1l, 1l);
		Coord two = new Coord(2l, 2l);
		Coord twenty = new Coord(20l, 20l);
		
		fixture.place_shape(one);
		fixture.place_shape(two);
		fixture.place_shape(twenty);
		
		Coord only_twenty_tl = new Coord(19, 21);
		Coord only_twenty_br = new Coord(21, 19);
		
		Selection only_twenty = new Selection(only_twenty_tl, only_twenty_br);
		Board only_twenty_b = fixture.get_shape(only_twenty);
		
		assertTrue(only_twenty_b.get_cells().contains(twenty), "Only Twenty should contain twenty");
		assertFalse(only_twenty_b.get_cells().contains(one), "Only Twenty should not contain one");
		assertFalse(only_twenty_b.get_cells().contains(two), "Only Twenty should not contain two");
		
		
		Coord one_and_two_tl = new Coord(0L, 10L);
		Coord one_and_two_br = new Coord(10L, 0L);
		
		Selection one_and_two = new Selection(one_and_two_tl, one_and_two_br);
		Board one_and_two_b = fixture.get_shape(one_and_two);
		
		assertFalse(one_and_two_b.get_cells().contains(twenty), "One and Two should not contain twenty");
		assertTrue(one_and_two_b.get_cells().contains(one), "One and Two should contain one");
		assertTrue(one_and_two_b.get_cells().contains(two), "One and Two should contain two");
	}
	
	@Test
	void testCheckLiving() {
		Board fixture = new Board();
		
		assertFalse(fixture.check_living(), "New boards are dead");
		
		Coord one = new Coord(1l, 1l);
		Coord two = new Coord(2l, 2l);
		Coord twenty = new Coord(20l, 20l);
		
		fixture.place_shape(one);
		fixture.place_shape(two);
		fixture.place_shape(twenty);
		
		assertTrue(fixture.check_living(), "Board with stuff are alive");
		
	}
	
	@Test
	void testPlaceShapeToggle() {
		Board fixture = new Board();
		
		assertFalse(fixture.check_living(), "New boards are dead");
		
		Coord one = new Coord(1l, 1l);
		
		fixture.place_shape(one);
		
		assertTrue(fixture.check_living(), "Board with stuff are alive");
		
		fixture.place_shape(one);
		
		assertFalse(fixture.check_living(), "Boards with no stuff are dead");
	}

}
