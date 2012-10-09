import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.zip.DataFormatException;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;


public class TestPairingConstraints {

  public static final Class CLASSUNDERTEST = PairingConstraints.class;

  public static int score = 0;

  @Before
  public void setUp() {
    // System.out.println("Setting up");
  }

  // Ang's suggestion on getting annotation values
  public static String getClassAnnotationValue(Class classType, 
                                               Class annotationType, 
                                               String attributeName) {
    String value = null;
    Annotation annotation = classType.getAnnotation(annotationType);
    if (annotation != null) {
      try {
        value = (String) annotation.annotationType().getMethod(attributeName)
                                                    .invoke(annotation);
     } catch (Exception ex) {
        System.out.println("Failed loading class annotations");
     }
   }
   return value;
  }

  @AfterClass
  public static void oneTimeTearDown() {
    String name = getClassAnnotationValue(CLASSUNDERTEST,
                                          Author.class, "name");
    String uteid = getClassAnnotationValue(CLASSUNDERTEST,
                                           Author.class, "uteid");
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
    String [] tc1ExpectedResult = {"Jack the Great + Pat"};
    String [] result = PairingConstraints.solve( tc1 );
    for ( int i = 0 ; i < tc1ExpectedResult.length; i++ ) {
      assertTrue( tc1ExpectedResult[i].toLowerCase().equals(result.toLowerCase() ) );
    }
    score += 5;
  }
}
