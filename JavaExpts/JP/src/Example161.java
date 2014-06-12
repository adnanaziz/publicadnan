import java.lang.reflect.*;

public class Example161 {
  public static void main(String[] args) {
    try {
      Class<C1> c1o = C1.class;
      Method m1o = c1o.getMethod("m1"); // gets C1.m1();
      Method m1io = c1o.getMethod("m1", int.class); // gets C1.m1(int);
      C2 o2 = new C2();
      m1o.invoke(o2); // Call o2.m1();
      m1io.invoke(o2, 117); // Call o2.m1(117);
    } catch (Exception e) {
      System.out.println("Unexpected exception");
      e.printStackTrace();
    }
  }
}
