public class ObjectCreation {
  public static void main(String[] args) {
    int N = new Integer(args[0]);
    long startTime = System.nanoTime();
    long dummy = 0;
    for (int i = 0; i < N; i++) {
      String s = new String("foobar");
      dummy += s.length();
    }
    long finishTime = System.nanoTime();
    System.out.println("with allocation: " + (finishTime - startTime));
    startTime = System.nanoTime();
    dummy = 0;
    for (int i = 0; i < N; i++) {
      String s = "foobar";
      dummy += s.length();
    }
    finishTime = System.nanoTime();
    System.out.println("with allocation: " + (finishTime - startTime));
  }
}
