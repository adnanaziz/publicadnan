import static org.junit.Assert.*;

import org.junit.Test;


public class HelloWorldTest {

	@Test
	public void test1() {
		HelloWorld hw = new HelloWorld();
		hw.main(null);
	}
	
	@Test
	public void test2() {
	  assertEquals("foo", "bar");
	}

}
