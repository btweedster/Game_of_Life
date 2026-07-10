package life;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestSelection {

	@Test
	void testSelection() {
		Coord top_left = new Coord (-10L, 10L);
		Coord bottom_right = new Coord(10L, -10L);
		
		Selection fixture = new Selection(top_left, bottom_right);
		
		assertEquals(top_left, fixture.get_tl(), "Top Left should match");
		assertEquals(bottom_right, fixture.get_br(), "Bottom Right should match");
	}
	
	@Test
	void testGetMin() {
		Coord top_left = new Coord (-10L, 20L);
		Coord bottom_right = new Coord(10L, -20L);
		
		Selection fixture = new Selection(top_left, bottom_right);
		
		assertEquals(-10L, fixture.get_min_x(), "Min X should be -10L");
		assertEquals(-20L, fixture.get_min_y(), "Min Y should be -20L");
	}
	
	@Test
	void testGetMax() {
		Coord top_left = new Coord (-10L, 20L);
		Coord bottom_right = new Coord(10L, -20L);
		
		Selection fixture = new Selection(top_left, bottom_right);
		
		assertEquals(10L, fixture.get_max_x(), "Max X should be 10L");
		assertEquals(20L, fixture.get_max_y(), "Max Y should be 20L");
	}

}
