// Example 114 from page 85 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.awt.Color;

class Example114 {
  public static void main(String[] args) {
    LabelPoint<String> p1 = new LabelPoint<String>(5, 117, "home"), 
      p2 = new LabelPoint<String>(2, 3, "work");
    LabelPoint<Double> p3 = new LabelPoint<Double>(10, 100, 3.1415);
    ColorLabelPoint<String,Integer> p4 = 
      new ColorLabelPoint<String,Integer>(20, 30, "foo", 0x0000FF);
    ColorLabelPoint<String,Color> p5 = 
      new ColorLabelPoint<String,Color>(40, 50, "bar", Color.BLUE);
    Movable[] movables = { p1, p2, p3, p4, p5 };
    LabelPoint<String>[] stringpoints;                       // Legal
    // stringpoints = new LabelPoint<String>[3];             // Illegal
  }
}

interface Movable {
  void move(int dx, int dy);
}

class LabelPoint<L> implements Movable {
  protected int x, y;
  private L lab;

  public LabelPoint(int x, int y, L lab) {
    this.x = x; this.y = y; this.lab = lab;
  }

  public void move(int dx, int dy) { 
    x += dx; y += dy;
  }
  
  public L getLab() { 
    return lab; 
  }
}

class ColorLabelPoint<L, C> extends LabelPoint<L> {
  private C c; 

  public ColorLabelPoint(int x, int y, L lab, C c) {
    super(x, y, lab);
    this.c = c;
  }
}


