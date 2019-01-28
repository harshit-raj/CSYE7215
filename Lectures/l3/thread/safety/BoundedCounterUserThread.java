package l3.thread.safety;

public class BoundedCounterUserThread extends Thread {
	
	private BoundedCounter counter;
	
	BoundedCounterUserThread (String name, BoundedCounter counter) {
		this.setName(name);
		this.counter = counter;
	}
	
	public void run () {
//		System.out.println ("Thread " + this.getName () + " started.");
//		for (; !counter.isMaxed(); counter.inc()) {
			counter.inc();
//			try {Thread.sleep(10);} catch (InterruptedException e) {}
//			System.out.println ("Thread " + this.getName() + ":  " +
//					" counter = " + counter.current());
//		}
//		System.out.println ("Thread " + this.getName () + " finished.");
	}
}
