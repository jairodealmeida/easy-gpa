package br.com.slv.database.dao.delegate;

import br.com.slv.database.ConnectionFactory;
import br.com.slv.database.dao.DataTransferObject;
import br.com.slv.database.dao.model.TransferObject;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DataTransferDelegate {
 
	private DataTransferObject dao = null;
	 
	static Logger log = Logger.getLogger(DataTransferDelegate.class);
    
	public void EditFEatureDelegate(){
		try {
			Connection connection = ConnectionFactory.getConnection();
			dao = new DataTransferObject(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
     
	public String transact(ArrayList<TransferObject> features)  throws Exception {
	    long start, end;
	    start = (new java.util.Date()).getTime(); 
            if(features!=null && features.size()>0){
                dao.connect( true );
                dao.setAutoCommit(false);
                String result = dao.transact(features);
                if(result.equals("success")){
                    dao.commit();
                }else{
                    dao.rollback();
                }
                dao.setAutoCommit(true);
                dao.close();
                end = (new java.util.Date()).getTime();
                log.info("Time to query: " + (end - start) + " ms");
                return result;        
            }else{
                throw new NullPointerException("don't have features to transact");
            }
            
    }
}
 
