package br.com.slv.database.dao;

import br.com.gpa.util.Logger;
import br.com.slv.database.DatabaseDAOImpl;
import br.com.slv.database.dao.entity.Entity;
import br.com.slv.database.dao.model.TransferObject;
import br.com.slv.database.dao.model.FieldTO;
import br.com.slv.database.dao.statement.StatementFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataTransferObject extends DatabaseDAOImpl {
 
	static Logger log = Logger.getLogger(DataTransferObject.class);
    private Map<Integer,String> transaction = new HashMap<Integer,String>();
    
    /**
     * 
     * @param entityName - 
     * @param whereClause
     * @return
     */
    public ArrayList<Entity> select(String entityClassName, String whereClause){
       	//ArrayList<Entity> items = new ArrayList<Entity>();
    	try {
    		//Entity entityRoot = (Entity)Class.forName( entityClassName ).newInstance();	
            StatementFactory statement = new StatementFactory(null);
            StringBuilder sqls = statement.createStatementSQL();
            if(sqls!=null){
                PreparedStatement pstm = super.prepareStatement(sqls.toString());
                statement.prepareStatement( pstm );  
                ResultSet rs = pstm.executeQuery();
                ArrayList<Entity> entities = this.getEntitys(rs);
                return entities;
            }else{
                log.error("prepare sql fail", new NullPointerException("prepare sql fail"));
            } 		
		} catch (Exception e) {
			log.error(e);
		} 
		return null;
    }
    private ArrayList<Entity> getEntitys( ResultSet rs) throws SQLException{
        while(rs.next()){
        	String test = rs.getString(0);
           	//TransferObject to = new TransferObject(
           	//		tableName, 
           	//		fields, 
           	//		TransferObject.READ_TYPE);
        }
        return null;
    }
    /**
     * transaction method to represents a client EditFeature
     * @param tos
     * @return success or fail
     */
	public String transact(ArrayList<TransferObject> tos) { 
                try {
                    return this.transactEntitys(tos);
                } catch (Exception e) {
                	log.error(e);
                    return "fail";
                }
	} 
	/**
	 * transact entity's in database instance 
	 * @param tos - Transfer Objects in database
	 * @return transact or fail
	 * @throws Exception
	 */
    private String transactEntitys(ArrayList<TransferObject> tos) throws Exception {
        if(tos!=null){
        	int rows=0;
        	transaction.clear();
            for(; rows<tos.size();rows++){
                TransferObject to = tos.get(rows);
                String executedSql = transact(to);
                transaction.put(rows, executedSql);
                log.info( "transact rows : " + rows );
                log.info( "transact sql  : " + executedSql );
                log.info( "transact to   : " + to.toString() );
            }
            log.info("success to update " + rows + " rolls");
            return "success";
        }else{
            log.error("features are null", new NullPointerException("features are null"));
            return "fail";
        }	
    }
    public String transact(TransferObject to) throws Exception {
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
                    return "success";
                } catch (Exception ex)  {
                	log.error(sqls.toString());
                    log.error(ex.getMessage(), ex); 
                }
            }else{
                log.error("prepare sql fail", new NullPointerException("prepare sql fail"));
            } 
            return "fail";
        }

}
 
