// Example 28 from page 23 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example28 {
   public static void main(String[] args) {
     Vessel v1 = new Barrel(3, 10);
     Vessel v2 = new Tank(10, 20, 12);
     Vessel v3 = new Cube(4);
     Vessel[] vs = { v1, v2, v3 };
     v1.fill(90); v1.fill(10); v2.fill(100); v3.fill(80);
     double sum = 0;
     for (int i=0; i<vs.length; i++)
       sum += vs[i].capacity();
     System.out.println("Total capacity is " + sum);
     for (int i=0; i<vs.length; i++)
       System.out.println("vessel number " + i + ": " + vs[i]);
   }
}

