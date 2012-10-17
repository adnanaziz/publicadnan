// import the collections/libraries you are using
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
@Target(TYPE)
@Retention(RUNTIME)
//TODO(EE422C): update these to your name and eid
@interface Author {
  public String name() default  "John Snow" ;
  public String uteid() default  "js123" ;
}

@Author(name="Usain Bolt", uteid="ub959")
public class Skyline {
  public static List<Point> computeSkyline(List<Building> bldgList) {
	//TODOBEGIN(EE422C)

	//TODOEND(EE422C)
  }
	//TODOBEGIN(EE422C)
  // add your helper functions here
	//TODOEND(EE422C)
}

class Point {
  private int x;
  private int y;
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
  public int X() {
    return x;
  }
  public int Y() {
    return y;
  }
  public int setX() {
    return x;
  }
  public int setY() {
    return y;
  }
}

class Building {
}
