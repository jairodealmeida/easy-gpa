package br.com.jro.developer.tools.kml;


import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.jro.developer.tools.shapefile.ShapeFile;

public class KMLFileTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testContrutor1(){
		try {
			URL url = KMLFile.class.getResource("CCEAA10004.xml");
			KMLFile util = new KMLFile(url);	
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testContrutor2(){
		try {
			KMLFile util = new KMLFile(null);	
			fail("não pode passar aqui");
		} catch (Exception e) {
			assertEquals("Null URL for KMLFile", e.getMessage());
		}
	}
}
