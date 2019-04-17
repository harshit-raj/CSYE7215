package raj;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.Arrays;

/**
 * Main class for your estimation actor system.
 *
 * @author Akash Nagesh and M. Kokar
 *
 */
public class User {


	public static void main(String[] args) throws Exception {
		double[][] cityMatrix = null;
		try {
			cityMatrix = ReadCities.read("cities.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}


		Messages messages = new Messages(cityMatrix,500,2);

		ActorSystem system = ActorSystem.create("EstimationSystem");
		ActorRef solver = system.actorOf(Props.create(Solver.class));
		solver.tell(messages,ActorRef.noSender());
		while (!solver.isTerminated()){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		system.terminate();

		/*
		 * Create the Solver Actor and send it the StartProcessing
		 * message. Once you get back the response, use it to print the result.
		 * Remember, there is only one actor directly under the ActorSystem.
		 * Also, do not forget to shutdown the actorsystem
		 */

	}

}
