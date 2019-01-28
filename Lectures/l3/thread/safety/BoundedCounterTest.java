package l3.thread.safety;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoundedCounterTest {

	@Test
	public void test() {
		int numThreads = 500;
		BoundedCounter shared = new BoundedCounter(numThreads);
		Thread[] t = new Thread[numThreads];
		int numRuns = 100;
		for (int i = 0; i < numRuns; i++) {
			shared.reset();
			for (int j = 0; j < numThreads; j++) {
				t[j] = new BoundedCounterUserThread (Integer.toString(j), shared);
			}
			for (int j = 0; j < numThreads; j++) {
				t[j].start();
			}
			for (int j=0; j < numThreads; j++) {
				try {
					t[j].join();
				}
				catch (InterruptedException e) { }
			}
			System.out.println ("Number of increments:  " + numThreads + "; Counter value = " + shared.current());
			assertTrue("foo", (shared.current()) == numThreads);
		}
	}

}
