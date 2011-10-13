// Example 90 from page 65 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


// To exercise all paths through the try-catch-finally statement in
// method m, run this program with each of these arguments: 
// 101 102 103 201 202 203 301 302 303 411 412 413 421 422 423 431 432 433
// like this:
//    java Example90 101
//    java Example90 102
//    etc

class Example90 {
  public static void main(String[] args) throws Exception { 
    if (args.length != 1) 
      System.out.println("Usage: java Example90 <integer>\n");
    else
      System.out.println(m(Integer.parseInt(args[0]))); 
  }

  static String m(int a) throws Exception {
    try {
      System.out.print("try ... ");
      if (a/100 == 2) return "returned from try";
      if (a/100 == 3) throw new Exception("thrown by try");
      if (a/100 == 4) throw new RuntimeException("thrown by try");
    } catch (RuntimeException x) {
      System.out.print("catch ... ");
      if (a/10%10 == 2) return "returned from catch";
      if (a/10%10 == 3) throw new Exception("thrown by catch");
    } finally {
      System.out.println("finally");
      if (a%10 == 2) return "returned from finally";
      if (a%10 == 3) throw new Exception("thrown by finally");
    }
    return "terminated normally with " + a;
  }
}

