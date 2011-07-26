package br.com.jro.developer.tools.shapefile;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.List;

import org.junit.Test;



public class ShapeFileTest{


	@Test
	public void testReadFile(){
		try {
			URL url = ShapeFile.class.getResource("calc_cont.shp");
			ShapeFile util = new ShapeFile(url);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testReadFile1(){
		try {
			ShapeFile util = new ShapeFile(null);
			fail("não poderia passar aqui");
		} catch (Exception e) {
			assertEquals("Null URL for ShapefileDataSource", e.getMessage());
		}
	}
	@Test
	public void testGetWktList(){
		try {
			URL url = ShapeFile.class.getResource("calc_cont.shp");
			ShapeFile util = new ShapeFile(url);
			List<String> list = util.getWktList(); 
			assertNotNull(list);
			assertTrue(list.size()>0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
