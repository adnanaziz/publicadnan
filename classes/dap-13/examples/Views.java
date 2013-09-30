/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

class Building {
  int distToBeach;
  int height;
  Building(int d, int h) {
    distToBeach = d;
    height = h;
  }
  @Override
  public String toString() {
    return "distance to beach = " + distToBeach + ";" + "height = " + height;
  }
}

class Views
{
  // assume adding with building that's closer to the beach than buildings
  // in current set
  static void add(Stack<Building> withView, Building b) {
    while ( !withView.empty() && withView.peek().height < b.height ) {
       withView.pop();
    }
    withView.push(b);
  }

  static void show(Stack<Building> withView) {
    while ( !withView.empty() ) {
       System.out.println("Building : " + withView.pop());
    }
  }
  public static void main (String[] args) throws java.lang.Exception
  {
    Stack<Building> withView = new Stack<Building>();
    add(withView, new Building(6,9));
    add(withView, new Building(5,3));
    add(withView, new Building(4,8));
    add(withView, new Building(3,3));
    add(withView, new Building(2,2));
    add(withView, new Building(1,1));
    show( withView );
  }
}
