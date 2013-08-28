import static org.junit.Assert.*;

import org.junit.Test;


public class M1Test {

	@Test
	public void testBasic_3() {
		int retValue = M1.count(3);
		assertEquals(1, retValue);
	}
	
	@Test 
	public void testBasic_5() {
		int retVal = M1.count(5);
		assertEquals(1, retVal);
	}
	
	@Test 
	public void testBasic_6() {
		int retVal = M1.count(6);
		assertEquals(2, retVal);
	}
	
	@Test 
	public void testCorner_0() {
		try {
			M1.count(0);
			fail("Expected IllegalArgumentException on n == 0");
		} catch ( IllegalArgumentException e) {
			assertTrue(true);
		}
	}

}
