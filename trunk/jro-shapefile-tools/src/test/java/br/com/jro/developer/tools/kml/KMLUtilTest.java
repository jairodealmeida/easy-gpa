package br.com.jro.developer.tools.kml;


import static org.junit.Assert.fail;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.jro.developer.tools.shapefile.ShapefileUtil;

public class KMLUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testContrutor(){
		try {
			URL url = KMLUtilTest.class.getResource("CCEAA10004.xml");
			KMLUtil util = new KMLUtil(url);	
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
