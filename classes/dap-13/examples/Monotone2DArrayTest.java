import static org.junit.Assert.*;

import org.junit.Test;


public class Monotone2DArrayTest {

	@Test
	public void test() {
		int[][] T1 = { {1,2,3}, {0,2,4}};
		assertFalse(Monotone2DArray.checkMonotone(T1));
	}
	
	@Test
	public void test2() {
		int[][] T1 = { {1,2,3}, {5,8,12}, {10,20,30}, {100, 200, 300}};
		assertTrue(Monotone2DArray.checkMonotone(T1));
	}
	
	

}
