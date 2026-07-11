package life;

import java.util.List;

public class Controls {
	Game game;
	int speed;
	Board selected_shape;
	Library library;
	Selection current_selection;
	boolean is_playing;
	
	Controls() {
		reset();
	}
	
	public void start() {
		is_playing = true;
	}
	
	public boolean next() {
		return game.forward();
	}
	
	public void previous() {
		game.backward();
	}
	
	public void save_selection_to_library() {
		library.save_shape(game.shape_from_selection(current_selection));
	}
	
	public List<Board> list_shapes() {
		return library.get_shapes();
	}
	
	public void set_shape() {
		if (selected_shape == null) {
			game.place_shape(current_selection);
		} else {
			game.place_shape(current_selection, selected_shape);
		}
	}
	
	public void select_cells(Coord top_left,Coord bottom_right) {
		current_selection = new Selection(top_left,bottom_right);
	}
	
	public void reset() {
		game = new Game();
		speed = 1;
		selected_shape = null;
		library = new Library();
		current_selection = null;
		is_playing = false;
	}
}
