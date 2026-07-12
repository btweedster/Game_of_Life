package life;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestGame {

	@Test
	void testGameConstr() {
		Game g = new Game();
		assertNotNull(g);
		assertEquals(g.get_current_state().size(),0);
	}
	
	@Test
	void testPlaceShape() {
		Coord loc = new Coord(1,1);
		Board b = new Board();
		b.place_shape(loc);
		Game g = new Game();
		g.place_shape(loc);
		assertEquals(b,g.get_current_board());
	}
	
	@Test
	void testForward() {
		//test oscillating bar; horizontal to vertical
		Coord h_1 = new Coord(-1,0);
		Coord h_2 = new Coord(0,0);
		Coord h_3 = new Coord(1,0);
		Coord v_1 = new Coord(0,1);
		Coord v_2 = new Coord(0,0);
		Coord v_3 = new Coord(0,-1);
		Game g = new Game();
		g.place_shape(h_1);
		g.place_shape(h_2);
		g.place_shape(h_3);
		Board v = new Board();
		v.place_shape(v_1);
		v.place_shape(v_2);
		v.place_shape(v_3);
		assertTrue(g.forward());
		assertEquals(v,g.get_current_board());
		assertEquals(2,g.get_history_size());
	}
	
	@Test
	void testLoop() {
		Coord h_1 = new Coord(-1,0);
		Coord h_2 = new Coord(0,0);
		Coord h_3 = new Coord(1,0);
		Game g = new Game();
		g.place_shape(h_1);
		g.place_shape(h_2);
		g.place_shape(h_3);
		assertEquals(1,g.get_history_size());
		assertTrue(g.forward());
		assertEquals(2,g.get_history_size());
		// check that as the game loops through a cycle, it is not
		// adding to board history.
		for (int i = 0; i < 20; i++) {
			assertTrue(g.forward());
			assertEquals(2,g.get_history_size());
		}
	}
	
	@Test
	void testBackwards() {
		// test horizontal to vertical then backwards.
		Coord a_1 = new Coord(-1,0);
		Coord a_2 = new Coord(0,0);
		Coord a_3 = new Coord(1,0);
		Coord b_1 = new Coord(-1,0);
		Coord b_2 = new Coord(0,0);
		Coord b_3 = new Coord(1,0);
		Game g = new Game();
		g.place_shape(a_1);
		g.place_shape(a_2);
		g.place_shape(a_3);
		Board h = new Board();
		h.place_shape(b_1);
		h.place_shape(b_2);
		h.place_shape(b_3);
		assertTrue(g.forward());
		g.backward();
		assertEquals(g.get_current_board(),h);
	}
	
	@Test
	void testShapeFromSelection() {
		Board board_a = new Board();
		board_a.place_shape(new Coord(-2,0));
		board_a.place_shape(new Coord(-1,0));
		board_a.place_shape(new Coord(0,0));
		Board board_b = new Board();
		board_b.place_shape(new Coord(-2,0));
		board_b.place_shape(new Coord(-1,0));
		board_b.place_shape(new Coord(0,0));
		Game g = new Game();
		
		// Check the shapes match
		g.place_shape(new Coord(0,0), board_a);
		Selection shape_s = new Selection(new Coord(-3,1),new Coord(0,0));
		Board shape = g.shape_from_selection(shape_s);
		assertEquals(board_b,shape, "Shapes should match");
		
		// Check the offset shape does not match
		g.place_shape(new Coord(-1,1), board_a);
		shape_s = new Selection(new Coord(-1,1),new Coord(1,-1));
		shape = g.shape_from_selection(shape_s);
		assertNotEquals(board_b,shape, "Shapes shouldn't match");
	}
}
