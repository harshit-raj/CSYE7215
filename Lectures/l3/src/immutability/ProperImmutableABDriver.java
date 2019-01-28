package l3.src.immutability;

public class ProperImmutableABDriver {

	public static void main(String[] args) {
		ProperImmutableAB ab = new ProperImmutableAB (2,3);
		new ThreadABPrinter (ab).start();
		System.out.println (ab);

	}

}
