import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.zip.DataFormatException;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;


public class TestPairingConstraints {

  public static int score = 0;

  @Before
  public void setUp() {
    // System.out.println("Setting up");
  }

  // should be a better way to do this using existing lib function
  static String getAnnotationAttributeValue( Annotation [] annos, String annoName, String attribute ) {
    String annoLine = null;
    for ( Annotation anno : annos ) {
      if ( anno.toString().startsWith( annoName ) ) {
        annoLine = anno.toString();
        break;
      }
    }
    if ( annoLine == null ) {
      return null;
    }
    // annLine is of the form:
    // "@Author(name=Adnan Aziz, uteid=aa123)"
    String tmp1 = annoLine.replace("@Author", "");
    String tmp2 = tmp1.replace("(", "");
    String tmp3 = tmp2.replace(")", "");
    String attribValuePairs[] = tmp3.split(",");
    for ( int i = 0 ; i < attribValuePairs.length; i++ ) {
      // System.out.println("a-v:" + attribValuePairs[i]);
      String pair[] = attribValuePairs[i].trim().split("=");
      for( int j = 0 ; j < pair.length; j++ ) {
        // System.out.println(pair[j]);
      }
      if ( pair[0].equals( attribute ) ) {
        return pair[1];
      }
    }
    return null;
  }

  @AfterClass
  public static void oneTimeTearDown() {
    Class javacontructs = PairingConstraints.class;
    Annotation[] annos = javacontructs.getDeclaredAnnotations();
    String name = getAnnotationAttributeValue( annos, "@Author", "name" );
    String uteid = getAnnotationAttributeValue( annos, "@Author", "uteid" );
    System.out.println("\n@score" + "," + name + "," + uteid + "," + score);
  }


  public static String [] tc1 = {
			"Pooh Bear : M : Angie : Fred : 5.0" ,
			"Preppie : F : Myra : Sam : 4.1" ,
			"Deb : F : MyRa : Sam : 3.9" ,
			"BerNice : F : Angie : Sam : 5.9" ,
			"PhiLly : F : Myra : Fred : 6.0" ,
			"AntSy : M : Myra : Sam : 5.5" ,
			"A\ngie : F : Doris : DOn : 6.5" ,
			"Fred : M : Phyllis : Dave : 7.0" ,
			"Myra : F : Lynn : Dave : 7.0" ,
			"Sam : M : Lynn : Tom : 6.5" ,
			"Sety : F : Angie : Fred : 5.0" ,
			"Art : M : Preppie : Pooh Bear : 5.0" ,
			"Sandy : F : Preppie : Pooh Bear : 5.0" ,
			"Silly_@#$%Name : F : Sety : Antsy : 1.9" ,
			"Pat : F : MYRa : Antsy : 5.0" ,
			"Jack the Great : M : PhYLlis : POOh Bear : 5.0" };

  @Test
  public void testTc1() {
    String [] tc1ExpectedResult = {"Pat + Jack the Great"};
    String [] result = PairingConstraints.solveFromStringArray( tc1 );
    assertArrayEquals( "tc1: ", tc1ExpectedResult, result );
    score += 5;
  }

}
