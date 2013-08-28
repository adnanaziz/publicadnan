import static org.junit.Assert.*;

import org.junit.Test;


public class BuySellTest {

	@Test
	public void basictest0() {
		assertEquals(2,BuySell.buysell(new int[]{1,2,3}));
	}

	@Test
	public void basictest1() {
		assertEquals(0,BuySell.buysell(new int[]{1,1,1}));
	}

	@Test
	public void advtest0() {
		assertEquals(Integer.MAX_VALUE, BuySell.buysell(new int[]{0,1,2,Integer.MAX_VALUE, Integer.MIN_VALUE}));
	}
	
}
