package l3.lec07.src;


// this export problem demonstrator derived from example by
// Evan Golub

public class TimeStampedDriver {

	public static void main(String[] args) {
		int errorCount = 0;
		int iterations = 10000;
		Thread t1;

		for (int i=0; i<iterations; i++) {
			t1 = new Thread(new Runnable() {
				public void run() {
					new TimeStampedObj(new Object());
				}
			});
			t1.start();

			if (TimeStampedObjCache.lastObjCreated.getTimeStamp() == null) {
				errorCount++;
			}
		}

		System.out.println(errorCount);

	}

}
