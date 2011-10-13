// Example 94 from page 71 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Buffer {
  private int contents;
  private boolean empty = true;
  public int get() {
    synchronized (this) {
      while (empty)
        try { this.wait(); } catch (InterruptedException x) {};
      empty = true;
      this.notify();
      return contents;
  } }
  public void put(int v) {
    synchronized (this) {
      while (!empty)
        try { this.wait(); } catch (InterruptedException x) {};
      empty = false; 
      contents = v; 
      this.notify();
  } }
}

class Example94 {
  public static void main(String[] args) {
    final Buffer buf = new Buffer();

    class Producer extends Thread {
      public void run() {
        for (int i=1; true; i++) {
          buf.put(i);
          Util.pause(10, 100);
    } } }

    class Consumer extends Thread {
      public void run() {
        for (;;) 
          System.out.println("Consumed " + buf.get());

    } }

    new Producer().start(); 
    new Consumer().start(); new Consumer().start();
} }


