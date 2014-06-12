public class Example158 {
  public static void main(String[] args) {
    Class<C2> c2o = C2.class;
    C2 o2 = new C2();
    Class c2o2 = o2.getClass(); // c20 == c202
    C1 o11 = new C1(), o12 = o2; // o11.getClass() != o12.getClass()
    if (o11.getClass() != o12.getClass()) {
      System.out.println("o11 and o12 have different run-time classes " +
          "but the same compile-time class");
    }
  }
}
