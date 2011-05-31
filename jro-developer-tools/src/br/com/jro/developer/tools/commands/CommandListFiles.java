package br.com.jro.developer.tools.commands;

import java.io.File;

import org.apache.log4j.Logger;

public class CommandListFiles extends Command{
	private static Logger log = Logger.getLogger(CommandListFiles.class);
	@Override
	public void execute() {
		listFilesByPath();
	}
   private boolean listFilesByPath(){
	    String path_name = super.getCommandValues()[1];
        if(path_name==null)
            return false;
        File file = new File(path_name);
        if(file.isDirectory()){
             searchFiles(file);
             return true;
        }else{
            return false;
        }

    }
   private void searchFiles(File file){
       File[] files = file.listFiles();
       for (File file_item : files) {
           if(file_item.isDirectory()){
               searchFiles(file_item);
           }else{
           	log.info(file_item.getAbsolutePath());
           }
       }
   }
}
