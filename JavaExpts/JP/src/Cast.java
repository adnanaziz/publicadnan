class Point {
  public int x;
  public int y;
}

class PointWithZ extends Point {
  public int z;
}

public class Cast {
  public static void main(String[] args) {
    Point a = new Point();
    Point b = new PointWithZ();
    PointWithZ z = new PointWithZ();
    if (z instanceof Point) {
      System.out.println("z is instanceof Point");
    }
    Point p = z;
    if (p instanceof Point) {
      System.out.println("p is instanceof Point");
    }
    Point q = (Point) z;
    if (q instanceof PointWithZ) {
      System.out.println("q is instanceof PointWithZ");
    }
    if (a instanceof PointWithZ) {
      System.out.println("a is instanceof PointWithZ");
    }
    if (b instanceof PointWithZ) {
      System.out.println("b is instanceof PointWithZ");
    }
  }
}
