package l3.thread.safety;

public class BoundedCounterDriver2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		BoundedCounterThreadSafe c = new BoundedCounterThreadSafe (2);
		Thread t1 = new Thread (new BoundedCounterIncThread (c));
		Thread t2 = new Thread (new BoundedCounterIncThread (c));
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println (c.current());

	}

}
