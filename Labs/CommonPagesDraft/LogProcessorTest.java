import org.junit.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

public class LogProcessorTest  {

  @Test
  public void tmp0() {
    LogProcessor lp = new LogProcessorFast(1);
    lp.add( "foo", 1 );
    lp.add( "bar", 2 );
    lp.add( "foo", 3 );
    System.out.println(lp);
    List<String> r = lp.getOrderedUrlsInWindow(2);
    assertEquals(r.get(0), "bar:1");
  }

  public static void util0(LogProcessor lp) {
    lp.add( "foo", 1 );
    lp.add( "bar", 2 );
    lp.add( "foo", 3 );
    List<String> r0 = lp.getOrderedUrlsInWindow(2);
    assertEquals(r0.get(0), "foo:2");
    assertEquals(r0.get(1), "bar:1");

    lp.add( "widget", 4 );
    r0 = lp.getOrderedUrlsInWindow(3);
    assertEquals("bar:1", r0.get(0));
    assertEquals("foo:1", r0.get(1));
    assertEquals("widget:1", r0.get(2));
    
    lp.add( "foo", 4 );
    r0 = lp.getOrderedUrlsInWindow(3);
    assertEquals(r0.get(0), "foo:2");
    assertEquals(r0.get(1), "bar:1");
    assertEquals(r0.get(2), "widget:1");
  }

  @Test
  public void t0() {
    LogProcessor lp = new LogProcessorSlow(2);
    util0(lp);
    lp = new LogProcessorFast(2);
    util0(lp);
  }


  public void util1(LogProcessor lp) {
    String[] urls = {"a","b","c","d"};
    for ( int i = 0 ; i < 1000; i++ ) {
      lp.add( urls[ i % urls.length ], i);
    }
    List<String> r1 = lp.getOrderedUrlsInWindow(1);
    assertEquals(r1.get(0), "a:1");
    r1 = lp.getOrderedUrlsInWindow(4);
    assertEquals(r1.get(3), "d:1");
  }

  @Test 
  public void t1() {
    LogProcessor lp = new LogProcessorSlow(3);
    util1(lp);
    lp = new LogProcessorFast(3);
    util1(lp);
  }

  public void util2(LogProcessor lp) {
    String[] urls = {"a","b","c","d", "c", "d", "d"};
    Random rnd = new Random(0);
    for ( int i = 0 ; i < 100000; i++ ) {
      int index = rnd.nextInt(urls.length);
      lp.add( urls[ index ], i);
    }
    List<String> r2 = lp.getOrderedUrlsInWindow(1);
    assertEquals("d", r2.get(0).substring(0,1));
    r2 = lp.getOrderedUrlsInWindow(2);
    assertEquals("c", r2.get(1).substring(0,1));
  }


  @Test(timeout=1000)
  public void t2() {
    LogProcessor lp = new LogProcessorSlow(Integer.MAX_VALUE);
    util2(lp);
  }

  @Test(timeout=1000)
  public void t3() {
    LogProcessor lp = new LogProcessorFast(Integer.MAX_VALUE);
    util2(lp);
  }

}
