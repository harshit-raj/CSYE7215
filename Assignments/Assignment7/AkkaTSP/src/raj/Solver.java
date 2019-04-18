package raj;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * This is the main actor and the only actor that is created directly under the
 * {@code ActorSystem} This actor creates 4 child actors
 * {@code SearcherA}
 * 
 * @author Akash Nagesh and M. Kokar
 *
 */
public class Solver extends UntypedActor {
	int poisonCount = 0;
	int solRec = 0;


	ActorRef searcherA = getContext().actorOf(Props.create(SearcherA.class),"SearcherA");
	ActorRef searcherB = getContext().actorOf(Props.create(SearcherB.class),"SearcherB");
	ActorRef searcherC = getContext().actorOf(Props.create(SearcherC.class),"SearcherC");
	ActorRef searcherD = getContext().actorOf(Props.create(SearcherD.class),"SearcherD");

	ActorRef winner = null;



	public Solver() {

	}

	@Override
	public void onReceive(Object msg) throws Throwable {
		if(msg instanceof Integer){
//			System.out.println("gotha");
//
//			//Code to implement
//			getContext().stop(searcherA);
//			getContext().stop(getSelf());

		}

		if(msg instanceof String){
//			System.out.println("Message :"+ msg);
//			searcherA.tell(msg,getSelf());

		}

		//start searching
		if(msg instanceof Messages){
			Messages messageRecived = (Messages) msg;
			searcherA.tell(messageRecived,getSelf());
			searcherB.tell(messageRecived,getSelf());
			searcherC.tell(messageRecived,getSelf());
			searcherD.tell(messageRecived,getSelf());


		}

		if(msg instanceof SolutionMessage)
		{
			solRec++;

			SolutionMessage solutionMessage = (SolutionMessage)msg;
			if(solutionMessage.isSolutionFound()){


				if(winner ==null){
					winner = getSender();
					System.out.println("Got winner: "+getSender().getClass().getName());
					//getSender().tell("Champion",getSelf());
					//getContext().stop(winner);
					searcherA.tell("Winner",winner);
					searcherB.tell("Winner",winner);
					searcherC.tell("Winner",winner);
					searcherD.tell("Winner",winner);

				}
				System.out.println(solutionMessage);
			}
			else{
				System.out.println("Solution exceeds limit ");
				if(solRec == 4){
					searcherA.tell("Winner",ActorRef.noSender());
					searcherB.tell("Winner",ActorRef.noSender());
					searcherC.tell("Winner",ActorRef.noSender());
					searcherD.tell("Winner",ActorRef.noSender());
				}

			}



		}

		if(msg instanceof PoisonMessage){
			poisonCount++;
			if(poisonCount == 4){
				getContext().stop(self());
			}
		}

	}


}
