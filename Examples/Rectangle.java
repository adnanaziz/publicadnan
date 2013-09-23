import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Random;

class Rectangle implements Comparable {
  int L;
  int H;
  int area;

  public Rectangle(int L, int H) {
    this.L = L;
    this.H = H;
    this.area = L*H;
  }

  @Override
  public boolean equals( Object o ) {
    if ( o == null ) {
      return false;
    }
    if ( !(o instanceof Rectangle) ) {
      return false;
    }
    Rectangle ro = (Rectangle) o;
    return (ro.L == L && ro.H == H);
  }

  public int compareTo( Object o ) {
    Rectangle ro = (Rectangle) o;
    if ( ro.H == H && ro.L == L ) {
      return 0;
    } else if ( ro.L < L || ro.H < H ) {
      return -1;
    } else {
      return 1;
    }
  }
}
