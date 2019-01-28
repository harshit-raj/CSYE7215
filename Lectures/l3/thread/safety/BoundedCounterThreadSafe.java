package l3.thread.safety;
// Class of thread-safe bounded-counter objects
// @author Rance Cleaveland 2012-09-07

public class BoundedCounterThreadSafe {
	private int value = 0;
	private int upperBound = 0;
	
	//INVARIANT 1:  in all instances 0 <= value <= upperBound
	//INVARIANT 2:  value = # of calls to inc since last reset
	//              or object creation, if # of calls <= upperBound
	//INVARIANT 3:  value = upperBound if # of calls to inc since last reset
	//              or object creation > upperBound
	
	//Precondition:  argument must be >= 0
	//Postcondition:  object created
	//Exception:  If argument < 0, IllegalArgumentException thrown
	BoundedCounterThreadSafe (int upperBound) throws IllegalArgumentException {
		if (upperBound >= 0) this.upperBound = upperBound;
		else throw new IllegalArgumentException (
				"Bad argument to BoundedCounter: " + upperBound + "; must be >= 0");
	}
	
	//Precondition:  none
	//Postcondition:  current value returned
	//Exception:  none	
	public synchronized int current () {
		return value;
	}
	
	//Precondition:  none
	//Postcondition:  value reset to 0
	//Exception:  none
	public synchronized void reset () {
		value = 0;
	}
	
	//Precondition:  none
	//Postcondition:  returns boolean indicating whether or not value is maxed out
	//Exception:  none
	public synchronized boolean isMaxed () {
		return (value == upperBound);
	}
	
	//This method is incorrect, as it breaks the invariant
	//public void inc () {
	//	++value;
	//}
	
	//Precondition:  none
	//Postcondition:  increment value if not maxed; otherwise, do nothing.
	//Exception:  none
	public synchronized void inc () {
		if (!isMaxed()) ++value;
	}
}
