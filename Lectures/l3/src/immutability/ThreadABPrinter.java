package l3.src.immutability;

public class ThreadABPrinter extends Thread {
	
	private ImmutableAB ab;

	ThreadABPrinter (ImmutableAB ab) {
		this.ab = ab;
	}
	
	public void run () {
		System.out.println ("b = " + ab.getB());
		try { Thread.sleep (100); } catch (InterruptedException e) {}
		System.out.println ("b = " + ab.getB());
	}

}
