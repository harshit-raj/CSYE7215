package l3.lock.performance;

public class LockTimer {

	// Tests time needed to do operations with, without locking
	
	public static void main(String[] args) {
		int val = 0;
		long start = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			int val1 = val;
		}
		long end = System.nanoTime();
		long time1 = end-start;
		System.out.println ("Execution time no locking:  " + time1);
		start = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			synchronized(LockTimer.class) {
				int val1 = val;
			}
		}
		end = System.nanoTime();
		long time2 = end-start;
		System.out.println("Execution time w/ locking:  " + time2);
		double ratio = (double)time2 / (double)time1;
		System.out.println("Ratio = " + ratio);
	}

}
