// import the collections/libraries you are using
// use anything you'd like from the JDK
import java.util.List;
//TODOBEGIN(EE422C)
//TODOEND(EE422C)

public class Skyline {
  public static List<Integer> computeSkyline(List<Building> bldgList) {
    //TODOBEGIN(EE422C)
    return null;
    //TODOEND(EE422C)
  }
    //TODOBEGIN(EE422C)
    // add your helper functions here
    //TODOEND(EE422C)
}

class Building {
  private int left;
  private int height;
  private int right;
  Building(int left, int height, int right) {
    this.left = left;
    this.height = height;
    this.right = right;
  }
  public int left() { return left; }
  public int height() { return height; }
  public int width() { return right; }
}
