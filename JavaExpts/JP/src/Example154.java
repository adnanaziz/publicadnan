// Example 154 from page 131 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;

class Example154 {
  public static void main(String[] args) throws IOException {
    if (args.length > 0)
      showDir(0, new File(args[0]));
    else
      showDir(0, new File(".."));
  }

  static void showDir(int indent, File file) throws IOException {
    for (int i=0; i<indent; i++)
      System.out.print('-');
    System.out.println(file.getName());
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (int i=0; i<files.length; i++)
        showDir(indent+4, files[i]);
    } 
  }
}

