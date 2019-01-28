package l3.lec07.src;

// this export problem demonstrator derived from example by
// Evan Golub

public class FixedTimeStampedDriver {

	public static void main(String[] args) {
		int errorCount = 0;
		int iterations = 10000;
		Thread t1;

		for (int i=0; i<iterations; i++) {
			t1 = new Thread(new Runnable() {
				public void run() {
					FixedTimeStampedObj.newInstance(new Object());
				}
			});
			t1.start();

			if (FixedTimeStampedObjCache.lastObjCreated.getTimeStamp() == null) {
				errorCount++;
			}
		}

		System.out.println(errorCount);

	}

}
