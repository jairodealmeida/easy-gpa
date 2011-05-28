package br.com.jro.developer.tools.commands;

import java.io.File;

import org.apache.log4j.Logger;

import br.com.jro.developer.tools.util.FileUtil;

public class CommandMerge extends Command{
	private static Logger log = Logger.getLogger(CommandMerge.class);
	
	private FileUtil fileUtil = new FileUtil();
	@Override
	public void execute() {
		try {
		    String path_name = super.getCommandValues()[1];
	        if(path_name==null)
	            return;
	        File file = new File(path_name);
	        StringBuilder text = new StringBuilder();
	        //recursive read path files and merge with 
	        //encoding to pt-br characters
			fileUtil.recursiveMerge(file, text, "ISO-8859-1");
			log.info(text);
		} catch (Exception e) {
			log.error(e);
		}

	}
	

}
