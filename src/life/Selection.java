package life;

public class Selection {
	Coord p1;
	Coord p2;
	Long min_x;
	Long max_x;
	Long min_y;
	Long max_y;
	
	Selection(Coord p1, Coord p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Long get_min_x() {
		if (min_x == null) {
			Long x1 = p1.get_x();
			Long x2 = p2.get_x();
			min_x = Long.min(x1, x2);
		}
		return min_x;
	}
	
	public Long get_max_x() {
		if (max_x == null) {
			Long x1 = p1.get_x();
			Long x2 = p2.get_x();
			max_x = Long.max(x1, x2);
		}
		return max_x;
	}
	
	public Long get_min_y() {
		if (min_y == null) {
			Long y1 = p1.get_y();
			Long y2 = p2.get_y();
			min_y = Long.min(y1, y2);
		}
		return min_y;
	}
	
	public Long get_max_y() {
		if (max_y == null) {
			Long y1 = p1.get_y();
			Long y2 = p2.get_y();
			max_y = Long.max(y1, y2);
		}
		return max_y;
	}
	
	public Long get_width() {
		return get_max_x() - get_min_x();
	}
	
	public Long get_height() {
		return get_max_y() - get_min_y();
	}
	
	public boolean contains(Coord coord) {
		Long x = coord.get_x();
		Long y = coord.get_y();
		if (
			x >= get_min_x() &&
			x <= get_max_x() &&
			y >= get_min_y() &&
			y <= get_max_y()
		) {
			return true;
		}
		return false;
	}
	
}
