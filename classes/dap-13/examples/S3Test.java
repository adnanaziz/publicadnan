import static org.junit.Assert.*;

import org.junit.Test;


public class S3Test {

	@Test
	public void test1() {
		String s  ="ab cd";
		int result = S3.wc(s);
		assertEquals(result,2);
	}

	
	@Test
	public void test2() {
		String s  ="  ab cd";
		int result = S3.wc(s);
		assertEquals(result,2);
	}
	
	@Test
	public void test3() {
		String s  ="ab cd  ";
		int result = S3.wc(s);
		assertEquals(result,2);
	}
	
	@Test
	public void test4() {
		String s  ="ab  cd";
		int result = S3.wc(s);
		assertEquals(result,2);
	}
	
	@Test
	public void test5() {
		String s  ="a; cd";
		int result = S3.wc(s);
		assertEquals(result,2);
	}
	
	@Test
	public void test6() {
		String s  =" ";
		int result = S3.wc(s);
		assertEquals(result,0);
	}
	
	@Test
	public void test7() {
		String s  ="";
		int result = S3.wc(s);
		assertEquals(result,0);
	}
	
	@Test
	public void test8() {
		String s  ="ab";
		int result = S3.wc(s);
		assertEquals(result,1);
	}
	
	@Test
	public void test9() {
		String s  = "";
		for ( int i = 0 ; i < 10000; i++ ) {
			s += "foo ";
		}
		int result = S3.wc(s);
		assertEquals(result,10000);
	}
}
