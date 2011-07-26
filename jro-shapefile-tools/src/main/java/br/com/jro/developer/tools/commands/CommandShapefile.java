package br.com.jro.developer.tools.commands;

import java.net.URL;
import java.util.List;
import br.com.jro.developer.tools.shapefile.ShapeFile;

public class CommandShapefile extends Command{
	
	public static final String COMMAND_LISTWKTS = "-wkt";
	
	@Override
	public void execute() throws Exception {
		String command = super.getCommandValues()[0];
	    String pathName = super.getCommandValues()[1];
	    if(command.equalsIgnoreCase(COMMAND_LISTWKTS)){
	    	commandGetWkts(pathName);
	        return;
	    }
	}
	private void commandGetWkts(String pathName) throws Exception{
        if(pathName!=null){
        	if(!pathName.startsWith("file:")){
        		pathName = "file:" + pathName;
        	}
        	URL url = new URL(pathName);
    		ShapeFile util = new ShapeFile(url);
    		List<String> wkts = util.getWktList();
    		System.out.println( wkts.toString() );
        }else{
        	System.out.println("Is necessary set shapefile file name - " +
        					   "example file:/c:/temp.test.shp");
        }
	}
}
