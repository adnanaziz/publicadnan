import static org.junit.Assert.*;
import org.junit.Test;

// Need 10MB stack to run, set using -Xss10M

public class ChangeMakerTest {

	@Test
	public void t1() {
		int res = ChangeMaker.mincoins(1);
		assertEquals(1,res);
		res = ChangeMaker.mincoins_iter(1);
		assertEquals(1,res);
	}

	@Test 
	public void t2() {
		int res = ChangeMaker.mincoins(2);
		assertEquals(2,res);		
		res = ChangeMaker.mincoins_iter(2);
		assertEquals(2,res);		
	}
	
	@Test 
	public void t25() {
		int res = ChangeMaker.mincoins(25);
		assertEquals(2,res);
		res = ChangeMaker.mincoins_iter(25);
		assertEquals(2,res);
	}
	
	@Test 
	public void t3000() {
		int res = ChangeMaker.mincoins(3000);
		assertEquals(100,res);
		res = ChangeMaker.mincoins_iter(3000);
		assertEquals(100,res);
	}

	@Test 
	public void t30000() {
		int res = ChangeMaker.mincoins(30000);
		assertEquals(1000,res);
		res = ChangeMaker.mincoins_iter(30000);
		assertEquals(1000,res);
	}
}
