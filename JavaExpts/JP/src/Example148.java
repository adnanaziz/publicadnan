// Example 148 from page 125 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;

class SC implements Serializable { 
    static final long serialVersionUID = 50L;
    int ci; 
}

class SO implements Serializable {
    static final long serialVersionUID = 50L;
  int i;
  SC c;

  SO(int i, SC c) { this.i = i; this.c = c; }

  void cprint() { System.out.print("i" + i + "c" + c.ci + " "); }
}

class Example148 {
  public static void main(String[] args) 
    throws IOException, ClassNotFoundException {
    File f = new File("objects1.dat");
    if (!f.exists()) {
      System.out.println("Creating objects and writing them to file:"); 
      SC c = new SC();
      SO o1 = new SO(1, c), o2 = new SO(2, c); 
      o1.c.ci = 3; o2.c.ci = 4;                 // update the shared c twice
      o1.cprint(); o2.cprint();                 // prints i1c4 i2c4
      OutputStream os = new FileOutputStream(f);
      ObjectOutputStream oos = new ObjectOutputStream(os);
      oos.writeObject(o1); oos.writeObject(o2); oos.close();
      System.out.println("\nRun the example again to read objects from file");
    } else {
      System.out.println("Reading objects from file (shared c):");
      InputStream is = new FileInputStream(f);
      ObjectInputStream ois = new ObjectInputStream(is);
      SO o1i = (SO)(ois.readObject()), o2i = (SO)(ois.readObject());
      o1i.cprint(); o2i.cprint();               // prints i1c4 i2c4
      o1i.c.ci = 5; o2i.c.ci = 6;               // update the shared c twice
      o1i.cprint(); o2i.cprint();               // prints i1c6 i2c6
      f.delete();
    }
    System.out.println();
  }
}

