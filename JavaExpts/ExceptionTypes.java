import java.io.*;
import java.util.*;

public class ExceptionTypes {

  public static void doThis() {
    try {
      RTException.entry();
      NonRTException.entry();
    //} catch (RuntimeException e) {
    //  e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Integer x = 123;
    List<Integer> y = new ArrayList<>();
    if (x == y) {
    }
    ExceptionTypes.doThis();
  }

}

class RTException {
  public static void entry() {
    RuntimeException e = new RuntimeException("RTException");
    throw e;
  }
}

class NonRTException {
  public static void entry() throws IOException {
    IOException e = new IOException("IOException");
    throw e;
  }
}
