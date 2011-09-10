package br.com.slv.database;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

import br.com.slv.database.dao.entity.Entity;
import br.com.slv.database.dao.entity.annotation.GPAEntity;
import br.com.slv.database.dao.entity.annotation.GPAField;
import br.com.slv.database.dao.entity.annotation.GPAPrimaryKey;
import br.com.slv.database.dao.model.FieldTO;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;

public class Repositorio  {
	
	private SQLiteDatabase db;  
	private Class<?> entity;
	
	public String getTableName() {
		Annotation annoClass = entity.getAnnotation(GPAEntity.class);
		GPAEntity anno = (GPAEntity)annoClass;
		String tableName = anno.name();
		return tableName;
	}

	public Repositorio(Context ctx,
						Class<?> entity,
						String databaseName,
						int version) throws Exception{
		
		this.entity = entity;
        SQLiteHelper dbHelper = new SQLiteHelper(ctx,  
                databaseName, 
                version,   
                createScript(), 
                deleteScript());  
        db = dbHelper.getWritableDatabase();  
	}

	public void close(){
        db.close();
    }
	
	public SQLiteDatabase getDb(){
		return db;
	}
	public String createScript() throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("create table ");
		sb.append(getTableName());
		sb.append(createStatementParams(entity,true));
		return sb.toString();
	}
	public String deleteScript(){
		StringBuilder sb = new StringBuilder();
		sb.append("drop table if exists ");
		sb.append(getTableName() + ";");
		return sb.toString();
	}
	/**
	 * method to prepare the statments by transact entity persistences
	 * using to get a fields of entitys and setting a obfuscate values
	 * in statement sql
	 * Translate a entity class to FieldTO (field transfer objects)
	 * this objects TO are using in persistence atributs of tables
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String createStatementParams(Class<?> entity, boolean usePrimaryKey) 
			throws Exception{
		
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		Field[] fields = entity.getDeclaredFields();	
		
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
					String name = pk.name();
					sb.append( name + " integer primary key");
					if(pk.ignore() == true){
						sb.append(" autoincrement ");
					}
					if(i<fields.length-1){
						sb.append(",");
					}
					continue;
				}
				if(annoField!=null && annoField instanceof GPAField){
					GPAField field = (GPAField)annoField;
					String name = field.name();
					Class<?> type = reflectionField.getType();
					sb.append( name + " " + getSqLiteType(type));
					if(i<fields.length-1){
						sb.append(",");
					}
					continue;
				}
			}
		}
		sb.append(");");
		return sb.toString();
	}
	/**
	 * method to get SqLite data types
	 * http://www.sqlite.org/datatype3.html
	 */
	private String getSqLiteType(Class<?> value){
		String sn = value.getSimpleName();
		if(sn.equalsIgnoreCase("String"))
			return "text";
		if(sn.equalsIgnoreCase("int") || sn.equalsIgnoreCase("Integer") || 
		   sn.equalsIgnoreCase("long") || sn.equalsIgnoreCase("Long") || 
		   sn.equalsIgnoreCase("BigDecimal")){
			return "integer";
		}
		if(sn.equalsIgnoreCase("double") || sn.equalsIgnoreCase("Double") || 
		   sn.equalsIgnoreCase("float") || sn.equalsIgnoreCase("Float")){
			return "integer";
		}
		if(sn.equalsIgnoreCase("byte[]") || sn.equalsIgnoreCase("Byte[]")){
			return "blob";
		}
		throw new NullPointerException("type not found " + sn);
	}
}
