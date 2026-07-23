package life;

public class Selection {
	Coord p1;
	Coord p2;
	
	Selection(Coord p1, Coord p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Long get_min_x() {
		Long x1 = p1.get_x();
		Long x2 = p2.get_x();
		return Long.min(x1, x2);
	}
	
	public Long get_max_x() {
		Long x1 = p1.get_x();
		Long x2 = p2.get_x();
		return Long.max(x1, x2);
	}
	
	public Long get_min_y() {
		Long y1 = p1.get_y();
		Long y2 = p2.get_y();
		return Long.min(y1, y2);
	}
	
	public Long get_max_y() {
		Long y1 = p1.get_y();
		Long y2 = p2.get_y();
		return Long.max(y1, y2);
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
