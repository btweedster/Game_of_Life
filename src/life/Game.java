package life;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class Game {
	List<Board> board_history;
	int history_position;
	Map<Board,Integer> loop_map;
	
	Game() {
		board_history = new ArrayList<Board>();
		board_history.add(new Board());
		history_position = 0;
		loop_map = new HashMap<Board,Integer>();
	}
	
	public boolean forward() {
		Board b = board_history.get(history_position).evolve();
		// Check the new board is living (has at least 1 living cell)
		// and that it is not equal to the last board.
		if (b.check_living() && !board_history.get(history_position).equals(b)) {
			// If loop_map contains the board, it is due to a loop
			if (loop_map.containsKey(b)) {
				history_position = loop_map.get(b);
				// If we want to stop when we hit loops uncomment the next line
				// return false;
			} else {
				board_history.add(b);
				history_position++;
				loop_map.put(b, history_position);

			}
			return true;
		}
		return false;
	}
    
	public void backward() {
    	if (history_position > 0) {
    		history_position--;
    	}
    }
    
	public Set<Coord> get_current_state() {
		return board_history.get(history_position).get_cells();
	}
    
	public void place_shape(Coord loc) {
		board_history.get(history_position).place_shape(loc);
		reset_history();
	}
	
	public void place_shape(Coord loc, Board b) {
		board_history.get(history_position).place_shape(loc, b);
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
		loop_map = new HashMap<Board,Integer>();
		loop_map.put(temp_b,0);
	}
	
	public Board get_current_board() {
		return board_history.get(history_position);
	}
	
	public int get_history_size() {
		return board_history.size();
	}
}
