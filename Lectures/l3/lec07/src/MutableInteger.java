package l3.lec07.src;

public class MutableInteger {
	private int value;
	MutableInteger (int i) { value = i; }
	public synchronized int get () { return value; }
	public synchronized void set (int i) { value = i;}
}
