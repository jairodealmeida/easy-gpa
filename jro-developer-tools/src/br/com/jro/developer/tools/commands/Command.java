package br.com.jro.developer.tools.commands;

public abstract class Command {
	
	private String   commandName = null;
	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	private String[] commandValues = null;
	public String[] getCommandValues() {
		return commandValues;
	}

	public void setCommandValues(String[] commandValues) {
		this.commandValues = commandValues;
	}

	private String   commandDescription = null;
	
	public String getCommandDescription() {
		return commandDescription;
	}

	public void setCommandDescription(String commandDescription) {
		this.commandDescription = commandDescription;
	}

	public abstract void execute() throws Exception;
	
}
