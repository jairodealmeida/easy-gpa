package br.com.jro.developer.tools.commands;

import java.net.URL;
import java.util.List;
import br.com.jro.developer.tools.shapefile.ShapefileUtil;

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
        	URL url = new URL(pathName);
    		ShapefileUtil util = new ShapefileUtil(url);
    		List<String> wkts = util.getWktList();
    		System.out.println( wkts.toString() );
        }
	}
}
