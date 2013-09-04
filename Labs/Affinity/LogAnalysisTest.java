import static org.junit.Assert.*;

import org.junit.Test;
import java.util.Random;

public class LogAnalysisTest {
  
  @Test
  public void simple1() {
    String s = "1,foo,adnan\n2,bar,john\n3,bar,adnan\n";
    String r = new LogAnalysis().highestAffinityPair(s);
    assertEquals(r,"bar,foo");
  }

  static String[] pages2 = {"a","b","c","d","e"};
  static String[] persons2 = {"a","b","c","d","e","f","g"};

  @Test
  public void complex1() {
    StringBuffer sb = new StringBuffer();
    Random rnd = new Random(0);
    for ( int i = 0 ; i < 10000; i++ ) {
      // biasing page views to "c" and "e"
      if ( rnd.nextInt( 2 ) == 0 ) {
        sb.append(i + "," 
          + pages2[2] + "," 
          + persons2[ rnd.nextInt(persons2.length)] + "\n");
      } else if ( rnd.nextInt( 2 ) == 0 ) {
        sb.append(i + "," 
          + pages2[4] + "," 
          + persons2[ rnd.nextInt(persons2.length)] + "\n");
      } else {
        sb.append(i + "," 
          + pages2[rnd.nextInt(pages2.length)] + "," 
          + persons2[ rnd.nextInt(persons2.length)] + "\n");
      }
    }
    String s = sb.toString();
    String r = new LogAnalysis().highestAffinityPair(s);
    assertEquals(r,"c,e");
  }

  @Test
  public void complex2() {
    StringBuffer sb = new StringBuffer();
    for ( int i = 0 ; i < 10000; i++ ) {
      sb.append(i + "," 
        + (i % 100) + ","  
        + (i % 100) + "\n");
    }
    sb.append(10001 + "," + 13 + "," + 0 + "\n");
    sb.append(10002 + "," + 91 + "," + 0 + "\n");
    sb.append(10003 + "," + 13 + "," + 1 + "\n");
    sb.append(10004 + "," + 91 + "," + 1 + "\n");
    String s = sb.toString();
    String r = new LogAnalysis().highestAffinityPair(s);
    assertEquals(r,"13,91");
  }

}

