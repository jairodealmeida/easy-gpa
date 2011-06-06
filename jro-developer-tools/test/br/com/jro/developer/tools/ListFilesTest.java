package br.com.jro.developer.tools;

import static org.junit.Assert.fail;

import org.junit.Test;


public class ListFilesTest {
	@Test
	public void test1(){
		String[] args = new String[]{"-d", "C:/Documents and Settings/jairo.almeida/Desktop/AgroGIS/AgroGIS - Views v3"};
	   	try {
            PromptCommand prompt = new PromptCommand();
            prompt.execute(args);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
