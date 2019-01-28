package l3.src.unsafePublication;

public class UnsafePublication {

	private static Holder h;
	public static class ThreadCheck extends Thread {
		public void run () {
			while (true) h.assertSanity();
		}
	}
	
	public static void main(String[] args) {
		final Holder holder = new Holder (42);
		for (int i = 0; i < 100; i++) {
			Thread t = new ThreadCheck();
			t.setDaemon(true);
			t.start();
		}
		System.out.println ("Threads created");
		for (int i = 0; i < 100000000; i++) h = new Holder(42);
		System.out.println ("Objects created");
	}

}
