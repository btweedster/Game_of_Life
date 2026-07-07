package life;
import java.util.HashMap;
import java.util.Map;

public class Board {
	Map<Coord,Object> cells = new HashMap<Coord,Object>();
	long max_x = 0;
	long max_y = 0;
	long min_x = 0;
	long min_y = 0;
	
	private void add_cell(Coord c) {
		long x = c.get_x();
		long y = c.get_y();
		
		if (x > max_x) {
			max_x = x;
		} else if (x < min_x) {
			min_x = x;
		}
		
		if (y > max_y) {
			max_y = y;
		} else if (y < min_y) {
			min_y = y;
		}
		
		if (!cells.containsKey(c)) {
			cells.put(c, null);
		}
	}
	
	public void flip_cell(Coord c) {
		if (cell_alive(c)) {
			cells.remove(c);
		} else {
			cells.put(c, null);
		}
	}
	
	private int adjacent_count(Coord c) {
		int count = 0;
		
		for (long i = c.get_x() - 1; i <= c.get_x() + 1; i++) {
			for (long j = c.get_y() - 1; j <= c.get_y() + 1; j++) {
				if (cells.containsKey(new Coord(i,j))) {
					count++;
				}
			}
		}
		
		return count;
	}
	
	private boolean cell_alive(Coord c) {
		if (cells.containsKey(c)) {
			return true;
		}
		return false;
	}
	
	public boolean is_living() {
		if (!cells.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public Board evolve() {
		Board b = new Board();
		for (long i = min_x + 1; i <= max_x + 1; i++) {
			for (long j = min_y + 1; j <= max_y + 1; j++) {
				Coord new_cell = new Coord(i,j);
				int count = adjacent_count(new_cell);
				if (count == 3 || (count == 2 && cell_alive(new_cell))) {
					b.add_cell(new_cell);
				}
			}
		}
		return b;
		
	}
	public Board get_shape(Selection s) {
		Board b = new Board();
		for (Coord c : cells.keySet()) {
			if (c.get_x() <= s.top_left.get_x() 
					&& c.get_x() >= s.bot_right.get_x() 
					&& c.get_y() <= s.top_left.get_y() 
					&& c.get_y() >= s.bot_right.get_y()) {
						b.add_cell(c);
			}
		}
		return b;
	}
	
	
	public void place_diff(Coord c, Board b) {
		
	}
}
