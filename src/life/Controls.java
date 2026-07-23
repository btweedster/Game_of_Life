package life;

import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import java.util.Set;

public class Controls {
	Game game;
	int speed;
	Board selected_shape;
	Library library;
	Selection current_selection;
	final BooleanProperty is_playing = new SimpleBooleanProperty(false);
	final BooleanProperty has_selection = new SimpleBooleanProperty(false);
	
	Controls() {
		// Do this here so the saved shapes persist through resets
		library = new Library();
		reset();
	}
	
	public int get_speed() {
		return speed;
	}
	
	public void toggle_playing() {
		is_playing.set(!is_playing.get());
	}
	
	public boolean is_playing() {
		return is_playing.get();
	}
	
	public BooleanProperty is_playing_prop() {
		return is_playing;
	}
	
	public BooleanProperty has_selection_prop() {
		return has_selection;
	}
	
	public Set<Coord> get_current_for_display() {
		return game.get_current_state();
	}
	
	public boolean next() {
		boolean temp = game.forward();
		if (is_playing.get()) {
			is_playing.set(temp);;
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
	
	public void select_cells(Coord p1,Coord p2) {
		current_selection = new Selection(p1,p2);
		has_selection.set(true);
	}
	
	public void clear_selection() {
		current_selection = null;
		has_selection.set(false);
	}
	
	public Selection get_current_selection() {
		return current_selection;
	}
	
	public void reset() {
		game = new Game();
		speed = 100_000_000; // ~100ms per frame
		selected_shape = null;
		clear_selection();
		is_playing.set(false);
	}
}
