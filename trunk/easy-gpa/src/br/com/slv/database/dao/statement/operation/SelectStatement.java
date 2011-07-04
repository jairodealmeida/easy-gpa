package br.com.slv.database.dao.statement.operation;

import br.com.slv.database.dao.statement.transacts.Selectable;
/**
 * Class to make a selection SQL statement
 * @author jairo.almeida
 * @since 0.0.1	
 */
public class SelectStatement implements Selectable{
	
	private String tableName;
	private String whereClause;
	
	public SelectStatement(String tableName, String whereClause){
        this.tableName = tableName;
        this.whereClause = whereClause;
    }
	//TODO creating a create statements to select
	public StringBuilder createStatement() {
		if(tableName!=null && whereClause!=null){
			StringBuilder result = new StringBuilder();
			result.append("SELECT * FROM ");
			result.append(tableName);
			result.append(" WHERE ");
			result.append(whereClause);
			return result;
		}else{
			throw new NullPointerException("where not found");
		}
	}
}
