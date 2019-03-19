package raj;
import raj.SimulationEvent;

import java.util.ArrayList;
import java.util.List;

import static raj.Simulation.*;

/**
 * Cooks are simulation actors that have at least one field, a name.
 * When running, a cook attempts to retrieve outstanding orders placed
 * by Eaters and process them.
 */
public class Cook implements Runnable {
	private final String name;
	private static boolean cookWorking = true;


	/**
	 * You can feel free modify this constructor.  It must
	 * take at least the name, but may take other parameters
	 * if you would find adding them useful.
	 *
	 * @param: the name of the cook
	 */
	public Cook(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
	public static void stopCook(){
		cookWorking = false;

		System.out.println("--------------------cook has been stoppped ");
	}

	/**
	 * This method executes as follows.  The cook tries to retrieve
	 * orders placed by Customers.  For each order, a List<Food>, the
	 * cook submits each Food item in the List to an appropriate
	 * Machine, by calling makeFood().  Once all machines have
	 * produced the desired Food, the order is complete, and the Customer
	 * is notified.  The cook can then go to process the next order.
	 * If during its execution the cook is interrupted (i.e., some
	 * other thread calls the interrupt() method on it, which could
	 * raise InterruptedException if the cook is blocking), then it
	 * terminates.
	 */
	public void run() {

        List<Thread> cookingFood = new ArrayList<Thread>();
		Simulation.logEvent(SimulationEvent.cookStarting(this));
		try {
			while(cookWorking) {

				Customer order;
				synchronized (orderList){
					while(orderList.size()<=0)
					{
						orderList.wait();
						orderList.notifyAll();
					}

						order = orderList.poll();
					Simulation.logEvent(SimulationEvent.cookReceivedOrder(this,order.getOrder(),order.getOrderNum()));
					orderList.notifyAll();

				}
				synchronized (order) {

					order.getOrder().forEach(food -> {
						try {
							Simulation.logEvent(SimulationEvent.cookStartedFood(this, food, order.getOrderNum()));
							switch (food.name) {
								case "burger":
									cookingFood.add(burger.makeFood(this, order.getOrderNum()));
									break;
								case "fries":
									cookingFood.add(fries.makeFood(this, order.getOrderNum()));
									break;
								case "coffee":
									cookingFood.add(coffee.makeFood(this, order.getOrderNum()));
									break;

								default:
									System.out.println("Improper food type");
									break;
							}
						} catch (Exception e) {
							System.out.println("Error placing order");
						}

					});
					//System.out.println("Orders placed");
				}
						cookingFood.forEach(thread -> {
							try {
								thread.join();



							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						});
						Simulation.logEvent(SimulationEvent.cookCompletedOrder(this,order.getOrderNum()));
						//System.out.println("Food cooked for: " + order.toString()+ " By "+ this.name);


						synchronized (order){
							order.finishOrder();
							order.notifyAll();
						}







			}
		}
		catch(InterruptedException e) {
			// This code assumes the provided code in the Simulation class
			// that interrupts each cook thread when all customers are done.
			// You might need to change this if you change how things are
			// done in the Simulation class.
			cookingFood.forEach(Thread::interrupt);
			for (Thread thread : cookingFood) {
				try {
					thread.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

			//System.out.println("Cook leaving");
			orderList.notifyAll();
			Simulation.logEvent(SimulationEvent.cookEnding(this));
		}
	}
}