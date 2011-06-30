package br.com.slv.database.dao.delegate;

import br.com.slv.database.ConnectionFactory;
import br.com.slv.database.dao.DataTransferObject;
import br.com.slv.database.dao.entity.Entity;
import br.com.slv.database.dao.entity.annotation.GPAEntity;
import br.com.slv.database.dao.entity.annotation.GPAField;
import br.com.slv.database.dao.entity.annotation.GPAPrimaryKey;
import br.com.slv.database.dao.model.FieldTO;
import br.com.slv.database.dao.model.TransferObject;

import java.util.ArrayList;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DataTransferDelegate {
 
	private DataTransferObject dao = null;
	 @Deprecated
	private ArrayList<FieldTO> primaryKeyTos;
	 @Deprecated
	private ArrayList<FieldTO> fieldTos;
	
	static Logger log = Logger.getLogger(DataTransferDelegate.class);
	
	/**
	 * method used to get a table name of entity
	 * based in entity class name 
	 * TODO alter by annotation class name
	 * @return String table name
	 */
	@Deprecated
	private String getTableName(Entity entity){
		GPAEntity annoTable = (GPAEntity) entity.getClass().getAnnotation(GPAEntity.class);
		String tableName = annoTable.name();
		return tableName;
	}
	@Deprecated
	private void setPrimaryKeyTos(ArrayList<FieldTO> primaryKeyTos) {
		this.primaryKeyTos = primaryKeyTos;
	}
	@Deprecated
	private ArrayList<FieldTO> getPrimaryKeyTos() {
		return primaryKeyTos;
	}
	@Deprecated
	private void setFieldTos(ArrayList<FieldTO> fieldTos) {
		this.fieldTos = fieldTos;
	}
	@Deprecated
	private ArrayList<FieldTO> getFieldTos() {
		return fieldTos;
	}
    
	public DataTransferDelegate(){
	//	try {
			//Connection connection = ConnectionFactory.getConnection();
			dao = new DataTransferObject();
		//} catch (SQLException e) {
	//		e.printStackTrace();
	//	} catch (ClassNotFoundException e) {
	//		e.printStackTrace();
	//	}
	}
	
	public ArrayList<Entity> select(String tableName, String whereClause){
		try {
			ArrayList<Entity> entities = dao.select(tableName, whereClause);
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage(), e); 
		}
		return null;
	}
	
	/**
	 * method will use to insert objects
	 * @return "success or fail"
	 */	
	public String insert(Entity entity){
		try {
			this.prepareFields(entity, false);
			String tableName = this.getTableName(entity);
			TransferObject to = new TransferObject(
						tableName, 
						fieldTos, 
						TransferObject.INSERT_TYPE);
			return transact(to);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}
	/**
	 * method will use to update objects
	 * @return "success or fail"
	 */
	public String update(Entity entity){
		try {
			this.prepareFields(entity, true);
			String tableName = this.getTableName(entity);
			TransferObject to = new TransferObject(
						tableName,
						primaryKeyTos,
						fieldTos, 
						TransferObject.UPDATE_TYPE);
			return transact(to);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}
	/**
	 * method will use to delete objects
	 * @return "success or fail"
	 */
	public String delete(Entity entity){
		try {
			this.prepareFields(entity, true);
			String tableName = this.getTableName(entity);
			TransferObject to = new TransferObject(
						tableName,
						primaryKeyTos,
						fieldTos, 
						TransferObject.DELETE_TYPE);
			return transact(to);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}

	
	private String transact(ArrayList<TransferObject> features)  throws Exception {
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
	
	private String transact(TransferObject feature)  throws Exception {
	    long start, end;
	    start = (new java.util.Date()).getTime(); 
            if(feature!=null){
                dao.connect( true );
                dao.setAutoCommit(false);
                String result = dao.transact(feature);
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
	/**
	 * method to prepare the statments by transact entity persistences
	 * using to get a fields of entitys and setting a obfuscate values
	 * in statement sql
	 * Translate a entity class to FieldTO (field transfer objects)
	 * this objects TO are using in persistence atributs of tables
	 * TODO alter by annotation field name
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void prepareFields(Entity entity, boolean usePrimaryKey) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		primaryKeyTos = new ArrayList<FieldTO>();
		fieldTos = new ArrayList<FieldTO>();
		Field[] fields = entity.getClass().getDeclaredFields();	
		
		//trunk entity to persistence
		for(int i=0; i<fields.length; i++){
			Field reflectionField = fields[i];
			if(reflectionField!=null){
				reflectionField.setAccessible(true);
				Annotation annoField = reflectionField.getAnnotation(GPAField.class);
				Annotation annoFieldPK = reflectionField.getAnnotation(GPAPrimaryKey.class);
				/* 
				 ainda falta validar a chave primária do objeto
				 por enquanto so esta prevendo pk usando sequence no banco
				 objeto id sempre é gerado no banco por uma sequence
				*/
				if(annoFieldPK!=null && annoFieldPK instanceof GPAPrimaryKey){
					GPAPrimaryKey pk = (GPAPrimaryKey)annoFieldPK;
					//if(pk.ignore() == true){
					//	continue;
					//}else{
					String name = pk.name();
					Object value = reflectionField.get(entity);
					primaryKeyTos.add(new FieldTO(name, value));
					continue;
					//}
				}
				if(annoField!=null && annoField instanceof GPAField){
					GPAField field = (GPAField)annoField;
					String name = field.name();
					Object value = reflectionField.get(entity);
					fieldTos.add(new FieldTO(name, value));
					continue;
				}
			}
		}
	}
}
 
