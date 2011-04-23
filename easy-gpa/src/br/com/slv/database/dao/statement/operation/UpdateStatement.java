package br.com.slv.database.dao.statement.operation;

import br.com.slv.database.dao.model.TransferObject;
import br.com.slv.database.dao.model.FieldTO;
import br.com.slv.database.dao.statement.transacts.Updatable;

import java.util.ArrayList;

public class UpdateStatement implements Updatable {
	
    private TransferObject to;
    
    public UpdateStatement(TransferObject feature){
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
                         result.append("UPDATE ");
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
        ArrayList<FieldTO> fields = to.getFields();
        StringBuilder result = new StringBuilder();
        if(fields!=null && fields.size()>0){
            result.append(" SET ");
            for (int i = 0; i < fields.size(); i++)  {
                FieldTO field = fields.get(i);
                if(field!=null){
                    result.append(field.getName());
                    result.append("=");
                    result.append("?");
                    if( i==fields.size()-1 ){
                    	result.append(", ");
                    }
                }else{
                    throw new NullPointerException("fields is null");
                }
            }
            return result;
        }else{
            throw new NullPointerException("feature don't have fields");
        }
    }
}