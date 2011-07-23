package br.com.jro.developer.tools.commands;

import static org.junit.Assert.*;

import org.junit.Test;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.jro.developer.tools.commands.Command;
import br.com.jro.developer.tools.commands.CommandShapefile;
import br.com.jro.developer.tools.shapefile.ShapefileUtil;


public class CommandShapefileTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCommandShapefile(){
		try {
			Command command = new CommandShapefile();
			URL url = ShapefileUtil.class.getResource("calc_cont.shp");
			command.setCommandValues(new String[]{"-wkt", url.getPath()});
			command.execute();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testCommandShapefile1(){
		try {
			Command command = new CommandShapefile();
			URL url = ShapefileUtil.class.getResource("calc_cont.shp");
			command.setCommandValues(new String[]{"-wkt", url.toString()});
			command.execute();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testCommandShapefile2(){
		try {
			Command command = new CommandShapefile();
			command.setCommandValues(new String[]{"-wkt", null});
			command.execute();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
