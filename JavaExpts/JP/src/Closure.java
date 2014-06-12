import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class Closure {
  public static void test(ArrayList<String> A,
      final Map<String, Integer> freqMap) {
    Collections.sort(A, new Comparator<String>() {
      public int compare(String a, String b) {
        // return a.toUpperCase().compareTo(b.toUpperCase());
        freqMap.put(a + ":" + b, 10);
        return freqMap.get(a).compareTo(freqMap.get(b));
      }
    }
        );
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
    for (String s : freqMap.keySet()) {
      System.out.println(s);
    }
  }
}
