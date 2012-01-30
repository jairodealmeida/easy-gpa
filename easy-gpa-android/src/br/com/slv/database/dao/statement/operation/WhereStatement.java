package br.com.slv.database.dao.statement.operation;

import br.com.slv.database.dao.model.FieldTO;

import java.util.ArrayList;
import java.util.List;


public class WhereStatement {
		private List<FieldTO> fields;
		 public WhereStatement(){
			 
		 }
	    public WhereStatement(List<FieldTO> fields){
	    	this.fields = fields;
	    }
		public StringBuilder createWhereStatement(){
			return createWhereStatement(fields);
		}
        public StringBuilder createWhereStatement(List<FieldTO> primaryKeys){
            if(primaryKeys!=null && primaryKeys.size()>0){
                StringBuilder result = new StringBuilder();
                result.append(" WHERE ");
                for (int i = 0; i < primaryKeys.size(); i++)  {
                    FieldTO pk = primaryKeys.get(i);
                    if(pk!=null){
                        result.append( pk.getName() );
                        result.append( "=" );
                        //TASK pk.getSqlType() == Field. make to validation type Prepared statement
                        //result.append( "'" + pk.getValue()  + "'");    
                         result.append( "?");    
                    }else{
                        throw new NullPointerException("primary key (" + i + ") is null");
                    }
                    if(i<primaryKeys.size()-1){
                        result.append( " AND " );
                    }
                }
                return result;
            }else{
                throw new NullPointerException("impossible make the where clause without primary keys");
            }
        }
        public String[] getArguments(){
        	String[] values = new String[fields.size()];
        	for (int i = 0; i < values.length; i++) {
				values[i] = String.valueOf( fields.get(i).getValue() );
			}
        	return values;
        }
        
}

