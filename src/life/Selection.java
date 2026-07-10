package life;

public class Selection {
	Coord top_left;
	Coord bot_right;
	
	Selection(Coord tl, Coord br) {
		top_left = tl;
		bot_right = br;
	}
	
	public Coord get_tl(){
		return top_left;
	}
	public Coord get_br(){
		return bot_right;
	}
	
	public Long get_min_x() {
		Long tl_x = top_left.get_x();
		Long br_x = bot_right.get_x();
		return Long.min(tl_x, br_x);
	}
	
	public Long get_max_x() {
		Long tl_x = top_left.get_x();
		Long br_x = bot_right.get_x();
		return Long.max(tl_x, br_x);
	}
	
	public Long get_min_y() {
		Long tl_y = top_left.get_y();
		Long br_y = bot_right.get_y();
		return Long.min(tl_y, br_y);
	}
	
	public Long get_max_y() {
		Long tl_y = top_left.get_y();
		Long br_y = bot_right.get_y();
		return Long.max(tl_y, br_y);
	}
	
}
