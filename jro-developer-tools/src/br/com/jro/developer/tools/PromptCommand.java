/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jro.developer.tools;

import org.apache.log4j.Logger;

import br.com.jro.developer.tools.commands.Command;

/**
 *
 * @author jairodealmeida
 */
   public class PromptCommand{
	   private static Logger log = Logger.getLogger(PromptCommand.class);
 
	   public static final String[][] CMD_ARRAY = new String[][]{
	    new String[]{"-h","br.com.jro.developer.tools.commands.CommandHelp"},
	    new String[]{"-d","br.com.jro.developer.tools.commands.CommandListFiles"},
	    new String[]{"-t","br.com.jro.developer.tools.commands.CommandListToken"},
	    new String[]{"-m","br.com.jro.developer.tools.commands.CommandMerge"},
	    new String[]{"-wkt", "br.com.jro.developer.tools.commands.CommandShapefile"}
	   };
	 
	   public boolean execute(String[] args) throws Exception{
           	String[] cmd_array_item = null;
           	if(args==null || args.length==0)
           		return false;
            String token = args[0];
            cmd_array_item = tokenValidate(token);
            if(!token.equalsIgnoreCase("") && cmd_array_item==null ){
            	log.info("Parameter <" + token + "> not found.");
            	return false;
            }else{
            	command(cmd_array_item,args);
            	return true;
            }
        }
	    private void listParameters(String[] args, String[] command_values){
	    	if(args.length>0){
	    		command_values[0] = "-h";
	    		return;
	    	}	    	
            for (int i=0; i<args.length; i++) {
            	String token = args[i];
            	String[] new_token = null;
            	new_token = new String[]{token};
            	if(tokenValidate(token)==null){
            		
            		System.arraycopy(command_values,0, new_token, command_values.length, 2);
            	}
            }
	    }
	    private void command(String[] cmd_array_item) {
            try {
            	String class_name = cmd_array_item[1];
				Command command_exe = (Command)Class.forName(class_name).newInstance();
				command_exe.execute();
			} catch (Exception e) {
				log.error(e.getMessage());
			} 
        }
        private void command(String[] cmdArrayItem,String[] commandValues) throws Exception {
        	String class_name = cmdArrayItem[1];
			Command commandExe = (Command)Class.forName(class_name).newInstance();
			commandExe.setCommandValues(commandValues);
			commandExe.execute();
        }
        private String[] tokenValidate(String token){
            for (String[] token_item : CMD_ARRAY) {
                if(token_item[0].equalsIgnoreCase(token)){
                    return token_item;
                }
            }
            return null;
        }
    }