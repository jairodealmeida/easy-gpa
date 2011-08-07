package br.com.jro.developer.tools.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class FileUtil {
	   
	private static Logger log = Logger.getLogger(FileUtil.class);
	   
	  public StringBuilder read(File file, String encoding) throws IOException {
	    StringBuilder text = new StringBuilder();
	    String NL = System.getProperty("line.separator");
	    Scanner scanner = new Scanner(new FileInputStream(file), encoding);
	    Locale brLocale = new Locale("pt", "BR");
	    scanner.useLocale(brLocale); 
	    try {
	      while (scanner.hasNextLine()){
	        text.append(scanner.nextLine() + NL);
	      }
	    }finally{
	      scanner.close();
	    }
	    return text;
	  }
	  
	  public void recursiveMerge(File file, StringBuilder text, String encoding) throws IOException{
	       File[] files = file.listFiles();
	       for (File file_item : files) {
	           if(file_item.isDirectory()){
	        	   recursiveMerge(file_item, text,encoding);
	           }else{
	        	   StringBuilder readedFile = read(file_item, encoding);
	        	   text.append( readedFile );
	           }
	       }
	       
	  }
	  public void recursiveMerge(File file, StringBuilder text, String encoding, String extension) throws IOException{
	       File[] files = file.listFiles();
	       for (File file_item : files) {
	           if(file_item.isDirectory()){
	        	   recursiveMerge(file_item, text,encoding);
	           }else{
	        	   String ex = getExtension(file_item);
	        	   if(ex.equalsIgnoreCase(extension)){
	        		   StringBuilder readedFile = read(file_item, encoding);
	        		   text.append( readedFile );
	        	   }
	           }
	       }
	       
	  }
	  public String getExtension(File fileItem){
		  int index = fileItem.getName().indexOf(".");
		  String extension = fileItem.getName().substring(index);
		  return extension;
	  }
	  public boolean listFilesByPath(String path_name){
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
