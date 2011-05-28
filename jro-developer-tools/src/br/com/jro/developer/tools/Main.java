package br.com.jro.developer.tools;

import java.util.Arrays;

import org.apache.log4j.Logger;

public class Main {
	private static Logger log = Logger.getLogger(Main.class);

	/**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	printArgs(args);
        PromptCommand prompt = new PromptCommand();
        prompt.execute(args);
    }
    private static void printArgs(String[] args){
        if(args!=null){
        	String comand = Arrays.toString(args);
        	log.info("command ..: " + comand);
        }else{
        	log.info("command ..: " + "null");
        }
    }

}
