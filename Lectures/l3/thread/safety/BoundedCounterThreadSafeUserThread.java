package l3.thread.safety;

public class BoundedCounterThreadSafeUserThread extends Thread{
	private BoundedCounterThreadSafe counter;
	
	BoundedCounterThreadSafeUserThread (String name, BoundedCounterThreadSafe counter) {
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
