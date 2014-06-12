import java.lang.reflect.*;

public class Example160 {
  public static void main(String[] args) {
    try {
      Class<C2> c2o = C2.class;
      Field f1o = c2o.getField("f1");
      C2 o2 = new C2();
      f1o.set(o2, 117);
      System.out.format("Value of o2.f1 = %d%n", f1o.get(o2));
      Class<C1> c1o = C1.class;
      Field f21 = c1o.getDeclaredField("f2");
      try {
        Field f22 = c2o.getDeclaredField("f2");
      } catch (NoSuchFieldException e) {
        System.out
            .println("As expected, c2o.getDeclaredField(\"f2\") threw NoSuchFieldException");
      }
    } catch (Exception e) {
      System.out.println("Unexpected exception");
      e.printStackTrace();
    }
  }
}
