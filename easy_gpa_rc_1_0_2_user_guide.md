# Library to work with CRUD entities. #

CRUD(Create, read, update and delete)

**IRC** #easy-gpa
**Group** http://groups.google.com/group/easy-gpa

**User guide of easy-gpa library**

**How to use?**
First of all, set the **easy-gpa.jar**,the liked database **jdbc**, and the **log4j-1.2.14.jar** library in your classpath and configurate the database instance in your project, in source folder in your project you will create a **database-config.properties** file
and setting the configuration to database instance.

**example to postgresql database**
```
## define a type of database connection
database.type = postgres

## postgresql connection
database.postgres.url = jdbc:postgresql://localhost:5432/test_database
database.postgres.driver = org.postgresql.Driver
database.postgres.user = test
database.postgres.pass = test
```

**example to oracle connection**
```
## define a type of database connection
database.type = oracle

## oracle connection
database.oracle.url = jdbc:oracle:thin:@localhost:1521:orcl
database.oracle.driver = oracle.jdbc.driver.OracleDriver
database.oracle.user = test
database.oracle.pass = test
```

In library **easy-gpa.jar** exist a class **Entity**, in your project get the class with sugetive name for example
If the table in your database named **tb\_usuario** create a class with name **Usuario** and get in up of class name this annotation **@GPAEntity(name="tb\_usuario")**
, and next step extends the **Entity** class and to columns of table for example, if your name are **@GPAPrimaryKey(name="id", ignore=true)** reference by atribute id into database and get **"id"** field in your classe with your getters and setters, for example, if isn't primary key get the annotation **@GPAField(name="nome\_completo")** to create a simple attribute database column

Can you make the statement create in your database configured, for example.

```
CREATE TABLE tb_usuario
(
  id serial NOT NULL,
  nome_completo character varying(10485760) NOT NULL,
  nome_usuario character varying(10485760) NOT NULL,
  senha character varying(10485760) NOT NULL
)
```

After this you can to make a class with the table caracteristcs, and generate a bean with Entity how super class and use your methods, for example the class Usuario.

```
package br.com.slv.database.dao.entity;

import br.com.slv.database.dao.entity.annotation.GPAEntity;
import br.com.slv.database.dao.entity.annotation.GPAField;
import br.com.slv.database.dao.entity.annotation.GPAPrimaryKey;

@GPAEntity(name="tb_usuario")
public class Usuario extends Entity{
	@GPAPrimaryKey(name="id", ignore=true)
	private int id;
	@GPAField(name="nome_completo")
	private String nomeCompleto;
	@GPAField(name="nome_usuario")
	private String nomeUsuario;
	@GPAField(name="senha")
	private String senha;
	@GPAField(name="id_device")
	private int idDevice;
	/**
	 * Used by insert without id, database are responsible 
	 * to generate a sequential id to primary key 
	 * @param nomeCompleto
	 * @param nomeUsuario
	 * @param senhaCharacter
	 * @param idDevice
	 */
	public Usuario(String nomeCompleto, String nomeUsuario,
			String senhaCharacter, int idDevice) {
		super();
		this.nomeCompleto = nomeCompleto;
		this.nomeUsuario = nomeUsuario;
		this.senha = senhaCharacter;
		this.idDevice = idDevice;
	}
	/**
	 * in this case the entity are instantied with the primary key information
	 * @param id
	 * @param nomeCompleto
	 * @param nomeUsuario
	 * @param senhaCharacter
	 * @param idDevice
	 */
	public Usuario(int id, String nomeCompleto, String nomeUsuario,
			String senhaCharacter, int idDevice) {
		super();
		this.id = id;
		this.nomeCompleto = nomeCompleto;
		this.nomeUsuario = nomeUsuario;
		this.senha = senhaCharacter;
		this.idDevice = idDevice;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getIdDevice() {
		return idDevice;
	}
	public void setIdDevice(int idDevice) {
		this.idDevice = idDevice;
	}

}

```

With this entity created you can to work with this test source

```

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import br.com.slv.database.dao.delegate.DataTransferDelegate;
import br.com.slv.database.dao.model.TransferObject;



public class EntityTest {
	DataTransferDelegate delegate = new DataTransferDelegate();

	@Test
	public void insert(){
		Entity e = new Usuario(3,"test","test","test", 1);
		String result = delegate.insert(e);
		assertNotNull(result);
		assertEquals("success", result);
	}
	
	@Test
	public void update(){
		TransferObject to = delegate.selectMax("TB_USUARIO", "id");
		Integer maxId = to.getInteger("max");
		Entity e = new Usuario(maxId, "test", "test", "test", 1);
		String result = delegate.update(e);
		assertNotNull(result);
		assertEquals("success", result);
	}
	
	@Test
	public void delete(){
		TransferObject to = delegate.selectMax("TB_USUARIO", "id");
		Integer maxId = to.getInteger("max");
		Entity e = new Usuario( maxId, "test", "test", "test", 1);
		String result = delegate.delete(e);
		assertNotNull(result);
		assertEquals("success", result);
	}
	@Test
	public void select(){
		ArrayList<TransferObject> list = delegate.select("TB_USUARIO", "nome_completo = 'test'");
		assertNotNull(list);
	}
	

	
}
.
```
Make this you will create a Entity to transact informations about the database configured
operation insert update and delete functionalities are opened to you use in your aplication, the easy form to manipulate transacs statements without hard implementation of anamical DAO and other hard implementations.

**Author: Jairo de Almeida**
**Since: v2 - 02/08/2011**