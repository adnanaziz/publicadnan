// Example 107 from page 81 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.*;

/*

Since it is illegal to create an array by new T[SIZE] in class Log<T>,
we must pass a suitable array to the constructor.

Similarly, method getAll() could not return an array of type T[],
because it is not allowed to create such an array.  Also, it cannot
use res.toArray(log) to create an array with the right element type,
as that would overwrite the log array...

*/

class Log<T> {
  private final int size;
  private static int instanceCount = 0;
  private int count = 0;
  private T[] log;
  public Log(T[] log) { 
    this.log = log; 
    this.size = log.length;
    instanceCount++; 
  }
  public static int getInstanceCount() { return instanceCount; }
  public void add(T msg) { log[count++ % size] = msg; }
  public int getCount() { return count; }
  public T getLast() {
    // Return the last log entry, or null if nothing logged yet
    return count==0 ? null : log[(count-1)%size];
  }
  public void setLast(T value) {
    // Update the last log entry, or create one if nothing logged yet 
    if (count==0)
      log[count++] = value;
    else
      log[(count-1)%size] = value;
  }    
  public ArrayList<T> getAll() {
    ArrayList<T> res = new ArrayList<T>();
    int stop = Math.min(count, size);
    for (int i=0; i<stop; i++)
      res.add(log[(count-stop+i) % size]);
    return res;
  }
}

class Example107 {
  public static void main(String[] args) {
    Log<String> log1 = new Log<String>(new String[5]);
    log1.add("Reboot");
    log1.add("Coffee");
    Log<Date> log2 = new Log<Date>(new Date[5]);
    log2.add(new Date());                                       // now  
    log2.add(new Date(new Date().getTime() + 60*60*1000));      // now + 1 hour
    ArrayList<Date> dts = log2.getAll();
    // Printing both logs:
    for (String s : log1.getAll()) 
      System.out.print(s + "    ");
    System.out.println();
    for (Date dt : dts) 
      System.out.print(dt + "   ");
    System.out.println();
  }
}  

