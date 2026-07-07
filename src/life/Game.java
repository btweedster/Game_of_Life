package life;

import java.util.List;
import java.util.ArrayList;

public class Game {
	List<Board> board_history;
	int history_position;
	
	Game() {
		board_history = new ArrayList<Board>();
		board_history.add(new Board());
		history_position = 0;
	}
	
	public boolean forward() {
		Board b = board_history.get(history_position).evolve();
		if (b.is_living()) {
			history_position++;
			board_history.set(history_position, b);
			return true;
		}
		return false;
	}
    
	public void backward() {
    	if (history_position > 0) {
    		history_position--;
    	}
    }
    
	public void display() {
	}
    
	public void place_shape(Selection s) {
		board_history.get(history_position).flip_cell(s.get_tl());
		reset_history();
	}
	
	public void place_shape(Coord c, Board b) {
		board_history.get(history_position).place_diff(c, b);
		reset_history();
	}
    
	public Board shape_from_selection(Selection s) {
		return board_history.get(history_position).get_shape(s);
	}
	
	private void reset_history() {
		Board temp_b = board_history.get(history_position);
		board_history = new ArrayList<Board>();
		board_history.add(temp_b);
		history_position = 0;
	}
}
