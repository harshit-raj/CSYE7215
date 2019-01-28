package l3.thread.safety;
// @author Rance Cleaveland 8/27/2012
//
// Correctly synchronized version of "IncRace" example

public class SyncIncRace {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread (new SyncIncThread ("t1"));
		Thread t2 = new Thread (new SyncIncThread ("t2"));
		t1.start ();
//		Thread.sleep (10);
		t2.start ();
	}

}
