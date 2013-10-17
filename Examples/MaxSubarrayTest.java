import static org.junit.Assert.*;

import org.junit.Test;


public class MaxSubarrayTest {

	@Test
	public void t0() {
		int[] A = {4,-3,5,-2,-1,2,6,-2};
		int result = MaxSubarray.maxsubarray(A);
		assertEquals(11, result);
	}
	
	@Test
	public void t1() {
		int A[] = {1,2,3,4};
		int result = MaxSubarray.maxsubarray(A);
		assertEquals(10, result);
	}

	@Test
	public void t2() {
		int A[] = {3,-2,-3,4};
		int result = MaxSubarray.maxsubarray(A);
		assertEquals(4, result);
	}
	
	@Test
	public void t3() {
		int A[] = {4,-3,-2,3};
		int result = MaxSubarray.maxsubarray(A);
		assertEquals(4, result);
	}
	
	@Test
	public void t4() {
		int A[] = {0,-2,9,-3,1};
		int result = MaxSubarray.maxsubarray(A);
		assertEquals(9, result);
	}
	
	@Test
	public void t5() {
		int A[] = {-3,-2,-3,-4};
		int result = MaxSubarray.maxsubarray(A);
		assertEquals(0, result);
	}
	
	@Test
	public void t6() {
		int A[] = {};
		int result = MaxSubarray.maxsubarray(A);
		assertEquals(0, result);
	}
}
