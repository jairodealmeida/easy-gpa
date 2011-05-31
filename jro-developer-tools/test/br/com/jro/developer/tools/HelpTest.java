package br.com.jro.developer.tools;

import static org.junit.Assert.fail;

import org.junit.Test;


public class HelpTest {
	@Test
	public void test1(){
		String[] args = new String[]{"-h"};
	   	try {
            PromptCommand prompt = new PromptCommand();
            prompt.execute(args);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
