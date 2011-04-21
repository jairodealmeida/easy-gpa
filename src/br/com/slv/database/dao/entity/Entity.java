package br.com.slv.database.dao.entity;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;

import br.com.slv.database.ConnectionFactory;
import br.com.slv.database.dao.DataTransferObject;
import br.com.slv.database.dao.model.FieldTO;
import br.com.slv.database.dao.model.TransferObject;

public abstract class Entity {
	
	private ArrayList<FieldTO> fieldTos;
	
	private String getTableName(){
		String className = this.getClass().getSimpleName();
		String tableName = "tb_" + className.toLowerCase();
		return tableName;
	}
	
	public String insert(){
		try {
			prepareFields();
			String tableName = getTableName();
			TransferObject to = new TransferObject(
						tableName, fieldTos, TransferObject.INSERT_TYPE);
			Connection conn = ConnectionFactory.getConnection();
			DataTransferObject dto = new DataTransferObject(conn);
			ArrayList<TransferObject> items = new ArrayList<TransferObject>();
			items.add(to);
			return dto.transact(items);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}
	
	public String update(){
		try {
			prepareFields();
			//TODO
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "fail";
	}
	
	private void prepareFields() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		fieldTos = new ArrayList<FieldTO>();
		Field[] fields = this.getClass().getDeclaredFields();	
		//trunk entity to persistence
		for(int i=0; i<fields.length; i++){
			Field field = fields[i];
			if(field!=null){
				String name = fields[i].getName();
				if(name.equalsIgnoreCase("id")){
					//TODO ainda falta validar a chave primária do objeto
					//por enquanto so esta prevendo pk usando sequence no banco
					//objeto id sempre é gerado no banco por uma sequence
				}else{
					fields[i].setAccessible(true);
					Object value = fields[i].get(this);
					fieldTos.add(new FieldTO(name, value));
				}
			}
		}
	}
}
