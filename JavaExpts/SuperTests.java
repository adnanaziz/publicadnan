class Foo {
  public void whoami() { System.out.println("I am foo"); }
}

class Bar extends Foo {
  @Override
  public void whoami() { System.out.println("I am bar"); }
}

public class SuperTests {

  public static void main( String[] args ) {
    Bar x = new Bar();
    x.whoami();
    Foo y = (Bar) x;
    y.whoami();
  }
}
