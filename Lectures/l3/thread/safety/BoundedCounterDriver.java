package l3.thread.safety;

public class BoundedCounterDriver {

	public static void main(String[] args) {
		BoundedCounter shared = new BoundedCounter(3);
		int numThreads = 3;
		Thread[] t = new Thread[numThreads];
		for (int i = 0; i < numThreads; i++){
			t[i] = new BoundedCounterUserThread (Integer.toString(i), shared);
		}
		for (int i = 0; i < numThreads; i++){
			t[i].start();
		}
		System.out.println ("Main Finished");
//		BoundedCounter c = new BoundedCounter (2);
//		c.inc();
//		c.inc();
//		System.out.println(c.current());
	}

}
