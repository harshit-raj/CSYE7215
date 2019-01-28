package l3.src.threadLocal;

public class ManagerDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ManagerThread t1 = new ManagerThread ("t1", 2);
		ManagerThread t2 = new ManagerThread ("t2", 5);
		t1.start();
		t2.start();
	}

}
