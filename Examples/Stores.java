import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Random;
import java.util.Iterator;

public class Stores {
  public static void main(String[] args) {
    List<Rectangle> lr = new LinkedList<Rectangle>();
    Set<Rectangle> sr = new TreeSet<Rectangle>();
    // int N = 1000;
    // int D = 1000;
    int N = 15;
    int D = 7;

    Random r = new Random(0);
    for ( int i = 0 ; i < N; i++ ) {
      Rectangle tmp = new Rectangle( r.nextInt(D) + 1, r.nextInt(D) + 1 );
      lr.add( tmp );
      sr.add( tmp );
    }
    for ( int i = 0 ; i < N; i++ ) {
      Rectangle tmp = new Rectangle( r.nextInt(D) + 1, r.nextInt(D) + 1 );
      if ( !sr.contains( tmp ) && lr.contains( tmp ) ) {
        System.out.println(" problem case : " + tmp.L + "," + tmp.H );
        Iterator<Rectangle> iter = sr.iterator();
        while ( iter.hasNext() ) {
          Rectangle t = iter.next();
          System.out.println(" rectangle: " + t.L + "," + t.H );
        }
      }
    }
  }
}
