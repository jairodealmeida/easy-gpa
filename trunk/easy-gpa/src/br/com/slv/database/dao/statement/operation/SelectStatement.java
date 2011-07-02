package br.com.slv.database.dao.statement.operation;

import br.com.slv.database.dao.model.TransferObject;
import br.com.slv.database.dao.statement.transacts.Selectable;

public class SelectStatement implements Selectable{
	
	private TransferObject to;
	
	public SelectStatement(TransferObject feature){
        this.to = feature;
    }
	//TODO creating a create statements to select
	public StringBuilder createStatement() {
		//StringBuilder result = new StringBuilder();
		//result.append("SELECT * FROM ");
		//result.append(tableName);
		//result.append("WHERE ");
		//result.append(whereClause);
		return null;
	}
}
