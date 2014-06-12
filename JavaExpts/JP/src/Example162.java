import java.lang.reflect.*;

public class Example162 {
  public static void main(String[] args) {
    try {
      Class<C1> c1o = C1.class; 
      Constructor<C1> cc1o = c1o.getConstructor(int.class); // gets C1(int)
      Constructor cco = cc1o; 
      C1 c11 = cc1o.newInstance(42);    // compile time type is C1
      Object c12 = cco.newInstance(56);  // compile time type is C2
    } catch (Exception e) {
      System.out.println("Unexpected exception");
      e.printStackTrace();
    }
  }
}
