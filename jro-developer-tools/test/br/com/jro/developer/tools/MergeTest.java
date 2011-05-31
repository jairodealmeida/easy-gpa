package br.com.jro.developer.tools;


import static org.junit.Assert.*;

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
		String[] args = new String[]{"-m","G:/temp/AgroGIS - Views v3"};
	   	try {
            PromptCommand prompt = new PromptCommand();
            prompt.execute(args);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
