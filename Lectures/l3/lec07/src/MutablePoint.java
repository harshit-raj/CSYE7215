package l3.lec07.src;

// Class of mutable points
//@author Rance Cleaveland
public class MutablePoint {
	private double x;
	private double y;
	
	MutablePoint (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals (MutablePoint p) {
		return ((this.x == p.getX()) && (this.y == p.getY()));
	}
	
	public double getX () {
		return x;
	}
	
	public double getY () {
		return y;
	}
	
	public void setX (double newX) {
		x = newX;
	}
	
	public void setY (double newY) {
		y = newY;
	}
}
