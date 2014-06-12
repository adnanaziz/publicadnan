// Example 141 from page 113 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;

class Example141 {
  public static void main(String[] args) 
    throws IOException, ClassNotFoundException {
    // Write numbers, words etc on file "f.txt" in human-readable form:
    PrintWriter pwr = new PrintWriter(new FileWriter("f.txt"));
    pwr.print(4711); pwr.print(' '); pwr.print("cool"); pwr.close();
    // Read numbers and words from human-readable text file "f.txt":
    StreamTokenizer stok = new StreamTokenizer(new FileReader("f.txt"));
    int tok = stok.nextToken();
    while (tok != StreamTokenizer.TT_EOF) 
      { System.out.println(stok.sval); tok = stok.nextToken(); }
    // Write primitive values to a binary file "p.dat":
    DataOutputStream dos = new DataOutputStream(new FileOutputStream("p.dat"));
    dos.writeInt(4711); dos.writeChar(' '); dos.writeUTF("cool"); dos.close();
    // Read primitive values from binary file "p.dat":
    DataInputStream dis = new DataInputStream(new FileInputStream("p.dat"));
    System.out.println(dis.readInt()+"|"+dis.readChar()+"|"+ dis.readUTF());
    // Write an object or array to binary file "o.dat":
    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("o.dat"));
    oos.writeObject(new int[] { 2, 3, 5, 7, 11 }); oos.close();
    // Read objects or arrays from binary file "o.dat":
    ObjectInputStream ois = new ObjectInputStream(new FileInputStream("o.dat"));
    int[] ia = (int[])(ois.readObject());
    System.out.println(ia[0]+","+ia[1]+","+ia[2]+","+ia[3]+","+ia[4]);
    // Read and write parts of file "raf.dat" in arbitrary order:
    RandomAccessFile raf = new RandomAccessFile("raf.dat", "rw");
    raf.writeDouble(3.1415); raf.writeInt(42); 
    raf.seek(0); System.out.println(raf.readDouble() + " " + raf.readInt());
    // Read from a String s as if it were a text file:
    Reader r = new StringReader("abc");
    System.out.println("abc: " + (char)r.read() + (char)r.read() + (char)r.read());
    // Write to a StringBuilder as if it were a text file:
    Writer sw = new StringWriter();
    sw.write('d'); sw.write('e'); sw.write('f'); 
    System.out.println(sw.toString());
    // Write characters to standard output and standard error:
    System.out.println("std output"); System.err.println("std error");
    // Read characters from standard input (the keyboard):
    System.out.print("Type some characters and press Enter: "); 
    BufferedReader bisr = new BufferedReader(new InputStreamReader(System.in));
    String response = bisr.readLine();
    System.out.println("You typed: '" + response + "'");
    // Read a byte from standard input (the keyboard):
    System.out.print("Type one character and press Enter: "); 
    byte b = (byte)System.in.read();
    System.out.println("First byte of your input is: " + b);
  }
}

