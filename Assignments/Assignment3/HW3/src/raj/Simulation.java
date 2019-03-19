package raj;

import java.util.*;

import raj.SimulationEvent;
/**
 * Simulation is the main class used to run the simulation.  You may
 * add any fields (static or instance) or any methods you wish.
 */
public class Simulation {
	// List to track simulation events during simulation
	public static List<SimulationEvent> events;
	//Priority que to hold all the orders placed;
	public static Queue<Customer> orderList = new PriorityQueue<Customer>(new Comparator<Customer>() {
        @Override
        public int compare(Customer cust1, Customer cust2) {
            return Integer.compare(cust1.getPriority(),cust2.getPriority());
        }
    });
	//number of tables at he resturant is the size of HashSet
	public boolean cooksWorking = true;
	public static Set<Customer> resturant = new HashSet<Customer>();

	public static Machine burger;
	public static Machine fries;
	public static Machine coffee;





	/**
	 * Used by other classes in the simulation to log events
	 * @param event
	 */
	public static void logEvent(SimulationEvent event) {
		events.add(event);
		System.out.println(event);
	}

	/**
	 * 	Function responsible for performing the simulation. Returns a List of 
	 *  SimulationEvent objects, constructed any way you see fit. This List will
	 *  be validated by a call to Validate.validateSimulation. This method is
	 *  called from Simulation.main(). We should be able to test your code by 
	 *  only calling runSimulation.
	 *  
	 *  Parameters:
	 *	@param numCustomers the number of customers wanting to enter the coffee shop
	 *	@param numCooks the number of cooks in the simulation
	 *	@param numTables the number of tables in the coffe shop (i.e. coffee shop machineCap)
	 *	@param machineCapacity the machineCap of all machines in the coffee shop
	 *  @param randomOrders a flag say whether or not to give each customer a random order
	 *
	 */
	public static List<SimulationEvent> runSimulation(
			int numCustomers, int numCooks,
			int numTables, 
			int machineCapacity,
			boolean randomOrders
			) {

		//This method's signature MUST NOT CHANGE.  


		//We are providing this events list object for you.  
		//  It is the ONLY PLACE where a concurrent collection object is 
		//  allowed to be used.
		events = Collections.synchronizedList(new ArrayList<SimulationEvent>());




		// Start the simulation
		logEvent(SimulationEvent.startSimulation(numCustomers,
				numCooks,
				numTables,
				machineCapacity));



		// Set things up you might need



		// Start up machines
		burger = new Machine(FoodType.burger.name,FoodType.burger,machineCapacity);
		fries = new Machine(FoodType.fries.name,FoodType.fries,machineCapacity);
		coffee = new Machine(FoodType.coffee.name, FoodType.coffee,machineCapacity);






		// Let cooks in
		Thread[] cooks = new Thread[numCooks];
		Cook[] cookObj = new Cook[numCooks];
		for(int i = 0; i< cooks.length;i++){
			cookObj[i] = new Cook("Cook"+i);
			cooks[i] = new Thread(cookObj[i]);
		}
		for (Thread cook : cooks) {
			cook.start();
		}







		// Build the customers.
		System.out.println("Number of customer "+ numCustomers);
		Thread[] customers = new Thread[numCustomers];
		Random rand = new Random(19);
		LinkedList<Food> order;
		if (!randomOrders) {
			order = new LinkedList<Food>();
			order.add(FoodType.burger);
			order.add(FoodType.fries);
			order.add(FoodType.fries);
			order.add(FoodType.coffee);
			for(int i = 0; i < customers.length; i++) {
				customers[i] = new Thread(
						new Customer("Customer " + (i+1), order,rand.nextInt(5))
						);
			}
		}
		else {
			for(int i = 0; i < customers.length; i++) {
				Random rnd = new Random(27);
				int burgerCount = rnd.nextInt(3);
				int friesCount = rnd.nextInt(3);
				int coffeeCount = rnd.nextInt(3);
				order = new LinkedList<Food>();
				for (int b = 0; b < burgerCount; b++) {
					order.add(FoodType.burger);
				}
				for (int f = 0; f < friesCount; f++) {
					order.add(FoodType.fries);
				}
				for (int c = 0; c < coffeeCount; c++) {
					order.add(FoodType.coffee);
				}
				customers[i] = new Thread(
						new Customer("Customer " + (i+1), order,rnd.nextInt(5))
						);
			}
		}


		// Now "let the customers know the shop is open" by
		//    starting them running in their own thread.
		for(int i = 0; i < customers.length; i++) {
			customers[i].start();
			//NOTE: Starting the customer does NOT mean they get to go
			//      right into the shop.  There has to be a table for
			//      them.  The Customer class' run method has many jobs
			//      to do - one of these is waiting for an available
			//      table...
		}

//
		try {
			// Wait for customers to finish
			//   -- you need to add some code here...
		for (Thread customer : customers) {
			try {
				customer.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}






			// Then send cooks home...
			// The easiest way to do this might be the following, where
			// we interrupt their threads.  There are other approaches
			// though, so you can change this if you want to.
			Cook.stopCook();
			for(int i = 0; i < cooks.length; i++)
			{
				cookObj[i].stopCook();

				cooks[i].interrupt();
			}
			for(int i = 0; i < cooks.length; i++)
				cooks[i].join();

		}
		catch(InterruptedException e) {
			System.out.println("Simulation thread interrupted.");
		}

		// Shut down machines

		logEvent(SimulationEvent.machineEnding(burger));
		burger.shutDownMachine();
		logEvent(SimulationEvent.machineEnding(fries));
		fries.shutDownMachine();
		logEvent(SimulationEvent.machineEnding(coffee));
		coffee.shutDownMachine();





		// Done with simulation		
		logEvent(SimulationEvent.endSimulation());

		return events;
	}

	/**
	 * Entry point for the simulation.
	 *
	 * @param args the command-line arguments for the simulation.  There
	 * should be exactly four arguments: the first is the number of customers,
	 * the second is the number of cooks, the third is the number of tables
	 * in the coffee shop, and the fourth is the number of items each cooking
	 * machine can make at the same time.  
	 */
	public static void main(String args[]) throws InterruptedException {
		// Parameters to the simulation
		/*
		if (args.length != 4) {
			System.err.println("usage: java Simulation <#customers> <#cooks> <#tables> <machineCap> <randomorders");
			System.exit(1);
		}
		int numCustomers = new Integer(args[0]).intValue();
		int numCooks = new Integer(args[1]).intValue();
		int numTables = new Integer(args[2]).intValue();
		int machineCapacity = new Integer(args[3]).intValue();
		boolean randomOrders = new Boolean(args[4]);
		 */
//		int numCustomers = 10;
//		int numCooks =1;
//		int numTables = 5;
//		int machineCapacity = 4;
//		boolean randomOrders = false;
		int numCustomers = 500;
		int numCooks =15;
		int numTables = 25;
		int machineCapacity = 15;
		boolean randomOrders = true;


		// Run the simulation and then 
		//   feed the result into the method to validate simulation.
		System.out.println("Did it work? " + 
				Validate.validateSimulation(
						runSimulation(
								numCustomers, numCooks, 
								numTables, machineCapacity,
								randomOrders
								),numCustomers,numCooks,numTables,machineCapacity
						)
				);
	}

}



