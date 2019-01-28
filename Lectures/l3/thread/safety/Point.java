package l3.thread.safety;
// Class of 2-d immutable points
// @author Rance Cleaveland

public class Point {
	
	private final double x;
	private final double y;
	
	Point (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return ("(" + getX() + "," + getY() + ")");
	}
	
	double getX () { return x; }
	
	double getY () { return y; }
	
	public boolean equals (Point p) {
		return ((x == p.getX()) && (y == p.getY()));
	}
}
