package br.com.slv.database.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;

import br.com.slv.database.dao.model.FieldTO;

public abstract class Entity implements Serializable{
	private String tableName;
	private ArrayList<FieldTO> primaryKeyTos;
	private ArrayList<FieldTO> fieldTos;
	
	private String getTableName(){
		return tableName;
	}
	private void setPrimaryKeyTos(ArrayList<FieldTO> primaryKeyTos) {
		this.primaryKeyTos = primaryKeyTos;
	}
	private ArrayList<FieldTO> getPrimaryKeyTos() {
		return primaryKeyTos;
	}
	private void setFieldTos(ArrayList<FieldTO> fieldTos) {
		this.fieldTos = fieldTos;
	}
	private ArrayList<FieldTO> getFieldTos() {
		return fieldTos;
	}
}
