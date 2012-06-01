/**
 * 
 */
package br.com.jro.developer.tools;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jairo de Almeida - jairo.almeida@proxima.agr.br
 * @size 23/05/2012
 * jro-developer-tools 	
 */
public class StrutsUtilTest {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testWriteXMLManter(){
		String[] args = new String[]{"-d","G:/temp/AgroGIS - Views v3"};
	   	try {
            PromptCommand prompt = new PromptCommand();
            prompt.execute(args);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
