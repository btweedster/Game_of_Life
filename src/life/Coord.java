package life;

import java.util.Objects;

public class Coord {
	Long x;
	Long y;
	
	Coord(long x, long y){
		this.x = x;
		this.y = y;
	}
	
	public Long get_x() {
		return x;
	}
	
	public Long get_y() {
		return y;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord c = (Coord) o;
        return get_x() == c.get_x() && get_y() == c.get_y();
    }
	
	@Override
	public int hashCode() {
		return Objects.hash(x,y);
	}
}
