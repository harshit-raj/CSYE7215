package l3.thread.safety;
// Class of threads that increment a shared variable.
// @author Rance Cleaveland

public class SyncIncThread implements Runnable {
	
	private static int shared = 0;	// Shared variable among threads
	private String name = "";		// Name of thread
	
	public synchronized static void incShared () {
		++shared;
	}
	
	public synchronized static void resetShared () {
		shared = 0;
	}
	
	public synchronized static int getShared () {
		return (shared);
	}
	
	public static void altIncShared () {
		synchronized (SyncIncThread.class) {
			++shared;
		}
	}
	
	static Object lock = new Object ();	// Lock
	
	// Constructor.  Argument is name.
	
	SyncIncThread (String name) {
		this.name = name;
	}

	// run method reads shared variable into private variable, prints
	// value, increments private variable, then writes back.
	
	public void run () {
		synchronized (lock) {
			int myShared = shared;
			System.out.println (name + " read shared = " + myShared);
			myShared++;
			shared = myShared;
			System.out.println (name + " assigned to shared: " + myShared);
		}
	}
}
