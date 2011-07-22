package br.com.jro.developer.tools.shapefile;

import static org.junit.Assert.*;

import org.junit.Test;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.jro.developer.tools.commands.Command;
import br.com.jro.developer.tools.commands.CommandShapefile;


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
	
}
