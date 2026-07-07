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
}
