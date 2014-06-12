import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class Defer {
  public static void test(final ArrayList<String> A,
      final Map<String, Integer> freqMap) {
    final ArrayList<String> B = new ArrayList<>();
    B.add("b1");
    B.add("b2");
    B.add("b3");
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(1000);
        } catch (Exception e) {
          e.printStackTrace();
        }
        Collections.sort(A, new Comparator<String>() {
          public int compare(String a, String b) {
            // return a.toUpperCase().compareTo(b.toUpperCase());
            freqMap.put(a + ":" + b, 10);
            return freqMap.get(a).compareTo(freqMap.get(b));
          }
        }
            );
        B.add("b4");
        for (String s : B) {
          System.out.println(s);
        }
      }
    });
    thread.start();
  }

  public static void main(String[] args) {
    ArrayList<String> A = new ArrayList<>();
    A.add("foo");
    A.add("BAR");
    A.add("widget");
    A.add("Alpha");
    Map<String, Integer> freqMap = new HashMap<>();
    freqMap.put("foo", 12);
    freqMap.put("BAR", 19);
    freqMap.put("widget", 10);
    freqMap.put("Alpha", 1);
    test(A, freqMap);
    for (String s : A) {
      System.out.println(s);
    }
    try {
      Thread.sleep(1500);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("map keys-->");
    for (String s : freqMap.keySet()) {
      System.out.println(s);
    }
    System.out.println("<--map keys");
  }
}
