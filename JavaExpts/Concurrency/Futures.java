import java.util.concurrent.*;
import java.util.*;

class MyCallable implements Callable<String> {
  private int delta;
  private String s;
  public MyCallable(int delta, String s) {
    this.delta = delta;
    this.s = s;
  }
  @Override 
  public String call() throws Exception {
    Thread.sleep(delta);
    return "Dummy result for " + s;
  }
}

public class Futures {
  public static void main(String[] args) throws Exception {
    final Map<String,FutureTask<String>> cache = new HashMap<>();

    MyCallable c = new MyCallable(10000, "one");
    final FutureTask<String> ft1 = new FutureTask<String>(c); 
    (new Thread( new Runnable() {
      public void run() {
        ft1.run();
      }
    })).start();

    cache.put("one", ft1);

    c = new MyCallable(1000, "two");
    final FutureTask<String> ft2 = new FutureTask<String>(c); 
    (new Thread( new Runnable() {
      public void run() {
        ft2.run();
      }
    })).start();

    cache.put("two", ft2);

    (new Thread( new Runnable() {
      public void run() {
        try {
          System.out.println("one:" + cache.get("one").get());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    })).start();

    (new Thread( new Runnable() {
      public void run() {
        try {
          System.out.println("two:" + cache.get("two").get());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    })).start();


  }
}
