public class ArrayCasts {
  public static void main(String[] args) {
    Number [] narray = new Integer[3]; 
    Number p = new Integer(12); 
    narray[0] = p;
    // Java allows this to compile, but the assign 
    // hits a ClassCastException
    Number m = new Double(1.23D); 
    narray[1] = m;
  }
}
