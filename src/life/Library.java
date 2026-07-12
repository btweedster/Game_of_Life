package life;

import java.util.Map;
import java.util.HashMap;

public class Library {
	Map<String, Board> shapes;
	
	Library() {
		shapes = new HashMap<String, Board>();
	}
	
	public Board get_shape(String name) {
		return shapes.get(name);
	}
	
	public void save_shape(String name, Board b) {
		shapes.put(name, b);
	}
	
	public Map<String, Board> get_shapes() {
		return shapes;
	}
}
