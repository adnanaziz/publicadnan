import java.lang.reflect.*;

class C1 {
  public int f1;
  protected int f2;
  public C1() { }
  public C1(int f1) { this.f1 = f1; }
  public void m1() { System.out.println("C1.m1()"); }
  public void m1(int i) { System.out.println("C1.m1(int)"); }
  public void m2() { System.out.println("C1.m1()"); }
}

class C2 extends C1 {
  public void m3() { System.out.println("C2.m3()"); }
  private void m4() { System.out.println("C2.m4()"); }
}

class Reflection1 {
  public static void main(String [] args) {
    Class<C2> c2o = C2.class;
    Method[] mop = c2o.getMethods(); // gets m1() m1(int) m3() m4()
    Method[] mod = c2o.getDeclaredMethods(); // gets m3() m4()
    for ( Method m : mop ) {
      System.out.println("mop method: " + m);
    }
    for ( Method m : mod ) {
      System.out.println("mod method: " + m);
    }
  }
}
