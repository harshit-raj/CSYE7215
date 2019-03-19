package raj;

import java.util.List;

import static raj.Simulation.*;

/**
 * Customers are simulation actors that have two fields: a name, and a list
 * of Food items that constitute the Customer's order.  When running, an
 * customer attempts to enter the coffee shop (only successful if the
 * coffee shop has a free table), place its order, and then leave the 
 * coffee shop when the order is complete.
 */

/**
*Invariants
*customerId is unique among all customers
*priority is a value among 1,2,3
*customer ID, priority and order should be immutable
*order.size >0
*/

public class Customer implements Runnable {
	//JUST ONE SET OF IDEAS ON HOW TO SET THINGS UP...
	private final String name;
	private final List<Food> order;
	private final int orderNum;    
	private final int priority;
	private static int runningCounter = 0;
	private boolean orderComplete;

	public List<Food> getOrder() {
		return order;
	}

	public int getOrderNum() {
		return orderNum;
	}

	/**
	 * You can feel free modify this constructor.  It must take at
	 * least the name and order but may take other parameters if you
	 * would find adding them useful.
	 */
//	public Customer(String name, List<Food> order) {
//		this.name = name;
//		this.order = order;
//		this.orderNum = ++runningCounter;
//	}



    public Customer(String name, List<Food> order, int priority) {

            this.name = name;
            this.order = order;
            this.orderNum = ++runningCounter;
            this.priority= priority;
            this.orderComplete = false;


    }

	public String toString() {
		return "Name: "+ this.name + " Priority: "+ this.priority;
	}

	public int getPriority(){
        return this.priority;
    }

    public void finishOrder(){
    	this.orderComplete = true;
	}

	/** 
	 * This method defines what an Customer does: The customer attempts to
	 * enter the coffee shop (only successful when the coffee shop has a
	 * free table), place its order, and then leave the coffee shop
	 * when the order is complete.
	 */
	public void run() {
		//YOUR CODE GOES HERE...
		logEvent(SimulationEvent.customerStarting(this));
		synchronized (resturant){
			while(resturant.size() >= events.get(0).simParams[2]){
				try {
					resturant.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			resturant.add(this);
			logEvent(SimulationEvent.customerEnteredCoffeeShop(this));
//			System.out.println("Added to resturant: "+ this.name);
			resturant.notifyAll();

		}





		synchronized (orderList){
			orderList.add(this);
			logEvent(SimulationEvent.customerPlacedOrder(this,this.order,this.orderNum));
			orderList.notifyAll();
		}

		synchronized (this){
			while(!this.orderComplete){
				try {
					this.wait();
					this.notifyAll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
		logEvent(SimulationEvent.customerReceivedOrder(this,this.getOrder(),this.orderNum));

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		synchronized (resturant){
//			System.out.println("Removing : "+ this.name);
			logEvent(SimulationEvent.customerLeavingCoffeeShop(this));
			resturant.remove(this);
//			System.out.println("Size after remove "+ resturant.size());
			resturant.notifyAll();
		}


		
		
	}
}