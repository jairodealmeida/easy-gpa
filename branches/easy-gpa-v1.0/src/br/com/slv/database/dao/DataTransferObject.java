package br.com.slv.database.dao;

import br.com.slv.database.DatabaseDAOImpl;
import br.com.slv.database.dao.model.TransferObject;
import br.com.slv.database.dao.model.FieldTO;
import br.com.slv.database.dao.statement.StatementFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class DataTransferObject extends DatabaseDAOImpl {
 
	static Logger log = Logger.getLogger(DataTransferObject.class);
    private Map<Integer,String> transaction = new HashMap<Integer,String>();
    
    public DataTransferObject(Connection connection){
    	this.setConnection(connection);
    }
        /**
         * transaction method to represents a client EditFeature
         * @param tos
         * @return success or fail
         */
	public String transact(ArrayList<TransferObject> tos) { 
                try {
                    return this.transactFeatures(tos);
                } catch (Exception e) {
                    return "fail";
                }
	} 
    private String transactFeatures(ArrayList<TransferObject> tos) throws Exception {
        if(tos!=null){
        	int rows=0;
        	transaction.clear();
            for(; rows<tos.size();rows++){
                TransferObject to = tos.get(rows);
                String executedSql = transact(to);
                transaction.put(rows, executedSql);
                log.info(rows + "  " + executedSql + "  " + to.toString() );
            }
            log.info("success to update " + rows + " rolls");
            return "success";
        }else{
            log.error("features are null", new NullPointerException("features are null"));
            return "fail";
        }	
    }
    private String transact(TransferObject to) throws Exception {
            StatementFactory statement = new StatementFactory(to);
            StringBuilder sqls = statement.createStatementSQL();
            if(sqls!=null){
                PreparedStatement pstm = super.prepareStatement(sqls.toString());
                statement.prepareStatement( pstm );  
                try  {
                    if(pstm.executeUpdate()<1){
                        throw new NullPointerException("transact error ...");
                    }
                    String nativeSql =super.nativeSQL(sqls.toString());
                    return nativeSql;
                } catch (Exception ex)  {
                    log.error(ex.getMessage(), ex); 
                }
            }else{
                log.error("prepare sql fail", new NullPointerException("prepare sql fail"));
            } 
            return sqls.toString();
        }

}
 
