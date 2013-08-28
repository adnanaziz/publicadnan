import static org.junit.Assert.*;

import org.junit.Test;

public class MaxProfitTest {

	@Test
	public void testSimple() {
		int[] A = new int[]{1,2,3,4};
		int p = MaxProfit.maxProfit(A);
		assertEquals(3,p);
	}
	
	@Test
	public void testComplex1() {
		int A[] = new int[]{12,14,16,10,8,9,10,11,5};
		int p = MaxProfit.maxProfit(A);
		assertEquals(4,p);
	}
	
	@Test
	public void testDownOnly() {
		int[] A = new int[]{4,3,2,1};
		int p = MaxProfit.maxProfit(A);
		assertEquals(0,p);
	}
	

	public void testMaxValue() {
		int[] A = new int[]{1,2,3,Integer.MAX_VALUE, 5};
		int p = MaxProfit.maxProfit(A);
		assertEquals(Integer.MAX_VALUE - 1,p);
	}
	
	
	@Test
	public void testSingleValue() {
		int A[] = new int[]{1};
		int p = -1;
		try {
			p = MaxProfit.maxProfit(A);
			fail("Should have thrown IllegalArgumentException, p = " + p);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

}
