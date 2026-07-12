package life;
import java.util.HashSet;
import java.util.Set;

public class Board {
	Set<Coord> cells = new HashSet<Coord>();
	
	private void toggle_cell(Coord c) {
		if (cells.contains(c)) {
			cells.remove(c);
			return;
		}
		cells.add(c);
	}
	
	public void place_shape(Coord loc, Board shape) {
		if (shape == null) {
			place_shape(loc);
			return;
		}
		Long shape_offset_x = 0L;
		Long shape_offset_y = 0L;
		for (Coord cell: shape.cells) {
			if (cell.get_x() > shape_offset_x) {
				shape_offset_x = cell.get_x();
			}
			if (cell.get_y() > shape_offset_y) {
				shape_offset_y = cell.get_y();
			}
		}
		Long offset_x = loc.get_x() - shape_offset_x;
		Long offset_y = loc.get_y() - shape_offset_y;
		for (Coord cell: shape.cells) {
			Long new_x = cell.get_x() + offset_x;
			Long new_y = cell.get_y() + offset_y;
			cells.add(new Coord(new_x, new_y));
		}
	}
	
	public void place_shape(Coord loc) {
		toggle_cell(loc);
	}
	
	public Board get_shape(Selection selection) {
		Board result = new Board();
		for(Coord cell: cells) {
			if (selection.contains(cell)) {
				result.cells.add(cell);
			}
		}
		return result;
	}
	
	private boolean live_or_die(Coord cell) {
		// if true then live, else die
		Long x = cell.get_x();
		Long y = cell.get_y();
		Selection sel = new Selection(new Coord(x-1,y+1), new Coord(x+1,y-1));
		Board living_neighbors = get_shape(sel);
		int count_neighbors = living_neighbors.cells.size();
		
		
		if (cells.contains(cell)) {
			int count_neighbors_living = count_neighbors - 1;
			if (count_neighbors_living == 2 || count_neighbors_living == 3) {
				return true;
			}
			return false;
		}
		if (count_neighbors == 3) {
			return true;
		}
		return false;
		
	}
	
	public Board evolve() {
		Board result = new Board();
		for(Coord cell: cells) {
			Long x = cell.get_x();
			Long y = cell.get_y();
			for (Long i = -1L; i < 2; i++) {
				for (Long j = -1L; j < 2; j++) {
					Coord cell_to_check = new Coord(x + i, y + j);
					if(live_or_die(cell_to_check)) {
						result.cells.add(cell_to_check);
					}
				}
			}
		}
		return result;
	}
	
	public boolean check_living() {
		return !cells.isEmpty();
	}
	
	public Set<Coord> get_cells() {
		return cells;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Board b = (Board) o;
		// checks if all elements in each set are the same
		return cells.containsAll(b.cells) && 
				b.cells.containsAll(cells);
	}
	
	@Override
	public int hashCode() {
		// hashCode should be the same if all Coords are the same.
		int h = 0;
		for (Coord c : cells) {
			if (c != null) {
				h = h + c.hashCode();
			}
		}
		return h;
	}
}
