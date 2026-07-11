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
		Selection s = new Selection(new Coord(1,1), new Coord(1,1));
		Board b = new Board();
		b.place_shape(new Coord(1,1));
		Game g = new Game();
		g.place_shape(s);
		assertEquals(b,g.T_get_current_board());
	}
	
	@Test
	void testBigShape() {
		Board board_a = new Board();
		board_a.place_shape(new Coord(-1,0));
		board_a.place_shape(new Coord(0,0));
		board_a.place_shape(new Coord(1,0));
		Board board_b = new Board();
		board_b.place_shape(new Coord(-1,0));
		board_b.place_shape(new Coord(0,0));
		board_b.place_shape(new Coord(1,0));
		Game g = new Game();
		
		// Check two boards are equal.
		Selection s = new Selection(new Coord(0,0), new Coord(0,0));
		g.place_shape(s,board_a);
		assertEquals(g.T_get_current_board(), board_b);
		
		// Check the two board are not equal due to offset.
		g = new Game();
		s = new Selection(new Coord(-1,-1),new Coord(-1,-1));
		g.place_shape(s, board_a);
		assertNotEquals(g.T_get_current_board(),board_b);
	}
	
	@Test
	void testForward() {
		//test oscillating bar; horizontal to vertical
		Selection h_1 = new Selection(new Coord(-1,0), new Coord(-1,0));
		Selection h_2 = new Selection(new Coord(0,0), new Coord(0,0));
		Selection h_3 = new Selection(new Coord(1,0), new Coord(1,0));
		Selection v_1 = new Selection(new Coord(0,1), new Coord(0,1));
		Selection v_2 = new Selection(new Coord(0,0), new Coord(0,0));
		Selection v_3 = new Selection(new Coord(0,-1), new Coord(0,-1));
		Game g = new Game();
		g.place_shape(h_1);
		g.place_shape(h_2);
		g.place_shape(h_3);
		Board v = new Board();
		v.place_shape(v_1.get_tl());
		v.place_shape(v_2.get_tl());
		v.place_shape(v_3.get_tl());
		assertTrue(g.forward());
		assertEquals(v,g.T_get_current_board());
		assertEquals(2,g.T_get_history_size());
	}
	
	@Test
	void testLoop() {
		Selection h_1 = new Selection(new Coord(-1,0), new Coord(-1,0));
		Selection h_2 = new Selection(new Coord(0,0), new Coord(0,0));
		Selection h_3 = new Selection(new Coord(1,0), new Coord(1,0));
		Game g = new Game();
		g.place_shape(h_1);
		g.place_shape(h_2);
		g.place_shape(h_3);
		assertEquals(1,g.T_get_history_size());
		assertTrue(g.forward());
		assertEquals(2,g.T_get_history_size());
		// check that as the game loops through a cycle, it is not
		// adding to board history.
		for (int i = 0; i < 20; i++) {
			assertTrue(g.forward());
			assertEquals(2,g.T_get_history_size());
		}
	}
	
	@Test
	void testBackwards() {
		// test horizontal to vertical then backwards.
		Selection a_1 = new Selection(new Coord(-1,0), new Coord(-1,0));
		Selection a_2 = new Selection(new Coord(0,0), new Coord(0,0));
		Selection a_3 = new Selection(new Coord(1,0), new Coord(1,0));
		Selection b_1 = new Selection(new Coord(-1,0), new Coord(-1,0));
		Selection b_2 = new Selection(new Coord(0,0), new Coord(0,0));
		Selection b_3 = new Selection(new Coord(1,0), new Coord(1,0));
		Game g = new Game();
		g.place_shape(a_1);
		g.place_shape(a_2);
		g.place_shape(a_3);
		Board h = new Board();
		h.place_shape(b_1.get_tl());
		h.place_shape(b_2.get_tl());
		h.place_shape(b_3.get_tl());
		assertTrue(g.forward());
		g.backward();
		assertEquals(g.T_get_current_board(),h);
	}
	
	@Test
	void testShapeFromSelection() {
		Board board_a = new Board();
		board_a.place_shape(new Coord(-1,0));
		board_a.place_shape(new Coord(0,0));
		board_a.place_shape(new Coord(1,0));
		Board board_b = new Board();
		board_b.place_shape(new Coord(-1,0));
		board_b.place_shape(new Coord(0,0));
		board_b.place_shape(new Coord(1,0));
		Game g = new Game();
		
		// Check the shapes match
		Selection s = new Selection(new Coord(0,0),new Coord(0,0));
		g.place_shape(s, board_a);
		Selection shape_s = new Selection(new Coord(-1,1),new Coord(1,-1));
		Board shape = g.shape_from_selection(shape_s);
		assertEquals(board_b,shape);
		
		// Check the offset shape does not match
		s = new Selection(new Coord(-1,1),new Coord(-1,1));
		g.place_shape(s, board_a);
		shape_s = new Selection(new Coord(-1,1),new Coord(1,-1));
		shape = g.shape_from_selection(shape_s);
		assertNotEquals(board_b,shape);
	}
}
