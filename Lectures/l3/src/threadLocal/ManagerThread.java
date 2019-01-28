package l3.src.threadLocal;
import java.util.ArrayList;

// Example showing how ThreadLocal, local variables used to ensure thread safety,
// even when non-thread-safe objects are used.  Manager threads spawn worker threads,
// recording the worker-thread ids in the ThreadLocal threadIds.  The output string
// is computed using a local variable.
//
// @author  Rance Cleaveland 2012-09-25

public class ManagerThread extends Thread {

	private static ThreadLocal<ArrayList<Long>> threadIds
	= new ThreadLocal<ArrayList<Long>> () {
		protected ArrayList<Long> initialValue () {
			return new ArrayList<Long> ();
		} // Anonymous overriding needed to create proper initialValue () method
		  // for ThreadLocal variable.
	};
	
	private int numWorkers;
	
	ManagerThread (String name, int n) {
		this.setName (name);
		numWorkers = n;
	}
	
	// Precondition:  none
	// Postcondition:  new worker thread created, id stored in threadIds
	// Exception:  non
	private void startWorker () {
		WorkerThread t = new WorkerThread ();
		ArrayList<Long> workerIds = threadIds.get();
		workerIds.add(t.getId());
		t.start ();
	}
	
	// Precondition:  none
	// Postcondition:  numWorker number of threads started, ids printed
	// Exception:  none
	public void run () {
		for (int i = 0; i < numWorkers; i++) startWorker ();
		String output = getName() + " worker ids:  ";
		for (Long id : threadIds.get()) output += ( Long.toString(id) + " ");
		System.out.println (output);
	}

}
