// Example 8 from page 11 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example8 {
  public static void main(String[] args) {
    equalityAndPlus();
  }

  static void equalityAndPlus() {
    String s1 = "abc";
    String s2 = s1 + "";        // New object, but contains same text as s1
    String s3 = s1;             // Same object as s1
    String s4 = s1.toString();  // Same object as s1
    // The following statements print false, true, true, true, true:
    System.out.println("s1 and s2 identical objects: " + (s1 == s2)); 
    System.out.println("s1 and s3 identical objects: " + (s1 == s3)); 
    System.out.println("s1 and s4 identical objects: " + (s1 == s4)); 
    System.out.println("s1 and s2 contain same text: " + (s1.equals(s2))); 
    System.out.println("s1 and s3 contain same text: " + (s1.equals(s3))); 
    // These two statements print 35A and A1025 because (+) is left associative:
    System.out.println(10 + 25 + "A");  // Same as (10 + 25) + "A"
    System.out.println("A" + 10 + 25);  // Same as ("A" + 10) + 25
  }
}

