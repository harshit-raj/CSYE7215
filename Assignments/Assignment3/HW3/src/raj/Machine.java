package raj;

import java.util.ArrayList;

/**
 * A Machine is used to make a particular Food.  Each Machine makes
 * just one kind of Food.  Each machine has a machineCap: it can make
 * that many food items in parallel; if the machine is asked to
 * produce a food item beyond its machineCap, the requester blocks.
 * Each food item takes at least item.cookTimeMS milliseconds to
 * produce.
 */
public class Machine {
	public final String machineName;
	public final Food machineFoodType;
	private final int maxCapacity;
	private volatile int currentCap;
	Object machineCap;
	private static int foodCooked;
	private ArrayList<Thread> macThreads = new ArrayList<>();

	//YOUR CODE GOES HERE...


	/**
	 * The constructor takes at least the name of the machine,
	 * the Food item it makes, and its machineCap.  You may extend
	 * it with other arguments, if you wish.  Notice that the
	 * constructor currently does nothing with the machineCap; you
	 * must add code to make use of this field (and do whatever
	 * initialization etc. you need).
	 */
	public Machine(String nameIn, Food foodIn, int capacityIn) {
		this.machineName = nameIn;
		this.machineFoodType = foodIn;
		this.maxCapacity= capacityIn;
		machineCap = new Object();
		foodCooked = 0;
		currentCap = 0;
		//YOUR CODE GOES HERE...

	}

	public synchronized void  decrementCurrentCapacity(){
		this.currentCap--;
	}
	public synchronized void incrementCurrentCapacity(){
		this.currentCap++ ;
	}

	

	/**
	 * This method is called by a Cook in order to make the Machine's
	 * food item.  You can extend this method however you like, e.g.,
	 * you can have it take extra parameters or return something other
	 * than Object.  It should block if the machine is currently at full
	 * machineCap.  If not, the method should return, so the Cook making
	 * the call can proceed.  You will need to implement some means to
	 * notify the calling Cook when the food item is finished.
	 */
	public Thread makeFood(Cook cook, int orderNum) throws InterruptedException {

		Simulation.logEvent(SimulationEvent.machineStarting(this,this.machineFoodType,this.maxCapacity));
		synchronized (machineCap){
			while(currentCap>=maxCapacity){
				machineCap.wait();
				machineCap.notifyAll();
			}


			Thread cooking = new Thread(new CookAnItem(machineFoodType.cookTimeMS,this,cook,orderNum));
			cooking.start();
			return cooking;
		}




		//return null;
	}

	//THIS MIGHT BE A USEFUL METHOD TO HAVE AND USE BUT IS JUST ONE IDEA
	private class CookAnItem implements Runnable {
		int delayTime;
		Machine machine;
		Cook cook;
		int orderNum;
		CookAnItem(int delayTime,Machine machine, Cook cook, int orderNum){
			this.delayTime = delayTime;
		this.machine = machine;
		this.cook = cook;
		this.orderNum=orderNum;
		}
		public void run() {
			machine.incrementCurrentCapacity();
			Simulation.logEvent(SimulationEvent.machineCookingFood(this.machine,machineFoodType));

			try {
//				System.out.println("Cooking "+ machine.machineFoodType);
				Thread.sleep(this.delayTime);
//				this.machine.decrementCurrentCapacity();

				Simulation.logEvent(SimulationEvent.machineDoneFood(this.machine,machineFoodType));
				Simulation.logEvent(SimulationEvent.cookFinishedFood(cook,machineFoodType,orderNum));
				//YOUR CODE GOES HERE...
			} catch(InterruptedException e) {

//				this.machine.decrementCurrentCapacity();
				Simulation.logEvent(SimulationEvent.machineEnding(this.machine));
//				System.out.println("Machine cooking interupted");
			}finally {
				machine.decrementCurrentCapacity();

			}
		}
	}

	public void shutDownMachine(){
		macThreads.forEach(macThread -> macThread.interrupt());
		macThreads.forEach(macThreads -> {
			try {
				macThreads.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
 

	public String toString() {
		return machineName;
	}
}