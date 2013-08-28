import static org.junit.Assert.*;

import org.junit.Test;


public class ATOITest {

	@Test
	public void testbasic1() {
		assertEquals(123, ATOI.stringToInt("123"));
	}
	
	
	@Test
	public void testexception() {
		try {
			ATOI.stringToInt("12a3");
			fail("Expected exception on input 12a3");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testws1() {
		assertEquals(123, ATOI.stringToInt("123 "));
	}
	
	@Test 
	public void testempty() {
		assertEquals(0,ATOI.stringToInt(""));
	}
	
	@Test
	public void testnegative() {
		assertEquals(-123, ATOI.stringToInt(" -123  "));
	}
	
	@Test
	public void testnull() {
		assertEquals(0, ATOI.stringToInt(null));
	}
	
	@Test
	public void testblanks() {
		assertEquals(0, ATOI.stringToInt("  "));
	}
	
	@Test
	public void testJustMinus() {
		try {
			ATOI.stringToInt("-");
			fail("Need exception on \"-\" input");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testoverflow() {
		try {
			ATOI.stringToInt("4000000000");
			fail("should have AE");
		} catch (ArithmeticException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testoverflowCornerCase() {
		try {
			ATOI.stringToInt("2147483648");
			fail("should have AE");
		} catch (ArithmeticException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testoverflowNegative1() {
		// 2147483648
		assertEquals(-2147483648, ATOI.stringToInt("-2147483648"));
	}

	@Test
	public void testoverflowNegative2() {
		// 2147483648
		assertEquals(-2147483647, ATOI.stringToInt("-2147483647"));
	}
	
	@Test
	public void testoverflowCornerCase2() {
		try {
			ATOI.stringToInt("-2147483649");
			fail("should have AE");
		} catch (ArithmeticException e) {
			assertTrue(true);
		}
	}
}