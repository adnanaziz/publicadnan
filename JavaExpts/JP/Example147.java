// Example 147 from page 123 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;

class Example147 {
  public static void main(String[] args) throws IOException {
    // Write: DataOutputStream implements DataOutput
    OutputStream os = new FileOutputStream("tmp1.dat");
    DataOutputStream daos = new DataOutputStream(os);
    writedata(daos);
    System.out.println("Wrote " + daos.size() + " bytes");
    daos.close();
    // Read: DataInputStream implements DataInput
    InputStream is = new FileInputStream("tmp1.dat");
    DataInputStream dais = new DataInputStream(is);
    readdata(dais);
    // Write and read: RandomAccessFile implements DataOutput and DataInput
    RandomAccessFile raf = new RandomAccessFile("tmp2.dat", "rw");
    writedata(raf);
    System.out.println("Wrote " + raf.getFilePointer() + " bytes");
    raf.seek(0);
    readdata(raf);
  }

  static void writedata(DataOutput out) throws IOException {
    out.writeBoolean(true);                             // Write 1 byte
    out.writeByte(120);                                 // Write 1 byte
    out.writeBytes("foo");                              // Write 3 bytes
    out.writeBytes("fo");                               // Write 2 bytes
    out.writeChar('A');                                 // Write 2 bytes
    out.writeChars("foo");                              // Write 6 bytes
    out.writeDouble(300.1);                             // Write 8 bytes 
    out.writeFloat(300.2F);                             // Write 4 bytes
    out.writeInt(1234);                                 // Write 4 bytes
    out.writeLong(12345L);                              // Write 8 bytes
    out.writeShort(32000);                              // Write 2 bytes
    out.writeUTF("foo");                                // Write 2 + 3 bytes
    out.writeUTF("Rh�ne");                              // Write 2 + 6 bytes
    out.writeByte(-1);                                  // Write 1 byte
    out.writeShort(-1);                                 // Write 2 bytes
  }

  static void readdata(DataInput in) throws IOException {
    byte[] buf1 = new byte[3];
    System.out.print(      in.readBoolean());           // Read 1 byte
    System.out.print(" " + in.readByte());              // Read 1 byte
    in.readFully(buf1);                                 // Read 3 bytes
    in.readFully(buf1, 0, 2);                           // Read 2 bytes
    System.out.print(" " + in.readChar());              // Read 2 bytes
    System.out.print(" " + in.readChar()+in.readChar()+in.readChar());
    System.out.print(" " + in.readDouble());            // Read 8 bytes 
    System.out.print(" " + in.readFloat());             // Read 4 bytes
    System.out.print(" " + in.readInt());               // Read 4 bytes
    System.out.print(" " + in.readLong());              // Read 8 bytes
    System.out.print(" " + in.readShort());             // Read 2 bytes
    System.out.print(" " + in.readUTF());               // Read 2 + 3 bytes
    System.out.print(" " + in.readUTF());               // Read 2 + 6 bytes
    System.out.print(" " + in.readUnsignedByte());      // Read 1 byte
    System.out.print(" " + in.readUnsignedShort());     // Read 2 bytes
    System.out.println();
  }
}

