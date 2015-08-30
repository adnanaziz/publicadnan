import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

class Student implements Comparable<Student> {

  static class StudentAgeComparator implements Comparator<Student> {
    private StudentAgeComparator() {
    }
    private static StudentAgeComparator COMPARE_OBJECT = new StudentAgeComparator();
    public int compare(Student s1, Student s2) {
      return s1.age - s2.age;
    }
  }

  public int age;
  public String name;

  public Student( int age, String name ) {
    this.age = age;
    this.name = name;
  }

  public int compareTo(Student s) {
    return this.name.compareTo( s.name );
  }

  @Override 
  public String toString() {
    return "" + age + "," + name;
  }

  public static void main(String[] args) {
  
    ArrayList<Student> A = new ArrayList<>();

    A.add( new Student( 29, "Adam Smith"));
    A.add( new Student( 27, "Thomas Jefferson"));
    A.add( new Student( 24, "George Washington"));

    // Compare on name using Student's compareTo()
    ArrayList<Student> Adup = new ArrayList<>(A);
    Collections.sort(Adup);
    System.out.println("By name");
    for ( Student s : Adup ) {
      System.out.println(s);
    }

    // Compare on age using explicit comparator
    Adup = new ArrayList<>(A);
    Collections.sort(Adup, StudentAgeComparator.COMPARE_OBJECT);
    System.out.println("By age");
    for ( Student s : Adup ) {
      System.out.println(s);
    }

    // Compare on second name using anonymous class
    Adup = new ArrayList<>(A);
    final List<String> comparisonsMade = new ArrayList<>();
    Collections.sort(Adup, new Comparator<Student>() {
      // Assuming all students names are of the form Foo Bar
      public int compare(Student s1, Student s2) {
        String s1Last = s1.name.split(" ")[1];
        String s2Last = s2.name.split(" ")[1];
        return s1Last.compareTo(s2Last);
      }
    });
    System.out.println("By second name");
    for ( Student s : Adup ) {
      System.out.println(s);
    }

    // Compare with GPA, pass in via closures
    Adup = new ArrayList<>(A);
    final Map<String, Double> GPAs = new HashMap<>();
    GPAs.put("Adam Smith", 3.95);
    GPAs.put("Thomas Jefferson", 4.30);
    GPAs.put("George Washington", 3.90);

    Collections.sort(Adup, new Comparator<Student>() {
      public int compare(Student s1, Student s2) {
        comparisonsMade.add(s1.name + ":" + s2.name);
        if (GPAs.get(s1.name) == GPAs.get(s2.name)) {
          return 0;
        } else if (GPAs.get(s1.name) < GPAs.get(s2.name)) {
          return -1;
        } else {
          return 1;
        }
      } 
    });
    System.out.println("By GPA");
    for ( Student s : Adup ) {
      System.out.println(s);
    }
    for ( String comparison : comparisonsMade ) {
      System.out.println(comparison);
    }

  }

}

public class Closure {

  public static void test(ArrayList<String> A,
      final Map<String, Integer> freqMap) {
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
