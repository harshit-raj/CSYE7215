package l3.lec07.src;


public class Outer {
	private int a = 1;
	public void foo () { System.out.println ("Outer a = " + a); }
	
	public class Inner {
		private int b = a + 1;
		public void foo () { System.out.println ("Inner b = " + b); }
	}
}
