package br.com.gpa.util;

public class Logger { 
	public static Logger getLogger(Class artefact){
		if(artefact!=null){
			System.out.println("logging to " + artefact.getName());
			return new Logger();
		}else{
			throw new NullPointerException("Logger artefact not found");
		}
	}
	public void info(String msg){
		System.out.println(msg);
	}
	public void error(String msg){
		System.err.println(msg);
	}
	public void error(Exception e){
		if(e!=null){
			e.printStackTrace();
		}
	}
	public void error(String msg, Exception e){
		System.err.println(msg);
		if(e!=null){
			e.printStackTrace();
		}
	}
	
}
