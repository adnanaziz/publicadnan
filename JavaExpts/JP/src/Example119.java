// Example 119 from page 91 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class ErasedHold { 
  private Object contents;

  public void set(Object x) { 
    contents = x;                       // Note: no cast
  }

  public Object get() { 
    return contents;
  }
}

class Example119 {
  public static void main(String[] args) {
    ErasedHold h = new ErasedHold();
    h.set("foo");                       // Succeeds at run-time
    System.out.println("Succesfully executed  h.set(\"foo\")");
    h.get();                            // Succeeds at run-time
    System.out.println("Succesfully executed  h.get()");
    // String s = h.get();              // Illegal, rejected by compiler
    Integer i = (Integer)h.get();       // Legal, but fails at run-time
    System.out.println("Succesfully executed  Integer i = h.get()");
  }
}

