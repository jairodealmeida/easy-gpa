package br.com.slv.database.dao.entity;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import br.com.slv.database.dao.entity.annotation.GPAEntity;
import br.com.slv.database.dao.entity.annotation.GPAField;
import br.com.slv.database.dao.entity.annotation.GPAPrimaryKey;
import br.com.slv.database.dao.model.FieldTO;

public abstract class Entity implements Serializable{
	
	public static final int VARCHAR = 1;
	public static final int INTEGER = 2;
	public static final int LONG = 3;
	
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
