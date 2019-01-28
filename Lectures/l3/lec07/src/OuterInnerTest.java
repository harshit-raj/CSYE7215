package l3.lec07.src;

import java.lang.reflect.Field;

// This tests accessing an outer object from an inner one.
// Inside the code of the inner one, you can use "Outer.this".
// This is how you access the outer object from outside the
// code of the inner object.  Credit to:
// http://stackoverflow.com/questions/763543/in-java-how-do-i-access-the-outer-class-when-im-not-in-the-inner-class

public class OuterInnerTest {

	public static void main(String[] args) {
		Outer.Inner v = new Outer().new Inner();
		v.foo ();
	    try {
	    	Field outerThis = v.getClass().getDeclaredField("this$0");
			Outer u = (Outer)outerThis.get(v);
			u.foo();
	    } catch (NoSuchFieldException e) {
	    	throw new RuntimeException(e); 
	    }  catch (IllegalAccessException e) {
	    	throw new RuntimeException(e);
	    }
	}

}
