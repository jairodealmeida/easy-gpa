package br.com.jro.developer.tools.shapefile;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.List;

import org.junit.Test;



public class ShapefileUtilTest{


	@Test
	public void testReadFile(){
		try {
			URL url = ShapefileUtil.class.getResource("calc_cont.shp");
			ShapefileUtil util = new ShapefileUtil(url);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testReadFile1(){
		try {
			ShapefileUtil util = new ShapefileUtil(null);
			fail("não poderia passar aqui");
		} catch (Exception e) {
			assertEquals("Null URL for ShapefileDataSource", e.getMessage());
		}
	}
	@Test
	public void testGetWktList(){
		try {
			URL url = ShapefileUtil.class.getResource("calc_cont.shp");
			ShapefileUtil util = new ShapefileUtil(url);
			List<String> list = util.getWktList(); 
			assertNotNull(list);
			assertTrue(list.size()>0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
