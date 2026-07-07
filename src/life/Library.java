package life;

import java.util.List;
import java.util.ArrayList;

public class Library {
	List<Board> shapes;
	
	Library() {
		shapes = new ArrayList<Board>();
	}
	
	public Board get_shape(int i) {
		return shapes.get(i);
	}
	
	public void save_shape(Board b) {
		shapes.add(b);
	}
	
	public List<Board> get_shapes() {
		return shapes;
	}
}
