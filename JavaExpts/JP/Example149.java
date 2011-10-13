// Example 149 from page 125 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;

class Example149 {
  public static void main(String[] args) 
    throws IOException, ClassNotFoundException {
    File f = new File("objects2.dat");
    if (!f.exists()) {
      System.out.println("Creating objects and writing them to file:"); 
      SC c = new SC();
      SO o1 = new SO(1, c), o2 = new SO(2, c); 
      o1.c.ci = 3; o2.c.ci = 4;                 // update the shared c twice
      o1.cprint(); o2.cprint();                 // prints i1c4 i2c4
      OutputStream os = new FileOutputStream(f);
      ObjectOutputStream oos1 = new ObjectOutputStream(os);
      oos1.writeObject(o1); oos1.flush();
      ObjectOutputStream oos2 = new ObjectOutputStream(os);
      oos2.writeObject(o2); oos2.close();
      System.out.println("\nRun the example again to read objects from file");
    } else {
      System.out.println("Reading objects from file (non-shared c):");
      InputStream is = new FileInputStream(f);
      ObjectInputStream ois1 = new ObjectInputStream(is);
      SO o1i = (SO)(ois1.readObject());
      ObjectInputStream ois2 = new ObjectInputStream(is);
      SO o2i = (SO)(ois2.readObject());
      o1i.cprint(); o2i.cprint();               // prints i1c4 i2c4
      o1i.c.ci = 5; o2i.c.ci = 6;               // update two different c's
      o1i.cprint(); o2i.cprint();               // prints i1c5 i2c6
      f.delete();
    }
    System.out.println();
  }
}

