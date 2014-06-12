public class Example159 {
  public static void main(String[] args) {
    Class<C2> c2o = C2.class;
    C2 o2 = new C2();
    C1 o11 = new C1(), o12 = o2; // o11.getClass() != o12.getClass()
    try {
      C2 o21 = c2o.newInstance(); // well-typed: c20 has type Class<C2>
    } catch (InstantiationException e) {
      System.out.println("InstantiationException should never happen");
    } catch (IllegalAccessException e) {
      System.out.println("IllegalAccessException should never happen");
    }
    if (c2o.isInstance(o12)) {
      System.out.println("o12 is instance of C2");
    }
    if (!c2o.isInstance(o11)) {
      System.out.println("o11 is not instance of C2");
    }
    C2 c22 = c2o.cast(o12); // succeeds at runtime since o12 can be cast to C2
    try {
      C2 c23 = c2o.cast("foo");
    } catch (ClassCastException e) {
      System.out.println("Cast from \"foo\" to type C2 failed at runtime");
    }
  }
}
