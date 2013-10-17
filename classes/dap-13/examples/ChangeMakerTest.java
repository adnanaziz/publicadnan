import static org.junit.Assert.*;

import org.junit.Test;


public class ChangeMakerTest {

	@Test
	public void t1() {
		int res = ChangeMaker.mincoins(1);
		assertEquals(1,res);
	}

	@Test 
	public void t2() {
		int res = ChangeMaker.mincoins(2);
		assertEquals(2,res);		
	}
	
	@Test 
	public void t25() {
		int res = ChangeMaker.mincoins(25);
		assertEquals(2,res);
	}
	
	@Test 
	public void t3000() {
		int res = ChangeMaker.mincoins(3000);
		assertEquals(100,res);
	}
}
