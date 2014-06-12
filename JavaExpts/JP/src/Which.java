class A {
  String x = "A";

  String foo() {
    return ("A.foo");
  }
}

class B extends A {
  String x = "B";

  String foo() {
    return ("B.foo");
  }

  String superx() {
    return ("super.x = " + super.x + ", this.x = " + this.x);
  }
}

public class Which {
  public static void main(String[] args) {
    A a = new A();
    B b = new B();
    A c = b;
    A d = (A) b;
    B e = (B) d;

    System.out.println("c.x  = " + c.x);
    System.out.println("c.foo()  = " + c.foo());

    System.out.println("d.x  = " + d.x);
    System.out.println("d.foo()  = " + d.foo());

    System.out.println("e.x  = " + e.x);
    System.out.println("e.foo()  = " + e.foo());
    System.out.println("e.superx()  = " + e.superx());
  }
}
