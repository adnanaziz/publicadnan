// Example 79 from page 55 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example79 {
  public static void main(String[] args) {  
    if (args.length != 1) 
      System.out.println("Usage: java Example79 <string>\n");
    else {
      String q = args[0];
      System.out.println(q + " substring of hjsdfk: " + substring2(q, "hjsdfk"));
    }
  }

  // Decide whether query is a substring of target (using break)

  static boolean substring2(String query, String target) {
    for (int j=0; j<=target.length()-query.length(); j++) 
      thisposition: {
        for (int k=0; k<query.length(); k++) 
          if (target.charAt(j+k) != query.charAt(k))
            break thisposition;
        return true;
      }
    return false;
  }
}

