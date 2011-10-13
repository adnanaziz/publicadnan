// Example 95 from page 71 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;

class AnimatedCanvas extends Canvas implements Runnable {
  static final long serialVersionUID = 50L;
  private int scale = 50, step = 5;

  AnimatedCanvas() { Thread u = new Thread(this); u.start(); }

  public void run() {                           // from Runnable
    for (;;) { // forever sleep and repaint
      try { Thread.sleep(100); } catch (InterruptedException e) { }
      if (scale <= 0 || scale >= 100) 
        step = -step;
      scale += step;
      repaint();
    }
  }

  public void paint(Graphics g) {               // from Canvas
    Dimension size = getSize();
    g.fillRect(scale*(size.width-20)/100,                    0, 20, 20);
    g.fillRect((100-scale)*(size.width-20)/100, size.height-20, 20, 20);
  }

  public Dimension getPreferredSize() { return new Dimension(400, 100); }
  public Dimension getMinimumSize() { return getPreferredSize(); }
}

class Example95 {
  public static void main(String[] args) {
    Canvas anim1 = new AnimatedCanvas();
    Canvas anim2 = new AnimatedCanvas();
    Frame f = new Frame("Animation with threads");
    f.add(anim1, "North"); f.add(anim2, "South"); 
    f.pack(); f.setVisible(true);
  }
}

