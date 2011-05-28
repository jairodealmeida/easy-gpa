package br.com.jro.developer.tools;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MergeTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testMerge1(){
		//"-d C:/test_false".split(" ")
		String[] args = new String[]{"-m","G:/temp/AgroGIS - Views v3"};
		Main.main(args);
	}
}
