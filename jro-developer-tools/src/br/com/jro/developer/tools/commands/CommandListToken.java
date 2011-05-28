package br.com.jro.developer.tools.commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class CommandListToken extends Command{
	private static Logger log = Logger.getLogger(CommandListToken.class);
	@Override
	public void execute() {
		searchToken();
	}
	private void searchToken(){
		    if(super.getCommandValues()!=null && 
		    		super.getCommandValues().length==2){
			    String file_path = super.getCommandValues()[0]; 
			    String token = super.getCommandValues()[1];
				BufferedReader br;
				try {
					br = new BufferedReader(new FileReader(file_path));
					TreeSet<String> m = new TreeSet<String>();
					String line;
					while((line = br.readLine())!=null){
						Pattern p = Pattern.compile("\"" + token + "\\w+\"");
						Matcher ma = p.matcher(line);
						if(ma.find()){
							String str = ma.group();
							if (str!=null){
								m.add(str);
							}
						}
					}
					for(String stt : m){
						log.info(stt);
					}
				} catch (FileNotFoundException e) {
					log.error(e.getMessage());
				} catch (IOException e) {
					log.error(e.getMessage());
				}
		    }else{
		    	new NullPointerException("invalid parameterss");
		    }
	}
}
