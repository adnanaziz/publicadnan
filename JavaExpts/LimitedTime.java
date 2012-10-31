import java.math.BigInteger;
import java.util.Random;

public class LimitedTime {
  public static void main(String[] args) {
    Worker w = new Worker();
    w.start();
    try {
      Thread.sleep(1000);
      System.out.println("done sleeping");
      w.interrupt();
      System.out.println("interrupting w");
      Thread.sleep(1000);
      w.interrupt();
      w.join();
      System.out.println("main has succ joined on w");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

class Worker extends Thread {
  public void run() {
    int i = 2;
    Random r = new Random();
    boolean go = true;
    while ( go ) {
        BigInteger p = BigInteger.probablePrime(i++, r);
        System.out.println("Prime " + i + ":" + p.toString());
	if ( this.isInterrupted()) { // does not clear interrupted status
	  go = !this.interrupted(); // clears interrupted status
	}
    }
    System.out.println("w has stopped prime computations");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      System.out.println("Woke up from sleep via InterruptedException");
      // e.printStackTrace();
    }
  }
}
