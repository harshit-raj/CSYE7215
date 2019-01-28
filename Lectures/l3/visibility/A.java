package l3.visibility;

// This is code downloaded from:
// http://stackoverflow.com/questions/16178020/uninitialized-object-leaked-to-another-thread-despite-no-code-explicitly-leaking
// It is supposed to demonstrate visibility issues due to reordering
// However, I have not been able to observe the behavior as described by the authors.
// Probably because you need multiple processors to see this. 

import java.util.*;

class A {
    static B b;
    static class B {
        int x;
        B(int x) {
            this.x = x;
        }
    }
    public static void main(String[] args) {
        new Thread() {
            void f(B q) {
                int x = q.x;
                if (x != 1) {
                    System.out.println(x);
                    System.exit(1);
                }
            }
            @Override
            public void run() {
                while (b == null);
                while (true) f(b);
            }
        }.start();
        for (int x = 0;;x++)
            b = new B(Math.max(x%2,1));
    }
}
