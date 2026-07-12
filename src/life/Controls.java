package life;

import java.util.Map;
import java.util.Set;

public class Controls {
	Game game;
	int speed;
	Board selected_shape;
	Library library;
	Selection current_selection;
	boolean is_playing;
	
	Controls() {
		// Do this here so the saved shapes persist through resets
		library = new Library();
		reset();
	}
	
	public int get_speed() {
		return speed;
	}
	
	public void toggle_playing() {
		is_playing = !is_playing;
	}
	
	public boolean is_playing() {
		return is_playing;
	}
	
	public Set<Coord> get_current_for_display() {
		return game.get_current_state();
	}
	
	public boolean next() {
		boolean temp = game.forward();
		if (is_playing) {
			is_playing = temp;
		}
		return temp;
	}
	
	public void previous() {
		game.backward();
	}
	
	public void save_selection_to_library(String name) {
		library.save_shape(name, game.shape_from_selection(current_selection));
		clear_selection();
	}
	
	public Map<String, Board> list_shapes() {
		return library.get_shapes();
	}
	
	public void set_shape(Board shape) {
		selected_shape = shape;
	}
	
	public void clear_shape() {
		selected_shape = null;
	}
	
	public void place_shape(Coord loc) {
		if (selected_shape == null) {
			game.place_shape(loc);
			return;
		}
		game.place_shape(loc, selected_shape);
		clear_shape();
	}
	
	public void select_cells(Coord top_left,Coord bottom_right) {
		current_selection = new Selection(top_left,bottom_right);
	}
	
	public void clear_selection() {
		current_selection = null;
	}
	
	public Selection get_current_selection() {
		return current_selection;
	}
	
	public void reset() {
		game = new Game();
		speed = 100_000_000; // ~100ms per frame
		selected_shape = null;
		current_selection = null;
		is_playing = false;
	}
}
