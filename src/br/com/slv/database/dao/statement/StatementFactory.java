package br.com.slv.database.dao.statement;

import br.com.slv.database.dao.model.TransferObject;
import br.com.slv.database.dao.model.FieldTO;
import br.com.slv.database.dao.statement.operation.DeleteStatement;
import br.com.slv.database.dao.statement.operation.InsertStatement;
import br.com.slv.database.dao.statement.operation.UpdateStatement;
import br.com.slv.database.dao.statement.transacts.Transactionable;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class StatementFactory {

        private TransferObject to;
        private StringBuilder fieldValues = new StringBuilder();
        
        public StatementFactory(TransferObject to){
            this.to = to;
        }
        
        public StringBuilder createStatementSQL() throws Exception{
            Transactionable sql = this.parseToTransactionable(to);
            if(sql!=null){
                return sql.createStatement();
            }else{
                throw new NullPointerException("transactionable item is null");
            }
        }
        
        public void prepareStatement(PreparedStatement pstm ) throws SQLException {   
            ArrayList<FieldTO> allFields = this.getClauseFields(to);
            this.preparedFields(pstm, allFields);
        }

        private void preparedFields(PreparedStatement pstm , ArrayList<FieldTO> clauseFields ) throws SQLException {
            if(clauseFields!=null){
                for(int countFields=0; countFields < clauseFields.size(); countFields++){
                    FieldTO field = clauseFields.get(countFields);
                    if(field!=null){
                        int pstmPos = countFields+1;
                        Object value = field.getValue();
                        int type = field.getFieldType();
                        this.setValue(pstmPos,value,type,pstm);
                        fieldValues.append(":" + pstmPos + " = '" + value + "'");
                        if(countFields < clauseFields.size()-1){
                            fieldValues.append(", ");
                        }
                    }else{
                        throw new NullPointerException("field is null");
                    }
                }
            }
        }
 
        private ArrayList<FieldTO> getClauseFields(TransferObject to) throws SQLException {
            ArrayList<FieldTO> clauseFields = new ArrayList<FieldTO>();
            //attributs to transact
            if(!to.isDeleted()){ //no append atributes
                clauseFields.addAll( to.getFields() );
            }
            if(!to.isInserted()){ //where clause
                clauseFields.addAll( to.getPrimaryKeys() );
            }
            return clauseFields;
        }
        private boolean isInteger(String num){
            try{
                Integer.parseInt(num);
                return true;
            } catch(NumberFormatException nfe) {
                return false;
            }
        }
        private boolean isDouble(String num){
            try{
                Double.parseDouble(num);
                return true;
            } catch(NumberFormatException nfe) {
                return false;
            }
        }
        private boolean isLong(String num){
            try{
                Long.parseLong(num);
                return true;
            } catch(NumberFormatException nfe) {
                return false;
            }
        }
        
        private void setValue(int index, 
                              Object value, 
                              int type,
                              PreparedStatement pstm) throws SQLException {
            Connection connection = pstm.getConnection();
            if(connection!=null){
                if(value==null){
                    pstm.setObject(index, null) ;
                    return;
                }
                pstm.setObject(index, value) ;
            }else{
                throw new NullPointerException("connection is null");
            }
        }
        
        private Transactionable parseToTransactionable(TransferObject to) throws Exception{
            int transactionType = to.getTransactionType();
            switch (transactionType)  {
                case TransferObject.INSERT_TYPE: 
                    return new InsertStatement(to);
                case TransferObject.UPDATE_TYPE: 
                    return new UpdateStatement(to);
                case TransferObject.DELETE_TYPE: 
                    return new DeleteStatement(to);
                default: 
                    throw new NullPointerException("transaction type not exist");
            }
        }


    public StringBuilder getFieldValues() {
        return fieldValues;
    }


}

