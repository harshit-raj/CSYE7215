package raj;

import akka.actor.UntypedActor;

import java.util.ArrayList;
import java.util.List;

/**
 * this actor implements the search for a path that satisfies the project requirements 
 *
 * @author H. Raj
 *
 */
public class SearcherD extends UntypedActor {
	SolutionMessage solutionMessage = null;

	public SearcherD() {
		// TODO 
	}

	@Override
	public void onReceive(Object msg) throws Throwable {
		if(msg instanceof String){
			String m = (String)msg;
			if(m.equals("Winner")){
				if(sender().equals(getSelf())){
					System.out.println("I Won "+ getSelf().path().name() + " "+ solutionMessage);
				}
				else if(sender().equals(getContext().system().deadLetters())){
					System.out.println("There are no winners "+getSelf().path().name() + " "+ solutionMessage);

				}
				else{
					System.out.println("I lost "+ getSelf().path().name() + " "+ solutionMessage+" Winner is: "+ getSender().path().name());
//					System.out.println("Winner is: "+ getSender().getClass().getName());
				}

				getContext().parent().tell(new PoisonMessage(),getSelf());

				getContext().stop(self());
			}

			//getContext().stop(self());
		}


		if(msg instanceof Messages){

		 Messages messages = (Messages)msg;
		 TspDynamicProgrammingRecursive tspSolver = new TspDynamicProgrammingRecursive(messages.city,messages.cityMatrix);

		 if(tspSolver.getTourCost() > ((Messages) msg).getMaxLength()){
			 solutionMessage = new SolutionMessage(false,tspSolver.getTourCost(),tspSolver.getTour());
		 }else{
			 solutionMessage = new SolutionMessage(true,tspSolver.getTourCost(),tspSolver.getTour());
		 }



		 getSender().tell(solutionMessage,getSelf());


		}



	}


	/**
	 * The TSPDynamicProgramingRecursive is inspired from
	 * https://github.com/williamfiset/Algorithms/blob/master/com/williamfiset/algorithms/graphtheory/TspDynamicProgrammingRecursive.java
	 */


	 class TspDynamicProgrammingRecursive {

		private final int N;
		private final int START_NODE;
		private final int FINISHED_STATE;

		private double[][] distance;
		private double minTourCost = Double.POSITIVE_INFINITY;

		private List<Integer> tour = new ArrayList<>();
		private boolean ranSolver = false;

		public TspDynamicProgrammingRecursive(double[][] distance) {
			this(0, distance);
		}

		public TspDynamicProgrammingRecursive(int startNode, double[][] distance) {

			this.distance = distance;
			N = distance.length;
			START_NODE = startNode;

			// Validate inputs.
			if (N <= 2) throw new IllegalStateException("TSP on 0, 1 or 2 nodes doesn't make sense.");
			if (N != distance[0].length) throw new IllegalArgumentException("Matrix must be square (N x N)");
			if (START_NODE < 0 || START_NODE >= N) throw new IllegalArgumentException("Starting node must be: 0 <= startNode < N");
			if (N > 32) throw new IllegalArgumentException("Matrix too large! A matrix that size for the DP TSP problem with a time complexity of" +
					"O(n^2*2^n) requires way too much computation for any modern home computer to handle");

			// The finished state is when the finished state mask has all bits are set to
			// one (meaning all the nodes have been visited).
			FINISHED_STATE = (1 << N) - 1;
		}

		// Returns the optimal tour for the traveling salesman problem.
		public List<Integer> getTour() {
			if (!ranSolver) solve();
			return tour;
		}

		// Returns the minimal tour cost.
		public double getTourCost() {
			if (!ranSolver) solve();
			return minTourCost;
		}

		public void solve() {

			// Run the solver
			int state = 1 << START_NODE;
			Double[][] memo = new Double[N][1 << N];
			Integer[][] prev = new Integer[N][1 << N];
			minTourCost = tsp(START_NODE, state, memo, prev);

			// Regenerate path
			int index = START_NODE;
			while (true) {
				tour.add(index);
				Integer nextIndex = prev[index][state];
				if (nextIndex == null) break;
				int nextState = state | (1 << nextIndex);
				state = nextState;
				index = nextIndex;
			}
			tour.add(START_NODE);
			ranSolver = true;
		}

		private double tsp(int i, int state, Double[][] memo, Integer[][] prev) {

			// Done this tour. Return cost of going back to start node.
			if (state == FINISHED_STATE) return distance[i][START_NODE];

			// Return cached answer if already computed.
			if (memo[i][state] != null) return memo[i][state];

			double minCost = Double.POSITIVE_INFINITY;
			int index = -1;
			for (int next = 0; next < N; next++) {

				// Skip if the next node has already been visited.
				if ((state & (1 << next)) != 0) continue;

				int nextState = state | (1 << next);
				double newCost = distance[i][next] + tsp(next, nextState, memo, prev);
				if (newCost < minCost) {
					minCost = newCost;
					index = next;
				}
			}

			prev[i][state] = index;
			return memo[i][state] = minCost;
		}

	}
}
