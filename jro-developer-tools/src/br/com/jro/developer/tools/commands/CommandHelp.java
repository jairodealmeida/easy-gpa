package br.com.jro.developer.tools.commands;

import org.apache.log4j.Logger;

<<<<<<< .mine
import br.com.jro.developer.tools.PromptCommand;


=======
import br.com.jro.developer.tools.PromptCommand;

>>>>>>> .r76
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
    public void printHelp(){
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
    	//log.info("-d to print recursive path files : example -d <path_name> : directory name");
    	log.info("-h to read help");
    	try {
			for (String[] token_item : PromptCommand.CMD_ARRAY) {
				String command = token_item[0];
				if(command.equalsIgnoreCase("-h"))
					continue;
				String class_name = token_item[1];
				Command commandExe = (Command)Class.forName(class_name).newInstance();
				commandExe.printHelp();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
   }
}
