import java.lang.reflect.*;

public class Example163 {
  public static void main(String[] args) {
    Method[] mod = C2.class.getMethods();
    for (Method m : mod) {
      if (Modifier.isPrivate(m.getModifiers())) System.out.println(m);
    }
  }
}
