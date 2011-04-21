package br.com.slv.database.dao.statement.operation;

import br.com.slv.database.dao.model.TransferObject;
import br.com.slv.database.dao.model.FieldTO;
import br.com.slv.database.dao.statement.transacts.Deletable;

import java.util.ArrayList;

public class DeleteStatement implements Deletable {
    
	private TransferObject to;
    private boolean coolPersistence = false;
    
    public DeleteStatement(TransferObject feature){
        this.to = feature;
    }
    public StringBuilder createStatement() {
        if(to!=null){
             StringBuilder result = new StringBuilder();
             ArrayList<FieldTO> pks = to.getPrimaryKeys();
             if(pks!=null){
                 StringBuilder cws = where.createWhereStatement(pks);
                 if(cws!=null && !cws.toString().equalsIgnoreCase("")){
                    String tableName = to.getTableName();
                     if(tableName!=null && !tableName.equalsIgnoreCase("")){
                    	 if(coolPersistence){
                    		 result.append("UPDATE ");
                    	 }else{
                    		 result.append("DELETE ");
                    	 }
                         result.append(tableName);
                         result.append(this.getParameters());
                         result.append(cws);
                         result.append("\n");
                         return result;
                     }else{
                         throw new NullPointerException("invalid table name = '" + tableName + "'");
                     }
                 }else{
                     throw new NullPointerException("invalid where clause");
                 }
             }else{
                 throw new NullPointerException("impossible make the where " +
                                                "clause without primary keys");
             }
        }
        return null;
    }
    public StringBuilder getParameters(){
        StringBuilder result = new StringBuilder();
        if(coolPersistence){
        	result.append(" SET ");
        	if(to.isMerged()){
        		result.append(" REMEMBRADO");
        		result.append("=");
        		result.append("'S', ");
        	}
        	result.append(" SINCDEL");
        	result.append("=");
        	result.append("'S'");
        }
        return result;
    }

}
