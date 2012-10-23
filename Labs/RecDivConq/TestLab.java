import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;
import java.util.zip.DataFormatException;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;

import java.util.List;
import java.util.ArrayList;

public class TestLab {

	public static final Class<RegExp> CLASSUNDERTEST = RegExp.class;

	  public static int score = 0;

	  @Before
	  public void setUp() {
	    // System.out.println("Setting up")); score += 5;}
	  }

	  // Ang's suggestion on getting annotation values
	  public static String getClassAnnotationValue(Class<RegExp> classType,
	                                               Class<Author> annotationType,
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

	  @Test public void testRegExp1() { assertTrue( RegExp.match( ".", "a")); score += 5;}
	  @Test public void testRegExp2() { assertTrue( RegExp.match( "a", "a")); score += 5;}
	  @Test public void testRegExp3() { assertFalse( RegExp.match( "a", "b")); score += 5;}
	  @Test public void testRegExp4() { assertTrue( RegExp.match( "a.9", "aW9")); score += 5;}
	  @Test public void testRegExp5() { assertFalse( RegExp.match( "a.9", "aW19")); score += 5;}
	  @Test public void testRegExp6() { assertTrue( RegExp.match( "^a.9", "aW9")); score += 5;}
	  @Test public void testRegExp7() { assertFalse( RegExp.match( "^a.9", "baW19")); score += 5;}
	  @Test public void testRegExp8() { assertTrue( RegExp.match( ".*", "a")); score += 5;}
	  @Test public void testRegExp9() { assertTrue( RegExp.match( ".*", "")); score += 5;}
	  @Test public void testRegExp10() { assertTrue( RegExp.match( ".*",  "asdsdsa")); score += 5;}
	  @Test public void testRegExp11() { assertTrue( RegExp.match( "9$" , "xxxxW19")); score += 5;}
	  @Test public void testRegExp12() { assertTrue( RegExp.match( ".*a", "ba")); score += 5;}
	  @Test public void testRegExp13() { assertTrue( RegExp.match( ".*a", "ba")); score += 5;}
	  @Test public void testRegExp14() { assertTrue( RegExp.match( "^a.*9$", "axxxxW19")); score += 5;}
	  @Test public void testRegExp15() { assertTrue( RegExp.match( "^a.*W19$", "axxxxW19")); score += 5;}
	  @Test public void testRegExp16() { assertTrue( RegExp.match( ".*a.*W19", "axxxxW19123")); score += 5;}
	  @Test public void testRegExp17() { assertFalse( RegExp.match( ".*b.*W19", "axxxxW19123")); score += 5;}
	  @Test public void testRegExp18() { assertTrue( RegExp.match( "n.*", "n")); score += 5;}
	  @Test public void testRegExp19() { assertTrue( RegExp.match( "a*n.*", "an")); score += 5;}
	  @Test public void testRegExp20() { assertTrue( RegExp.match( "a*n.*", "ana")); score += 5;}
	  @Test public void testRegExp21() { assertTrue( RegExp.match( "a*n.*W19", "anaxxxxW19123")); score += 5;}
	  @Test public void testRegExp22() { assertTrue( RegExp.match( ".*a*n.*W19", "asdaaadnanaxxxxW19123")); score += 5;}
	  @Test public void testRegExp23() { assertTrue( RegExp.match( ".*.*.a*n.*W19", "asdaaadnanaxxxxW19123")); score += 5;}


	@Test
	public void testSkyline1() {
		List<Building> lb = new ArrayList<Building>();
		lb.add(new Building(0, 1, 1));
		lb.add(new Building(1, 1, 2));
		lb.add(new Building(2, 1, 3));
		List<Integer> expectedSkyline = new ArrayList<Integer>();
		expectedSkyline.add(0);
		expectedSkyline.add(1);
		expectedSkyline.add(3);
		expectedSkyline.add(0);
		List<Integer> skyline = Skyline.computeSkyline(lb);
		assertEquals(skyline, expectedSkyline);
		score += 10;

	}

	@Test
	public void testSkyline2() {
		List<Building> lb = new ArrayList<Building>();

		lb.add(new Building(0, 3, 8));
		lb.add(new Building(3, 17, 7));
		lb.add(new Building(4, 71, 8));
		lb.add(new Building(13, 21, 19));
		lb.add(new Building(9, 35, 11));
		lb.add(new Building(10, 7, 12));
		lb.add(new Building(15, 10, 21));

		List<Integer> expectedSkyline = new ArrayList<Integer>();
		int[] expectedArray = {0,3,3,17,4,71,8,0,9,35,11,7,12,0,13,21,19,10,21,0};
		for (int i = 0;i<expectedArray.length;i++)
			expectedSkyline.add(expectedArray[i]);

		List<Integer> skyline = Skyline.computeSkyline(lb);
		assertEquals(skyline, expectedSkyline);

	}

	@Test
	public void testSkyline3() {
		List<Building> lb = new ArrayList<Building>();
		lb.add(new Building(31, 23, 33));
		lb.add(new Building(12, 17, 19));
		lb.add(new Building(7, 71, 10));
		lb.add(new Building(2, 13, 11));
		lb.add(new Building(3, 35, 5));
		lb.add(new Building(21, 7, 28));
		lb.add(new Building(24, 14, 35));

		List<Integer> expectedSkyline = new ArrayList<Integer>();
		int[] expectedArray = {2,13,3,35,5,13,7,71,10,13,11,0,12,17,19,0,21,7,24,14,31,23,33,14,35,0};
		for (int i = 0;i<expectedArray.length;i++)
			expectedSkyline.add(expectedArray[i]);

		List<Integer> skyline = Skyline.computeSkyline(lb);
		assertEquals(skyline, expectedSkyline);

	}

	@Test
	public void testSkyline4() {
		List<Building> lb = new ArrayList<Building>();
		lb.add(new Building(3, 4, 23));
		lb.add(new Building(7, 12, 12));
		lb.add(new Building(10, 25, 17));
		lb.add(new Building(27, 13, 29));
		lb.add(new Building(31, 5, 41));
		lb.add(new Building(33, 7, 39));
		lb.add(new Building(35, 14, 37));

		List<Integer> expectedSkyline = new ArrayList<Integer>();
		int[] expectedArray = {3,4,7,12,10,25,17,4,23,0,27,13,29,0,31,5,33,7,35,14,37,7,39,5,41,0};
		for (int i = 0;i<expectedArray.length;i++)
			expectedSkyline.add(expectedArray[i]);

		List<Integer> skyline = Skyline.computeSkyline(lb);
		assertEquals(skyline, expectedSkyline);

	}

	@Test
	public void testSkyline5() {
		List<Building> lb = new ArrayList<Building>();
		lb.add(new Building(27, 23, 31));
		lb.add(new Building(13, 12, 23));
		lb.add(new Building(2, 3, 38));
		lb.add(new Building(3, 35, 7));
		lb.add(new Building(11, 10, 17));
		lb.add(new Building(27, 21, 30));
		lb.add(new Building(22, 7, 34));

		List<Integer> expectedSkyline = new ArrayList<Integer>();
		int[] expectedArray = {2,3,3,35,7,3,11,10,13,12,23,7,27,23,31,7,34,3,38,0};
		for (int i = 0;i<expectedArray.length;i++)
			expectedSkyline.add(expectedArray[i]);

		List<Integer> skyline = Skyline.computeSkyline(lb);
		assertEquals(skyline, expectedSkyline);

	}

	@Test
	public void testSkyline_stressTest1() {
		List<Building> lb = new ArrayList<Building>();
		int MAX_NUMBER = 4;

		for (int left = -MAX_NUMBER , right = MAX_NUMBER , height = 1; left < right; left++, right--, height++) {
			lb.add(new Building(left, height, right));
		}

		List<Integer> expectedSkyline = new ArrayList<Integer>();

		for (int i = -MAX_NUMBER , height = 1; i < 0; i++, height++) {
			expectedSkyline.add(i);
			expectedSkyline.add(height);
		}
		for (int i = 1, height = MAX_NUMBER -1; i <= MAX_NUMBER ; i++, height--) {
			expectedSkyline.add(i);
			expectedSkyline.add(height);
		}
		List<Integer> skyline = Skyline.computeSkyline(lb);
		assertEquals(skyline, expectedSkyline);
		score += 10;

	}
	@Test
	public void testSkyline_stressTest2() {
		List<Building> lb = new ArrayList<Building>();
		int MAX_NUMBER = 1000000;

		for (int left = -MAX_NUMBER , right = MAX_NUMBER , height = 1; left < right; left++, right--, height++) {
			lb.add(new Building(left, height, right));
		}

		List<Integer> expectedSkyline = new ArrayList<Integer>();

		for (int i = -MAX_NUMBER , height = 1; i < 0; i++, height++) {
			expectedSkyline.add(i);
			expectedSkyline.add(height);
		}
		for (int i = 1, height = MAX_NUMBER -1; i <= MAX_NUMBER ; i++, height--) {
			expectedSkyline.add(i);
			expectedSkyline.add(height);
		}
		List<Integer> skyline = Skyline.computeSkyline(lb);
		assertEquals(skyline, expectedSkyline);
		score += 10;

	}

	@Test
	public void testSkyline_stressTest3() {
		List<Building> lb = new ArrayList<Building>();
		int MAX_NUMBER = 4;

		for (int left = -1, right = 1, height = 1; right <= MAX_NUMBER; left--, right++, height++) {
			lb.add(new Building(left, height, right));
		}
		List<Integer> expectedSkyline = new ArrayList<Integer>();
		expectedSkyline.add(-MAX_NUMBER );
		expectedSkyline.add(MAX_NUMBER );
		expectedSkyline.add(MAX_NUMBER );
		expectedSkyline.add(0);
		List<Integer> skyline = Skyline.computeSkyline(lb);
		assertEquals(skyline, expectedSkyline);
		score += 10;

	}
	@Test
	public void testSkyline_stressTest4() {
		List<Building> lb = new ArrayList<Building>();
		int MAX_NUMBER = 1000000;

		for (int left = -1, right = 1, height = 1; right <= MAX_NUMBER; left--, right++, height++) {
			lb.add(new Building(left, height, right));
		}
		List<Integer> expectedSkyline = new ArrayList<Integer>();
		expectedSkyline.add(-MAX_NUMBER );
		expectedSkyline.add(MAX_NUMBER );
		expectedSkyline.add(MAX_NUMBER );
		expectedSkyline.add(0);
		List<Integer> skyline = Skyline.computeSkyline(lb);
		assertEquals(skyline, expectedSkyline);
		score += 10;

	}
	@Test
	public void testSkyline_stressTest5() {
		List<Building> lb = new ArrayList<Building>();
		int MAX_NUMBER = 4;

		for (int left = -1, right = 1, height = 1; right <= MAX_NUMBER; left--, right++) {
			lb.add(new Building(left, height, right));
		}

		List<Integer> expectedSkyline = new ArrayList<Integer>();
		expectedSkyline.add(-MAX_NUMBER);
		expectedSkyline.add(1);
		expectedSkyline.add(MAX_NUMBER);
		expectedSkyline.add(0);
		List<Integer> skyline = Skyline.computeSkyline(lb);
		assertEquals(skyline, expectedSkyline);
		score += 10;

	}
	@Test
	public void testSkyline_stressTest6() {
		List<Building> lb = new ArrayList<Building>();
		int MAX_NUMBER = 1000000;

		for (int left = -1, right = 1, height = 1; right <= MAX_NUMBER; left--, right++) {
			lb.add(new Building(left, height, right));
		}

		List<Integer> expectedSkyline = new ArrayList<Integer>();
		expectedSkyline.add(-MAX_NUMBER);
		expectedSkyline.add(1);
		expectedSkyline.add(MAX_NUMBER);
		expectedSkyline.add(0);
		List<Integer> skyline = Skyline.computeSkyline(lb);
		assertEquals(skyline, expectedSkyline);
		score += 10;

	}
	@Test
	public void testSkyline_stressTest7() {
		List<Building> lb = new ArrayList<Building>();
		int MAX_NUMBER = 4;

		for (int left = 0, right = 2, height = 1; right <= MAX_NUMBER; left++, right++, height++) {
			lb.add(new Building(left, height, right));
		}

		List<Integer> expectedSkyline = new ArrayList<Integer>();
		for (int i = 0, height = 1; i < MAX_NUMBER-1 ; i++, height++) {
			expectedSkyline.add(i);
			expectedSkyline.add(height);
		}		
		expectedSkyline.add(MAX_NUMBER);
		expectedSkyline.add(0);
		
		List<Integer> skyline = Skyline.computeSkyline(lb);
		assertEquals(skyline, expectedSkyline);
		score += 10;

	}
	@Test
	public void testSkyline_stressTest8() {
		List<Building> lb = new ArrayList<Building>();
		int MAX_NUMBER = 1000000;

		for (int left = 0, right = 2, height = 1; right <= MAX_NUMBER; left++, right++, height++) {
			lb.add(new Building(left, height, right));
		}

		List<Integer> expectedSkyline = new ArrayList<Integer>();
		for (int i = 0, height = 1; i < MAX_NUMBER-1 ; i++, height++) {
			expectedSkyline.add(i);
			expectedSkyline.add(height);
		}		
		expectedSkyline.add(MAX_NUMBER);
		expectedSkyline.add(0);
		
		List<Integer> skyline = Skyline.computeSkyline(lb);
		assertEquals(skyline, expectedSkyline);
		score += 10;

	}
}
