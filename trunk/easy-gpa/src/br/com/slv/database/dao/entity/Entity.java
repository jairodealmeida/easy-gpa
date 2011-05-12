package br.com.slv.database.dao.entity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.slv.database.ConnectionFactory;
import br.com.slv.database.dao.DataTransferObject;
import br.com.slv.database.dao.entity.annotation.GPAEntity;
import br.com.slv.database.dao.entity.annotation.GPAField;
import br.com.slv.database.dao.entity.annotation.GPAPrimaryKey;
import br.com.slv.database.dao.model.FieldTO;
import br.com.slv.database.dao.model.TransferObject;
@Deprecated // TODO Entity is a bad pratice to make OO persistence entities
			// is necessary move this method to DataTranferDelegate os other business artifact
public abstract class Entity {
	
	private ArrayList<FieldTO> primaryKeyTos;
	private ArrayList<FieldTO> fieldTos;
	/**
	 * method used to get a table name of entity
	 * based in entity class name 
	 * TODO alter by annotation class name
	 * @return String table name
	 */
	private String getTableName(){
		GPAEntity annoTable = this.getClass().getAnnotation(GPAEntity.class);
		String tableName = annoTable.name();
		return tableName;
	}
	private String transact(TransferObject to) throws SQLException, ClassNotFoundException{
		Connection conn = ConnectionFactory.getConnection();
		DataTransferObject dto = new DataTransferObject(conn);
		ArrayList<TransferObject> items = new ArrayList<TransferObject>();
		items.add(to);
		return dto.transact(items);	
	}
	/**
	 * method will use to insert objects
	 * @return "success or fail"
	 */	
	public String insert(){
		try {
			prepareFields(false);
			String tableName = getTableName();
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
	public String update(){
		try {
			prepareFields(true);
			String tableName = getTableName();
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
	public String delete(){
		try {
			prepareFields(true);
			String tableName = getTableName();
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
	private void prepareFields(boolean usePrimaryKey) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		primaryKeyTos = new ArrayList<FieldTO>();
		fieldTos = new ArrayList<FieldTO>();
		Field[] fields = this.getClass().getDeclaredFields();	
		
		//trunk entity to persistence
		for(int i=0; i<fields.length; i++){
			Field reflectionField = fields[i];
			if(reflectionField!=null){
				reflectionField.setAccessible(true);
				Annotation annoField = reflectionField.getAnnotation(GPAField.class);
				/* 
				 ainda falta validar a chave primária do objeto
				 por enquanto so esta prevendo pk usando sequence no banco
				 objeto id sempre é gerado no banco por uma sequence
				*/
				if(annoField instanceof GPAPrimaryKey){
					GPAPrimaryKey pk = (GPAPrimaryKey)annoField;
					String name = pk.name();
					Object value = reflectionField.get(this);
					primaryKeyTos.add(new FieldTO(name, value));
					continue;
				}
				if(annoField instanceof GPAPrimaryKey){
					GPAField field = (GPAField)annoField;
					String name = field.name();
					Object value = reflectionField.get(this);
					fieldTos.add(new FieldTO(name, value));
					continue;
				}
			}
		}
	}

}
