import java.io.*;

public class ExceptionTypes {

  public static void doThis() {
    try {
      NonRTException.entry();
      RTException.entry();
    } catch (RuntimeException e) {
      e.printStackTrace();
    // } catch (IOException e) {
    //   e.printStackTrace();
    }
  }

  public static void main(String[] args) {
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
