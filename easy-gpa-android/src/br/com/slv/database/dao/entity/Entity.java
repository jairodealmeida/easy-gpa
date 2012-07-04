package br.com.slv.database.dao.entity;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

import android.util.Log;
import br.com.slv.database.dao.entity.annotation.GPAEntity;
import br.com.slv.database.dao.entity.annotation.GPAField;
import br.com.slv.database.dao.entity.annotation.GPAPrimaryKey;
import br.com.slv.database.dao.model.FieldTO;
import br.com.slv.database.dao.model.TransferObject;

public abstract class Entity implements Serializable{
	
	public static final int VARCHAR = 1;
	public static final int INTEGER = 2;
	public static final int LONG = 3;
	public static final int FLOAT = 4;
	public static final int DATE =  5;
	public static final int BLOB =  6;
	public static final int BEAN =  7;
	
	/**
	 * metodo que gera um ID randomico 
	 * @return Long - chave  única
	 * @throws NoSuchAlgorithmException 
	 */
	public static Long getIdRandomico() {
		long random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG").nextInt(99999999);
			return random;
		} catch (NoSuchAlgorithmException e) {
			Log.e("ERROR",e.getLocalizedMessage(),e); 
		}
		return null;
	}
	
	public String getXML(){
		try {
			StringBuilder xml = new StringBuilder();
			Field[] fields = this.getClass().getDeclaredFields();	
			String className = this.getClass().getSimpleName();
			xml.append("<").append(className.toLowerCase()).append(">");
			for(int i=0; i<fields.length; i++){
				Field reflectionField = fields[i];
				String name = reflectionField.getName();
				Object value = reflectionField.get(this);
				xml.append(xml.append("<").append(name).append(">"));
				xml.append(value);
				xml.append(xml.append("</").append(name).append(">"));
			}
			xml.append("</").append(className.toLowerCase()).append(">");
			return xml.toString();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void valuable(TransferObject to){
		//try {
			Field[] fields = this.getClass().getDeclaredFields();	
			for(int i=0; i<fields.length; i++){
				
				try {
					

				Field reflectionField = fields[i];
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
					String name = pk.name();
					Object value = to.getValue(name);
					reflectionField.set(this, value);
					continue;
				}
				if(annoField!=null && annoField instanceof GPAField){
					GPAField field = (GPAField)annoField;
					String name = field.name();
					Object value = to.getValue(name);
					reflectionField.set(this, value);
					continue;
				}
				
				} catch (Exception e) {
					Log.e("ERROR",e.getLocalizedMessage(),e); 
				}
			}	
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
	}
	/**
	 * method used to get a table name of entity
	 * based in entity class name 
	 * @return String table name
	 */
	public String getTableName(){
		GPAEntity annoTable = (GPAEntity) this.getClass().getAnnotation(GPAEntity.class);
		String tableName = annoTable.name();
		return tableName;
	}
	

	//private String tableName;
	//private ArrayList<FieldTO> primaryKeyTos;
	//private ArrayList<FieldTO> fieldTos;
	
	//private String getTableName(){
	//	return tableName;
	//}
	//private void setPrimaryKeyTos(ArrayList<FieldTO> primaryKeyTos) {
	//	this.primaryKeyTos = primaryKeyTos;
	//}
	//private ArrayList<FieldTO> getPrimaryKeyTos() {
//		return primaryKeyTos;
//	}
//	private void setFieldTos(ArrayList<FieldTO> fieldTos) {
//		this.fieldTos = fieldTos;
//	}
//	private ArrayList<FieldTO> getFieldTos() {
//		return fieldTos;
//	}
}
