import static org.junit.Assert.*;

import org.junit.Test;


public class PrefixCheckerTest {

	@Test
	public void test() {
		assertTrue(PrefixChecker.isPrefix("abc", "abcde"));
	}

}
