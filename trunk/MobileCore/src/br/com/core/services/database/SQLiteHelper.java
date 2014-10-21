package br.com.core.services.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {  
    private String scriptCreate;  
    private String scriptDelete;  
  
    public SQLiteHelper(Context ctx, String nomeBd,   
        int versaoBanco, String scriptCreate,   
        String scriptDelete) {  
        super(ctx, nomeBd, null, versaoBanco);  
        this.scriptCreate = scriptCreate;  
        this.scriptDelete = scriptDelete;  
    }  
  
    public void onCreate(SQLiteDatabase db) {  
    	//Estamos fazendo a cria��o do banco por fora do sistema
        //db.execSQL(scriptCreate);  
    }  
  
    public void onUpgrade(SQLiteDatabase db,   
        int oldVersion, int newVersion) {
    	//Estamos fazendo a cria��o do banco por fora do sistema
        //db.execSQL(scriptDelete);  
        //onCreate(db);  
    } 
}  