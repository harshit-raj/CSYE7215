package l3.visibility;

public class VisibilityTest {

	private static boolean ready;
	private static int number;
	private static int result;

	private static class ReaderThread extends Thread {
		public void run () {
			while (!ready){} { Thread.yield(); }
			result = number;
		}
	}

	public static void main(String[] args) {
		ReaderThread t;
		for (int i = 0; i < 100000; i++) {
			ready = false;
			t = new ReaderThread();
			t.start ();
			number = i;
			ready = true;
			try { t.join (); } catch (InterruptedException e) {}
			if (number != result)
				System.out.println ("Discrepancy");
		}
		System.out.println("Done");
	}

}
