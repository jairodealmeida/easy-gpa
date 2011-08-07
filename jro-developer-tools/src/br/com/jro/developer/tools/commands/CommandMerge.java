package br.com.jro.developer.tools.commands;

import java.io.File;

import org.apache.log4j.Logger;

import br.com.jro.developer.tools.util.FileUtil;

public class CommandMerge extends Command{
	private static Logger log = Logger.getLogger(CommandMerge.class);
	
	private FileUtil fileUtil = new FileUtil();
	@Override
	public void execute() throws Exception{
		if(super.getCommandValues().length>=2){
		    String path_name = super.getCommandValues()[1];
		    String extension = super.getCommandValues()[2];
	        if(path_name==null)
	            return;
	        File file = new File(path_name);
	        StringBuilder text = new StringBuilder();
	        if(super.getCommandValues().length>3){ //enconding parameter sends
	        	String encoding = super.getCommandValues()[3];
	        	fileUtil.recursiveMerge(file, text, encoding, extension);
	        }else{ //recursive read path files and merge with encoding to pt-br characters
	        	fileUtil.recursiveMerge(file, text, "ISO-8859-1", extension);
	        }
			log.info(text);			
		}else{
			throw new NullPointerException("incorrect parameters : " +
					"correct write -m <path_name> <files_extension> (mandatory) " +
					"<encoding> (optional default ISO-8859-1)");
		}
	}
	@Override
	public void printHelp() {
		log.info("-m to read a recursive txt files into directory (will merge) : " +
		    	"example -m : merge text files in log file" +
		    	"<path_name> : directory name" +
		    	"<extension> : extension of files will merge - example .txt, .sql, .java, etc" +
		    	"<encoding> : for example: encoding are ISO-8859-1 to portuguese, UTF-8 to english, etc)");
		
	}
	

}
