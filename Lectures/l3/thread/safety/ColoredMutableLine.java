package l3.thread.safety;

// Thread-safe colored line
//@author Rance Cleaveland 2012-09-11

public class ColoredMutableLine {

	//@Invariant:  p1 and p2 must be different points.
	
	private Object InvLockP1P2 = new Object ();
	
	private Point p1;  // Guarded by lock LockP1P2Inv
	private Point p2;  // Guarded by lock LockP1P2Inv
	
	//@Invariant:  none
	
	private Object InvLockColor = new Object ();
	
	private int color; // Guarded by lock LockColor
	
	// CorrectLine throws exception if points overlap
	
	ColoredMutableLine (Point p1, Point p2, int color) throws IllegalArgumentException {
		if (!p1.equals(p2)) {
			this.p1 = p1;
			this.p2 = p2;
			this.color = color;
		}
		else {
			throw new IllegalArgumentException (
					"Points to ColoredMutableLine Constructor must be different:  " +
					p1.toString() + "given twice.");
		}
	}
	
	public Point getP1() {
		synchronized (InvLockP1P2) { return p1; }
	}

	public void setP1(Point p1) {
		synchronized (InvLockP1P2) {
			if (!p2.equals(p1))
				this.p1 = p1;
			else throw new IllegalArgumentException (
					"Illegal argument to setP1 : " +
					p1.toString() +
					" same as second point");
		}
	}


	public Point getP2() {
		synchronized (InvLockP1P2) { return p2; }
	}

	public void setP2(Point p2) {
		synchronized (InvLockP1P2) {
			if (!p1.equals(p2))
				this.p2 = p2;
			else throw new IllegalArgumentException (
					"Illegal argument to setP2 : " +
					p2.toString() +
					" same as first point");
		}
	}

	public int getColor() {
		synchronized (InvLockColor) { return color; }
	}

	public void setColor(int color) {
		synchronized (InvLockColor) { this.color = color; }
	}

	// Precondition:  p1 and p2 do not form a vertical line
	// Postcondition:  returns slope of line formed by p1, p2
	// Exception:  if p1 and p2 form a vertical line, an ArithmeticException
	//             is thrown.
	
	public double slope () throws ArithmeticException {
		return ((p1.getY() - p2.getY()) / (p1.getX() - p2.getX()));
	}

}
