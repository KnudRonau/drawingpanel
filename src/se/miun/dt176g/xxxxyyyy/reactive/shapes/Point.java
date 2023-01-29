package se.miun.dt176g.xxxxyyyy.reactive.shapes;

import java.io.Serializable;

/**
 * <h1>Point</h1> Helper class to create (x,y) coordinates as objects and easily manipulate them.
 *
 * @author 	--Knud Ronau Larsen--
 * @version 1.0
 * @since 	2022-09-08
 */

public class Point implements Serializable {

	private int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}

	public void x(int x) {
		this.x = x;
	}

	public void y(int y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		Point p = (Point) o;
		return (x == p.x() && y == p.y());
	}
	
	@Override
	public String toString() {
		return "["+x+","+y+"]";
	}

}
