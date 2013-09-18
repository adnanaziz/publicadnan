import static org.junit.Assert.*;

import org.junit.Test;
import java.util.Random;

public class LogAnalysisTest {
  
  @Test
  public void Simple1() {
    String s = "1,foo,adnan\n2,bar,john\n3,bar,adnan\n";
    String r = new LogAnalysis().highestAffinityPair(s);
    assertEquals(r,"bar,foo");
  }
  @Test
  public void Simple2() {
    String s = "1,lifehacker.com,Hector\n2,utexas.edu,Hector\n3,google.com,Betty\n4,gmail.com,Robert\n5,yahoo.com,May\n6,att.com,Bob\n7,utexas.edu,Sue\n8,att.com,James\n9,utexas.edu,Sally\n10,lifehacker.com,Sally\n11,utexas.edu,Robert\n12,gmail.com,Matt\n13,gmail.com,May\n14,reddit.com,Sally\n15,utexas.edu,Sue\n16,gmail.com,Bob\n17,gmail.com,Sue\n18,gmail.com,Hector\n19,gmail.com,Joe\n20,gmail.com,Bert\n21,gmail.com,Sally\n22,utexas.edu,Robert\n23,utexas.edu,Aaron\n24,utexas.edu,May\n25,reddit.com,Bill\n26,piazza.com,Bill\n27,utexas.edu,Sally\n28,utexas.edu,Bert\n29,utexas.edu,Sally\n30,gmail.com,Sue\n31,yahoo.com,Aaron\n32,gmail.com,Bert\n33,gmail.com,Hector\n34,utexas.edu,Bert\n35,utexas.edu,Greg\n36,gmail.com,Greg\n37,gmail.com,Sue\n38,utexas.edu,Jim\n39,reddit.com,James\n40,yelp.com,Matt\n41,gmail.com,Matt\n42,utexas.edu,Aaron\n43,reddit.com,Robert\n44,utexas.edu,Matt\n45,gmail.com,Sue\n46,gmail.com,James\n47,utexas.edu,Hector\n48,yelp.com,James\n49,gmail.com,Sue\n50,att.com,Sue\n";
    String r = new LogAnalysis().highestAffinityPair(s);
    assertEquals(r,"gmail.com,utexas.edu");
  }

  @Test
  public void Simple3() {
    String s = "1,google.com,Hector\n2,facebook.com,Joe\n3,yelp.com,Sue\n4,utexas.edu,Matt\n5,yelp.com,Hector\n6,yelp.com,Bob\n7,gmail.com,James\n8,google.com,Bill\n9,google.com,Robert\n10,yelp.com,Hector\n11,reddit.com,Sally\n12,gmail.com,Aaron\n13,yelp.com,Joe\n14,google.com,Bert\n15,yelp.com,Jim\n16,google.com,Bert\n17,yelp.com,Sally\n18,lifehacker.com,Hector\n19,ask.com,Bill\n20,ask.com,Bill\n21,yelp.com,Sue\n22,ask.com,Betty\n23,yelp.com,Bill\n24,yelp.com,Aaron\n25,google.com,Sally\n26,utexas.edu,May\n27,att.com,Aaron\n28,yelp.com,Bob\n29,google.com,James\n30,yelp.com,Aaron\n31,google.com,Hector\n32,google.com,May\n33,piazza.com,Sue\n34,google.com,Matt\n35,google.com,Bob\n36,yelp.com,Casey\n37,yelp.com,Bert\n38,yelp.com,Sue\n39,yelp.com,Matt\n40,ask.com,Bert\n41,gmail.com,Matt\n42,yelp.com,Aaron\n43,facebook.com,Bill\n44,att.com,Aaron\n45,gmail.com,Robert\n46,yelp.com,Joe\n47,utexas.edu,Hector\n48,google.com,Jim\n49,piazza.com,Greg\n50,yelp.com,Matt\n";
    String r = new LogAnalysis().highestAffinityPair(s);
    assertEquals(r,"google.com,yelp.com");
  }

  @Test
  public void Simple4() {
    String s = "1,piazza.com,Hector\n2,google.com,Jim\n3,facebook.com,Sally\n4,facebook.com,Jim\n5,piazza.com,Matt\n6,piazza.com,Hector\n7,gmail.com,Joe\n8,reddit.com,Bob\n9,facebook.com,Jim\n10,facebook.com,Betty\n11,piazza.com,Hector\n12,piazza.com,Bert\n13,google.com,Jim\n14,lifehacker.com,Aaron\n15,utexas.edu,May\n16,utexas.edu,Bill\n17,piazza.com,Bob\n18,facebook.com,Sally\n19,att.com,Bob\n20,facebook.com,Bill\n21,reddit.com,Matt\n22,yelp.com,Bill\n23,att.com,James\n24,piazza.com,May\n25,piazza.com,Hector\n26,reddit.com,Sue\n27,piazza.com,Aaron\n28,yelp.com,Betty\n29,facebook.com,Robert\n30,piazza.com,Bill\n31,piazza.com,Casey\n32,facebook.com,Matt\n33,piazza.com,Bill\n34,piazza.com,Hector\n35,ask.com,Bill\n36,lifehacker.com,Greg\n37,piazza.com,Casey\n38,facebook.com,Bob\n39,facebook.com,May\n40,reddit.com,Casey\n41,facebook.com,Casey\n42,piazza.com,Hector\n43,facebook.com,Betty\n44,facebook.com,Bob\n45,facebook.com,Betty\n46,piazza.com,James\n47,piazza.com,Matt\n48,facebook.com,Robert\n49,facebook.com,Bert\n50,piazza.com,Casey\n";
    String r = new LogAnalysis().highestAffinityPair(s);
    assertEquals(r,"facebook.com,piazza.com");
  }

  static String[] pages2 = {"a","b","c","d","e"};
  static String[] persons2 = {"a","b","c","d","e","f","g"};

  @Test
  public void Complex1() {
    StringBuffer sb = new StringBuffer();
    Random rnd = new Random(0);
    for ( int i = 0 ; i < 100; i++ ) {
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
  public void Complex2() {
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

