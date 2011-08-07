package br.com.jro.developer.tools.commands;

import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.jro.developer.tools.shapefile.ShapeFile;

public class CommandShapefile extends Command{
	private static Logger log = Logger.getLogger(CommandShapefile.class);
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
    		for(int i=0; i<wkts.size();i++){
    			log.info( wkts.toString() + "\n");
    		}
        }else{
        	log.info("Is necessary set shapefile file name - " +
        					   "example file:/c:/temp.test.shp");
        }
	}
	@Override
	public void printHelp() {
		log.info("-wkt <file_path> : to export shapefile to wkt");
		
	}
}
