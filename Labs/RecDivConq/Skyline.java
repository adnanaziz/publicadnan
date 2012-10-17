// import the collections/libraries you are using
// use anything you'd like from the JDK
import java.util.List;
//TODOBEGIN(EE422C)
//TODOEND(EE422C)

public class Skyline {
  public static List<Point> computeSkyline(List<Building> bldgList) {
    //TODOBEGIN(EE422C)
    return null;
    //TODOEND(EE422C)
  }
    //TODOBEGIN(EE422C)
    // add your helper functions here
    //TODOEND(EE422C)
}

class Point {
  private int x;
  private int y;
  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
  public int X() { return x; }
  public int Y() { return y; }
}

class Building {
  private int left;
  private int height;
  private int width;
  Building(int left, int height, int width) {
    this.left = left;
    this.height = height;
    this.width = width;
  }
  public int left() { return left; }
  public int height() { return height; }
  public int width() { return width; }
}
