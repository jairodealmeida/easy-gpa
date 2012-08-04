package br.com.slv.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;

import br.com.gpa.util.DateUtil;
import br.com.slv.database.dao.entity.Entity;
import br.com.slv.database.dao.entity.annotation.GPAEntity;
import br.com.slv.database.dao.entity.annotation.GPAField;
import br.com.slv.database.dao.entity.annotation.GPAFieldBean;
import br.com.slv.database.dao.entity.annotation.GPAPrimaryKey;
import br.com.slv.database.dao.model.FieldTO;
import br.com.slv.database.dao.model.TransferObject;
import br.com.slv.database.dao.statement.StatementFactory;
import br.com.slv.database.dao.statement.operation.SelectStatement;
import br.com.slv.database.dao.statement.operation.StatementArguments;
import br.com.slv.database.dao.statement.operation.WhereStatement;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Repository  {
	
	@Deprecated
	private ArrayList<FieldTO> primaryKeyTos;
	 @Deprecated
	private ArrayList<FieldTO> fieldTos;
	
	private SQLiteDatabase db;  
	private Class<?> entity;
	
	private Context context;
	
	public String getTableName() {
		Annotation annoClass = entity.getAnnotation(GPAEntity.class);
		GPAEntity anno = (GPAEntity)annoClass;
		String tableName = anno.name();
		return tableName;
	}
	/**
	 * 
	 * @param ctx
	 * @param entity
	 * @param databaseName
	 * @param version
	 * @throws Exception
	 *//*
	public Repository(Context ctx,
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
	}*/
	
	public Repository( 
			Context ctx, 
			String dataBaseName, 
			String dataBasePath, 
			int dataBaseVersion,
			Class<?> entity){
	    this.context = ctx;
	    this.entity = entity;
	    //final String DB_PATH = "/data/data/br.azeitona.app/databases/";
		final String DB_DESTINATION = dataBasePath + dataBaseName;
		try {
			// Check if the database exists before copying
			boolean initialiseDatabase = (new File(DB_DESTINATION)).exists();
			if (initialiseDatabase == false) {
				//Preferences.setDatabaseName(dataBaseName);
			    // Open the .db file in your assets directory
			    InputStream is = ctx.getAssets().open(dataBaseName);
			    
			    File dirDb = new File(dataBasePath);
			    if (!dirDb.exists()){
			    	dirDb.mkdirs();
			    }
			    // Copy the database into the destination
			    OutputStream os = new FileOutputStream(DB_DESTINATION);
			    byte[] buffer = new byte[1024];
			    int length;
			    while ((length = is.read(buffer)) > 0){
			        os.write(buffer, 0, length);
			    }
			    os.flush();
			    os.close();
			    is.close();
			}
		} catch (Exception e) {
			Log.e("ERROR",e.getLocalizedMessage(),e);
		}
		if(db!=null){
			db.close();
		}
	    SQLiteHelper dbHelper = new SQLiteHelper(ctx,  
	    		dataBaseName, 
	       		dataBaseVersion,   
	            null, 
	            null);  
	    db = dbHelper.getWritableDatabase();

	}
	
	/**
	 * 
	 */
	public void close(){
        db.close();
    }
	/**
	 * 
	 * @return
	 */
	public SQLiteDatabase getDb(){
		return db;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String createScript() throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("create table ");
		sb.append(getTableName());
		sb.append(createStatementParams(entity,true));
		return sb.toString();
	}
	/**
	 * 
	 * @return
	 */
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
				Annotation annoFieldBean = reflectionField.getAnnotation(GPAFieldBean.class);
				/* 
				 ainda falta validar a chave prim�ria do objeto
				 por enquanto so esta prevendo pk usando sequence no banco
				 objeto id sempre � gerado no banco por uma sequence
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
				if(annoFieldBean!=null && annoFieldBean instanceof GPAFieldBean){
					GPAFieldBean field = (GPAFieldBean)annoFieldBean;
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
	private void prepareFields(Entity entity, boolean usePrimaryKey) 
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
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
				Annotation annoFieldBean = reflectionField.getAnnotation(GPAFieldBean.class);
				/* 
				 ainda falta validar a chave prim�ria do objeto
				 por enquanto so esta prevendo pk usando sequence no banco
				 objeto id sempre � gerado no banco por uma sequence
				*/
				if(annoFieldPK!=null && annoFieldPK instanceof GPAPrimaryKey){
					GPAPrimaryKey pk = (GPAPrimaryKey)annoFieldPK;
					//if(pk.ignore() == true){
					//	continue;
					//}else{
					String name = pk.name();
					Object value = reflectionField.get(entity);
					fieldTos.add(new FieldTO(name, value));
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
				if(annoFieldBean!=null && annoFieldBean instanceof GPAFieldBean){
					GPAFieldBean field = (GPAFieldBean)annoFieldBean;
					String name = field.name();
					Object value = reflectionField.get(entity);
					fieldTos.add(new FieldTO(name, value));
					continue;
				}
			}
		}
	}
	/**
	 * method will use to insert objects
	 * @return "success or fail"
	 */	
	public long insert(Entity entity){
		try {
			this.prepareFields(entity, false);
			String tableName = this.getTableName();
			TransferObject to = new TransferObject(
						tableName, 
						fieldTos, 
						TransferObject.INSERT_TYPE);
			return transactStatements(to);
		} catch (Exception e) {
			Log.e("GPALOG" , e.getMessage(),e); 
		}
		return 0;
	}
	public long insert(List<Entity> entities){
		try {
			int sum = 0;
			getDb().beginTransaction();
			for (Entity entity : entities) {
				sum += insert(entity);	
			}
			getDb().setTransactionSuccessful();	
			return sum;
		} catch (Exception e) {
			Log.e("GPALOG" , e.getMessage(),e); 
		}finally{
			getDb().endTransaction();
		}
		return 0;
	}
	public long update(List<Entity> entities){
		try {
			int sum = 0;
			getDb().beginTransaction();
			for (Entity entity : entities) {
				sum += update(entity);	
			}
			getDb().setTransactionSuccessful();	
			return sum;
		} catch (Exception e) {
			Log.e("GPALOG" , e.getMessage(),e); 
		}finally{
			getDb().endTransaction();
		}
		return 0;
	}
	public long delete(List<Entity> entities){
		try {
			int sum = 0;
			getDb().beginTransaction();
			for (Entity entity : entities) {
				sum += delete(entity);	
			}
			getDb().setTransactionSuccessful();	
			return sum;
		} catch (Exception e) {
			Log.e("GPALOG" , e.getMessage(),e); 
		}finally{
			getDb().endTransaction();
		}
		return 0;
	}
	/**
	 * method will use to update objects
	 * @return "success or fail"
	 */
	public long update(Entity entity){
		try {
			this.prepareFields(entity, true);
			String tableName = this.getTableName();
			TransferObject to = new TransferObject(
						tableName,
						primaryKeyTos,
						fieldTos, 
						TransferObject.UPDATE_TYPE);
			return transactStatements(to);
		} catch (Exception e) {
			Log.e("GPALOG" , e.getMessage(),e); 
		}
		return 0;
	}
	
	/**
	 * method will use to delete objects
	 * @return "success or fail"
	 */
	public long delete(Entity entity){
		try {
			this.prepareFields(entity, true);
			String tableName = this.getTableName();
			TransferObject to = new TransferObject(
						tableName,
						primaryKeyTos,
						fieldTos, 
						TransferObject.DELETE_TYPE);
			

			
			return transactStatements(to);
		} catch (Exception e) {
			Log.e("GPALOG" , e.getMessage(),e); 
		}
		return 0;
	}
	public long removeAll(){
		try {
			//TODO
			//this.prepareFields(entity, true);
			String tableName = this.getTableName();
			TransferObject to = new TransferObject(
						tableName,
						primaryKeyTos,
						fieldTos, 
						TransferObject.DELETE_TYPE);
			return transactStatements(to);
		} catch (Exception e) {
			Log.e("GPALOG" , e.getMessage(),e); 
		}
		return 0;
	}
	public List<TransferObject> selectAll(Class<?> entity){
		long start, end;
		start = (new java.util.Date()).getTime(); 
		List<TransferObject> items = new ArrayList<TransferObject>();
		Cursor c = null;
		try {
			String tableName = this.getTableName();
			c = getDb().query(
			tableName, null, null, null, null, null, null);  
			c.moveToFirst();  
			while(!c.isAfterLast()){  
				TransferObject bean = this.fill(c);
				items.add( bean ); 
				c.moveToNext();  
			} 
			c.close();
			return items;
		} catch (Exception e) {
			Log.e("GPALOG" , e.getMessage(),e); 
		}finally{
			if(c!=null){c.close();}
			end = (new java.util.Date()).getTime();
			Log.i("GPALOG", "Time to query: " + (end - start) + " ms");
		}
		return null;
	}
	/**
	 * Method to get resultset collections from database
	 * this methodo works with select sintax
	 * @param tableName - Table name
	 * @param whereClause - Where clause
	 * @return List<TransferObject> select entities result from database
	 */
	public List<TransferObject> select(WhereStatement whereClause){
	    long start, end;
	    start = (new java.util.Date()).getTime(); 
	    String tableName = this.getTableName();
	    List<TransferObject> items = new ArrayList<TransferObject>();
	    Cursor c = null;
	    StatementArguments arguments = new StatementArguments(tableName);
	    
	    arguments.setWhereClause(whereClause.createWhereStatement().toString());
	    SelectStatement sql = new SelectStatement(arguments);
		try {
			c = getDb().rawQuery(sql.createStatement().toString(), whereClause.getArguments());
			Log.i("GPA", sql.createStatement().toString());
	        c.moveToFirst();  
	        while(!c.isAfterLast()){  
	        	TransferObject bean = this.fill(c);
	        	items.add( bean ); 
	        	c.moveToNext();  
	        } 
	        c.close();
	        return items;
		} catch (Exception e) {
			Log.e("GPALOG" , e.getMessage(),e); 
		}finally{
			   if(c!=null){c.close();}
	           end = (new java.util.Date()).getTime();
	           Log.i("GPALOG", "Time to query: " + (end - start) + " ms");
		}
		return null;
	}
	public  List<TransferObject> rawQuery(StringBuilder sql, String[] args){
	    long start, end;
	    start = (new java.util.Date()).getTime(); 
	    List<TransferObject> items = new ArrayList<TransferObject>();
	    Cursor c = null;
		try {
			c = getDb().rawQuery(sql.toString(), args);
			Log.i("GPA", sql.toString());
	        c.moveToFirst();  
	        while(!c.isAfterLast()){  
	        	TransferObject bean = this.fill(c);
	        	items.add( bean ); 
	        	c.moveToNext();  
	        } 
	        c.close();
	        return items;
		} catch (Exception e) {
			Log.e("GPALOG" , e.getMessage(),e); 
		}finally{
			   if(c!=null){c.close();}
	           end = (new java.util.Date()).getTime();
	           Log.i("GPALOG", "Time to query: " + (end - start) + " ms");
		}
		return null;
		
	}
	/**
	 * Method to get resultset collections from database
	 * this methodo works with select sintax
	 * @param tableName - Table name
	 * @param whereClause - Where clause
	 * @return ArrayList<TransferObject> select entities result from database
	 */
	public TransferObject selectMax(String tableName, String maxField){
	    long start, end;
	    start = (new java.util.Date()).getTime(); 
	    Cursor c = null;
		try {
	        c = getDb().query(
	        	tableName, 
	        	new String[]{"max( " + maxField + " )"},   
	        	null, 
	        	null, 
	        	null, 
	        	null,
	            null);  
	        c.moveToFirst();  
	        if(!c.isAfterLast()){  
	        	String value = c.getString( 0 );
	        	ArrayList<FieldTO> fieldsResult = new ArrayList<FieldTO>();
	        	fieldsResult.add(new FieldTO("max", value));
	        	TransferObject bean = new TransferObject(tableName, fieldsResult, TransferObject.READ_TYPE);
	            return bean; 
	        }      
		} catch (Exception e) {
			Log.e("GPALOG" , e.getMessage(),e); 
		}finally{
			   if(c!=null){c.close();}
	           end = (new java.util.Date()).getTime();
	           Log.i("GPALOG", "Time to query: " + (end - start) + " ms");
		}
		return null;
	}
	
	/**
	 * Method to transact object into database
	 * @param feature - Instantiate transfer object
	 * @return String success or fail
	 * @throws Exception
	 */
	private Cursor selectStatements(TransferObject feature)  throws Exception {
	    //ContentValues cv = new ContentValues();  
	    List<FieldTO> fields = feature.getFields();
	    String[] selectionArgs = new String[fields.size()];
	    for (int i=0; i<fields.size();i++) {
	    	FieldTO fieldTO = fields.get(i);
	    	String name = fieldTO.getName();
	    	Object value = fieldTO.getValue();
	    	selectionArgs[i] = String.valueOf(value);

		}
	   // return getDb().insert(getTableName(), null, cv);
		StatementFactory statement = new StatementFactory(feature);
        StringBuilder sqls = statement.createStatementSQL();
        Cursor c = getDb().rawQuery(sqls.toString(), selectionArgs);
	    return c;
	}
	private long transactStatements(TransferObject feature)  throws Exception {
		ContentValues cv = new ContentValues();  
		List<FieldTO> fields = feature.getFields();
	    for (int i=0; i<fields.size();i++) {
	    	FieldTO fieldTO = fields.get(i);
	    	String name = fieldTO.getName();
	    	Object value = fieldTO.getValue();
	    	if(value ==null){cv.putNull(name);}
	    	else{
	    		if(value instanceof String)		{cv.put(name, (String) value );}
		    	if(value instanceof Integer)	{cv.put(name, (Integer) value);}
		    	if(value instanceof Long)		{cv.put(name, (Long) value);} 
		    	if(value instanceof BigDecimal) {cv.put(name, (Long) value);}
		    	if(value instanceof Double) 	{cv.put(name, (Double) value);}
		    	if(value instanceof Float)		{cv.put(name, (Float) value);}
		    	if(value instanceof byte[])		{cv.put(name, (byte[]) value );} 
		    	if(value instanceof Short)		{cv.put(name, (Short) value);}
		    	if(value instanceof Date)		{cv.put(name, DateUtil.parseString((Date)value, DateUtil.DD_MM_YYYY_HH_MM_SS));}
		    	if(value instanceof Entity)		{cv.put(name, ((Entity)value).getId()) ;}
	    	}
	    }
	    return getDb().insert(getTableName(), null, cv);
	}
	/*
	private long transact2(TransferObject feature)  throws Exception {
		switch (feature.getTransactionType()) {
		case TransferObject.DELETE_TYPE:
			getDb().
		break;

		default:
			break;
		}
	}*/
	/**
	 * Method to transact objects into database
	 * @param features - Instantiate transfer objects
	 * @return String success or fail
	 * @throws Exception
	 */
	private long transact(ArrayList<TransferObject> features)  throws Exception {
	    long start, end;
	    start = (new java.util.Date()).getTime(); 
        if(features!=null && features.size()>0){
        	db.beginTransaction();
        	for (TransferObject transferObject : features) {
        		if(transactStatements(transferObject)<=0){
        			throw new NullPointerException("Insert error");
        		}
			}
        	db.setTransactionSuccessful();
        	db.endTransaction();
            end = (new java.util.Date()).getTime();
            Log.i("GPALOG","Time to query: " + (end - start) + " ms");
            return features.size();        
        }else{
            throw new NullPointerException("don't have features to transact");
        } 
    }/*
	private TransferObject fill(Cursor c){
		List<FieldTO> fields = new ArrayList<FieldTO>();
		String[] names = c.getColumnNames();
	    for (int i=0; i<names.length; i++ ) {
	    	String name = names[i];
	    	//Object value = fieldTO.getValue();
	    	if(!c.isNull(i)){
	    		
	    		if(value instanceof String)		{fields.add( new FieldTO(name, (String) value ));}
		    	if(value instanceof Integer)	{fields.add( new FieldTO(name, (Integer) value));}
		    	if(value instanceof Long)		{fields.add( new FieldTO(name, (Long) value));} 
		    	if(value instanceof BigDecimal) {fields.add( new FieldTO(name, (Long) value));}
		    	if(value instanceof Double) 	{fields.add( new FieldTO(name, (Double) value));}
		    	if(value instanceof Float)		{fields.add( new FieldTO(name, (Float) value));}
		    	if(value instanceof byte[])		{fields.add( new FieldTO(name, (byte[]) value ));} 
		    	if(value instanceof Short)		{fields.add( new FieldTO(name, (Short) value));}
	    	}
		}
		
	
	
		for(int i=0; i< c.getColumnCount(); i++){
			fields.add(new FieldTO(c.getColumnName(i),c.getBlob(i)));
		}
		
		
		TransferObject to = new TransferObject(
				getTableName(),
				fieldTos,
				TransferObject.READ_TYPE);
		return to;
	}*/
	
	
	private TransferObject fill(Cursor c) throws IllegalAccessException, InstantiationException{
		
		ArrayList<FieldTO> fieldsResult = new ArrayList<FieldTO>();
		
		Field[] fields = entity.getDeclaredFields();	
		
		//trunk entity to persistence
		for(int i=0; i<fields.length; i++){
			Field reflectionField = fields[i];
			if(reflectionField!=null){
				reflectionField.setAccessible(true);
				Annotation annoField = reflectionField.getAnnotation(GPAField.class);
				Annotation annoFieldPK = reflectionField.getAnnotation(GPAPrimaryKey.class);
				Annotation annoFieldBean = reflectionField.getAnnotation(GPAFieldBean.class);
				/* 
				 ainda falta validar a chave prim�ria do objeto
				 por enquanto so esta prevendo pk usando sequence no banco
				 objeto id sempre � gerado no banco por uma sequence
				*/
				if(annoFieldPK!=null && annoFieldPK instanceof GPAPrimaryKey){
					GPAPrimaryKey pk = (GPAPrimaryKey)annoFieldPK;
					String name = pk.name();
					int type = pk.type();
			    	//if(!c.isNull(i)){
			    		fieldsResult.add( getFieldAt(c, name, type, i) );
			    	//}
					continue;
				}
				if(annoField!=null && annoField instanceof GPAField){
					GPAField field = (GPAField)annoField;
					String name = field.name();
					int type = field.type();
					//if(!c.isNull(i)){
						fieldsResult.add( getFieldAt(c, name, type, i) );
					//}
					continue;
				}
				if(annoFieldBean!=null && annoFieldBean instanceof GPAFieldBean){
					GPAFieldBean field = (GPAFieldBean)annoFieldBean;
					String name = field.name();
					int type = field.type();
					//if(!c.isNull(i)){
						fieldsResult.add( getFieldAt(c, name, type, i) );
					//}
					continue;
				}
			}
		}
		TransferObject to = new TransferObject(
				getTableName(),
				fieldsResult,
				TransferObject.READ_TYPE);
		return to;
	}
	
	
	private TransferObject fillTO(Cursor c) throws IllegalAccessException, InstantiationException{
		ArrayList<FieldTO> fields = new ArrayList<FieldTO>();
		 while (c.moveToNext()) {
			 	for(int col=0;col<c.getColumnCount();col++){
			 		Object type = c.getClass();
			 		String name = c.getColumnName(col);
					try {
						
						//Object type = field.getType().newInstance();
			    		if(type instanceof String)
			    			fields.add( new FieldTO(name, c.getString(col)));
				    	if(type instanceof Integer)	
				    		fields.add( new FieldTO(name, c.getInt(c.getColumnIndex(name))));
				    	if(type instanceof Long)
				    		fields.add( new FieldTO(name, c.getLong(c.getColumnIndex(name))));
				    	if(type instanceof BigDecimal)
				    		fields.add( new FieldTO(name, c.getLong(c.getColumnIndex(name))));
				    	if(type instanceof Double)
				    		fields.add( new FieldTO(name, c.getDouble(c.getColumnIndex(name))));
				    	if(type instanceof Float)
				    		fields.add( new FieldTO(name, c.getFloat(c.getColumnIndex(name))));
				    	if(type instanceof byte[])
				    		fields.add( new FieldTO(name, c.getBlob(c.getColumnIndex(name))));
				    	if(type instanceof Short)
				    		fields.add( new FieldTO(name, c.getShort(c.getColumnIndex(name))));
					} catch (Exception e) {
						Log.e("GPALOG" , e.getMessage(),e); 
					}
			 	}
		    }
		TransferObject to = new TransferObject(
				getTableName(),
				fields,
				TransferObject.READ_TYPE);
		return to;
	}
	
	
	@Deprecated //TODO remove the newInstance to accept primitive types
	//substitute to annotation type, to get more flexibility data types 
	//use {@link getField}
	private FieldTO getFieldAt(Cursor c, String name, Field field, int columnIndex) 
			throws IllegalAccessException, InstantiationException{
			try {
				Object type = field.getType().newInstance();
	    		if(type instanceof String)
	    			return new FieldTO(name, c.getString(c.getColumnIndex(name)));
		    	if(type instanceof Integer)	
		    		return new FieldTO(name, c.getInt(c.getColumnIndex(name)));
		    	if(type instanceof Long)
		    		return new FieldTO(name, c.getLong(c.getColumnIndex(name)));
		    	if(type instanceof BigDecimal)
		    		return new FieldTO(name, c.getLong(c.getColumnIndex(name)));
		    	if(type instanceof Double)
		    		return new FieldTO(name, c.getDouble(c.getColumnIndex(name)));
		    	if(type instanceof Float)
		    		return new FieldTO(name, c.getFloat(c.getColumnIndex(name)));
		    	if(type instanceof byte[])
		    		return new FieldTO(name, c.getBlob(c.getColumnIndex(name)));
		    	if(type instanceof Short)
		    		return new FieldTO(name, c.getShort(c.getColumnIndex(name)));
			} catch (Exception e) {
				Log.e("GPALOG" , e.getMessage(),e); 
			}

	    	return null;
	}
	private FieldTO getFieldAt(Cursor c, String name, int type, int columnIndex) 
			throws IllegalAccessException, InstantiationException{
		try {
			switch (type) {
			case Entity.VARCHAR:
				return new FieldTO(name, c.getString(c.getColumnIndex(name)));
			case Entity.INTEGER:
				return new FieldTO(name, c.getInt(c.getColumnIndex(name)));
			case Entity.LONG:
				return new FieldTO(name, c.getLong(c.getColumnIndex(name)));
			case Entity.FLOAT:
				return new FieldTO(name, c.getFloat(c.getColumnIndex(name)));
			case Entity.DATE:
				return new FieldTO(name, DateUtil.parseDate( c.getString(c.getColumnIndex(name)) ));
			case Entity.BLOB:
				return new FieldTO(name, c.getBlob(c.getColumnIndex(name)));
			case Entity.DOUBLE:
				return new FieldTO(name, c.getDouble(c.getColumnIndex(name)));
			case Entity.BEAN:
				return new FieldTO(name, c.getInt(c.getColumnIndex(name)));
			}
		} catch (Exception e) {
			Log.e("GPALOG" , "fail to mapping " + name + " : " + e.getMessage(),e); 
		}
	    return null;
	}
}
