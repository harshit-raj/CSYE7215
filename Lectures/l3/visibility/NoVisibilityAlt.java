package l3.visibility;
// Adapted from JCIP p. 34

public class NoVisibilityAlt {

	// private static boolean ready;
	// private static int number;
	private static volatile boolean ready;
	private static volatile int number;

	private static class ReaderThread extends Thread {
		public void run () {
			while (!ready)  Thread.yield ();
			// Without this yield() it will print nothing
//			System.out.println ("waiting");
//			}

			System.out.println (number);

		}
	}

	public static void main(String[] args) throws InterruptedException  {

	new ReaderThread().start ();

//		Thread.sleep(4000);
		number = 42;
		ready = true;

	}

}
