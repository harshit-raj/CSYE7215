package raj;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Main class for your estimation actor system.
 *
 * @author Akash Nagesh and M. Kokar
 *
 */
public class User {


	public static void main(String[] args) throws Exception {

		System.out.println("Enter file name for adjacency matrix: ");
		Scanner scanner = new Scanner(System.in);
		String fName = scanner.nextLine();


		double[][] cityMatrix = null;
		try {
			cityMatrix = ReadCities.read(fName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Adjacency matrix parsed number of cities: "+cityMatrix.length);
		System.out.println("Enter starting point[0-"+(cityMatrix.length-1)+"]");
		int startCity = scanner.nextInt();
		if (startCity >= cityMatrix.length || startCity<0){
			throw new Exception("Invalid start city "+ startCity);
		}
		System.out.println("Enter upper limit for length: ");
		double upperLimit = scanner.nextDouble();
		if(upperLimit <= 0){
			throw new Exception("Invalid upper limit: "+ upperLimit);
		}

		Messages messages = new Messages(cityMatrix,upperLimit,startCity);

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
