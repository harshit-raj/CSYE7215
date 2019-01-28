package l3.src.unsafePublication;
// Derived from JCIP p. 51

public class Holder {

	private int n;
	
	public Holder (int n) { 
		//try { Thread.sleep(100); } catch (InterruptedException e) {}
		this.n = n; }
	
	public void assertSanity () {
		if (n != n) throw new AssertionError ("BAD CONSTRUCTION!");
	}
}
