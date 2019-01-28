package raj;

//import java.util.LinkedList;
import java.util.*;


/**
 * This class runs <code>numThreads</code> instances of
 * <code>ParallelMaximizerWorker</code> in parallel to find the maximum
 * <code>Integer</code> in a <code>LinkedList</code>.
 */
public class ParallelMaximizer {
	
	int numThreads;
	int numThreads2;
	private static Random random = new Random(System.currentTimeMillis());
	ArrayList<ParallelMaximizerWorker> workers; // = new ArrayList<ParallelMaximizerWorker>(numThreads);
	ArrayList<ParallelMaximizerWorker> shortListWorkers;
	public ParallelMaximizer(int numThreads) {
		workers = new ArrayList<ParallelMaximizerWorker>(numThreads);
		shortListWorkers = new ArrayList<ParallelMaximizerWorker>(numThreads2);
		this.numThreads = numThreads;
		this.numThreads2 = 100;
	}
	


	
	public static void main(String[] args) {
		int numThreads = 2000; // number of threads for the maximizer
		int numElements = 10000000; // number of integers in the list
		
		ParallelMaximizer maximizer = new ParallelMaximizer(numThreads);
		LinkedList<Integer> list = new LinkedList<Integer>();
		
		// populate the list
		// TODO: change this implementation to test accordingly
		System.out.println("Populating linked List");
		for (int i=0; i<numElements; i++) 
			list.add(random.nextInt());
		
		try {
			System.out.println("Maximum is : "+ maximizer.max(list));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Finds the maximum by using <code>numThreads</code> instances of
	 * <code>ParallelMaximizerWorker</code> to find partial maximums and then
	 * combining the results.
	 * @param list <code>LinkedList</code> containing <code>Integers</code>
	 * @return Maximum element in the <code>LinkedList</code>
	 * @throws InterruptedException
	 */
	public int max(LinkedList<Integer> list) throws InterruptedException {
		int max = Integer.MIN_VALUE; // initialize max as lowest value
		System.out.println("-------------------------Phase 1-------------------------");
		// run numThreads instances of ParallelMaximizerWorker
		for(int i = 0; i< numThreads; i++) {
			workers.add(new ParallelMaximizerWorker(list));
			workers.get(i).start();
		}
		System.out.println("Worker threads created and started");
		System.out.println("Number of thread : "+ workers.size());
		
		// wait for threads to finish
		for (int i=0; i<workers.size(); i++) {
			workers.get(i).join();
		}
		System.out.println("Threads completed");
			
		
		// take the highest of the partial maximums
		// TODO: IMPLEMENT CODE HERE
		System.out.println("Getting max from each thread");
		LinkedList<Integer> shortList = new LinkedList<Integer>();
		for(int i = 0; i< workers.size(); i++) {
			int temp = workers.get(i).getPartialMax();
			System.out.println("Max Calculated by thread id: "+workers.get(i).getId() + " = "+ temp);
			shortList.add(temp);
		}
		System.out.println("-------------------------Phase 2-------------------------");
		
		System.out.println("Finding max from shortlist");
			for(int j = 0; j< numThreads2; j++) {
				shortListWorkers.add(new ParallelMaximizerWorker(shortList));
				shortListWorkers.get(j).start();
			}
			// wait for threads to finish
			System.out.println("Workers for shortlist created");
			for (int j=0; j<shortListWorkers.size(); j++) {
				shortListWorkers.get(j).join();
			}
			System.out.println("Number of threads : "+ shortListWorkers.size());
			System.out.println("Workers for shortlist completed");
			LinkedList<Integer> finalList = new LinkedList<Integer>();
			for(int j = 0; j< shortListWorkers.size(); j++) {
				int newTemp = shortListWorkers.get(j).getPartialMax();
				System.out.println("Max Calculated by thread id: "+shortListWorkers.get(j).getId() + " = "+ newTemp);
				finalList.add(newTemp);
			
//			if(newTemp>max) {
//				max = newTemp;
//			}
		}
			System.out.println("-------------------------Phase 3-------------------------");
			System.out.println("Sequential computation for ultimate max");
			for(int i : finalList) {
				if(i>max) {
					max = i;
				}
			}
			
		return max;
	}
	
}
