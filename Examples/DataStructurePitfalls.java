import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;

class Point {
  public int x;
  public int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    Point p = (Point) o; // lazy check...
    return p.x == x && p.y == y;
  }
}


public class DataStructurePitfalls {
  public static void main(String[] args) {
    List<Point> aList = new ArrayList<>();
    Set<Point> hSet = new HashSet<>();

    Point p = new Point(1,2);
    Point q = new Point(100,100);

    aList.add( p );
    aList.add( q );

    hSet.add( p );
    hSet.add( q );

    if ( !aList.contains( p ) ) {
      System.out.println("aList does not contain p!");
    }

    if ( !hSet.contains( p ) ) {
      System.out.println("hSet does not contain p!");
    }

    if ( !aList.contains( new Point(1,2 ) ) ) {
      System.out.println("aList does not contain (1,2)!");
    }

    if ( !hSet.contains( new Point(1, 2) ) ) {
      System.out.println("hSet does not contain (1,2)!");
    }

  }
}
