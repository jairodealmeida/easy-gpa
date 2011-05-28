package br.com.jro.developer.tools.commands;

import org.apache.log4j.Logger;

public class CommandHelp extends Command{
	private static Logger log = Logger.getLogger(CommandHelp.class);

	public CommandHelp(){
		super.setCommandName( "-h" );
		super.setCommandValues(new String[]{"-h"});
	}
	
	@Override	
	public void execute() {
		printHelp();
	}
    private void printHelp(){
		log.info("TOOLS TO FACILITY A DEVELOPER STEPS.");
		
    	log.info("author ...: Jairo de Almeida. since 12 abril 2010. version 1.1\n" +
    			 "||.........## --.#.......|\n" +
    			 "||.........   #  # ......|\n" +
    			 "||.........     *  ......|\n" +
    			 "||........     -^........|\n" +        
    			 "||.....##\\        .......|\n" +
    			 "||....#####     /###.....|\n" +       
    			 "||....########\\ \\((#.....|\n" +
    			 "||..####,   ))/ ##.......|\n" + 
    			 "||..#####      '####.....|\n" +
    			 "||..#####\\____/#####.....|\n" +          
    			 "||...######..######......|\n" +
    			 "||.....\"\"\"\"  \"\"\"\"........|\n");
    	log.info("COMANDS ...");
    	log.info("-d to print recursive files example : -d <path_name>");
    	log.info("-h to read help");
    	log.info("-m to read a recursive txt files into directory (will merge)");
    }
}
