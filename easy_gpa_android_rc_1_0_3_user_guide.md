# Easy-GPA-Android #
# CRUD API(Create, read, update and delete) #
# Google Persistence API #
**IRC** #easy-gpa-android
**Group** http://groups.google.com/group/easy-gpa-android

**Easy-gpa-android user guide** <br>
<b>How to use?</b> <br>
First of all, <br>
Create a new android project in eclipse and put on classpath the easy-gpa-android.jar <br>
Set the easy-gpa-android.jar to work with sqlite database’s. <br>
To set the database instance to your project using easy-gpa-android.<br>
In easy-gpa-android.jar library has a class named Entity <br>
which you can make your regular beans extends in order to use easy-gpa functionalities.<br>
Create a regular class with a suggestive name representing a database table, <br>
make it extends Entity class (from easy-gpa.jar) and annotate it with <code>@GPAEntity(name="table_name")</code> annonation. <br>
After that, you have to annotate your class attributes’ getters and setters according to your database table’s fields. <br>
The first thing to consider is the primary key. <br>
Make sure to annotate the table’s id with <code>@GPAPrimaryKey(name="id_name", ignore=true)</code> annotation. <br>
The other regular fields must be annotated with <code>@GPAField(name="field_name")</code> annotation.<br>
Example: The following example was made using this sqlite script: <br>
<pre><code>CREATE TABLE [tb_usuario] (<br>
  [id] integer primary key autoincrement NOT NULL, <br>
  [nome_completo] VARCHAR NOT NULL, <br>
  [nome_usuario] VARCHAR NOT NULL, <br>
  [senha] VARCHAR NOT NULL);<br>
</code></pre>
After this, a class with the table characterizes was created, extending Entity class to use it’s methods.<br>
The following example shows how the class Usuario (user) implements the easy-gpa-android functionalities.:<br>

<pre><code><br>
import br.com.slv.database.dao.entity.Entity;<br>
import br.com.slv.database.dao.entity.annotation.GPAEntity;<br>
import br.com.slv.database.dao.entity.annotation.GPAField;<br>
import br.com.slv.database.dao.entity.annotation.GPAPrimaryKey;<br>
<br>
@GPAEntity(name="tb_usuario")<br>
public class Usuario extends Entity{<br>
	@GPAPrimaryKey(name="id", ignore=true)<br>
	private int id;<br>
	@GPAField(name="nome_completo")<br>
	private String nomeCompleto;<br>
	@GPAField(name="nome_usuario")<br>
	private String nomeUsuario;<br>
	@GPAField(name="senha")<br>
	private String senha;<br>
<br>
	/**<br>
	 * Used by insert without id, database are responsible <br>
	 * to generate a sequential id to primary key <br>
	 * @param nomeCompleto<br>
	 * @param nomeUsuario<br>
	 * @param senhaCharacter<br>
	 * @param idDevice<br>
	 */<br>
	public Usuario(String nomeCompleto, String nomeUsuario,<br>
			String senhaCharacter) {<br>
		super();<br>
		this.nomeCompleto = nomeCompleto;<br>
		this.nomeUsuario = nomeUsuario;<br>
		this.senha = senhaCharacter;<br>
	}<br>
	/**<br>
	 * in this case the entity are instantied with the primary key information<br>
	 * @param id<br>
	 * @param nomeCompleto<br>
	 * @param nomeUsuario<br>
	 * @param senhaCharacter<br>
	 * @param idDevice<br>
	 */<br>
	public Usuario(int id, String nomeCompleto, String nomeUsuario,<br>
			String senhaCharacter) {<br>
		super();<br>
		this.id = id;<br>
		this.nomeCompleto = nomeCompleto;<br>
		this.nomeUsuario = nomeUsuario;<br>
		this.senha = senhaCharacter;<br>
	}<br>
	public int getId() {<br>
		return id;<br>
	}<br>
	public void setId(int id) {<br>
		this.id = id;<br>
	}<br>
	public String getNomeCompleto() {<br>
		return nomeCompleto;<br>
	}<br>
	public void setNomeCompleto(String nomeCompleto) {<br>
		this.nomeCompleto = nomeCompleto;<br>
	}<br>
	public String getNomeUsuario() {<br>
		return nomeUsuario;<br>
	}<br>
	public void setNomeUsuario(String nomeUsuario) {<br>
		this.nomeUsuario = nomeUsuario;<br>
	}<br>
	public String getSenha() {<br>
		return senha;<br>
	}<br>
	public void setSenha(String senha) {<br>
		this.senha = senha;<br>
	}<br>
}<br>
<br>
</code></pre>
With this entity created, you can try this android test code<br>
<pre><code>import java.util.List;<br>
import android.test.ActivityInstrumentationTestCase2;<br>
import br.com.jro.gpa.MainActivity;<br>
import br.com.slv.database.Repositorio;<br>
import br.com.slv.database.dao.entity.Entity;<br>
import br.com.slv.database.dao.model.TransferObject;<br>
<br>
public class EntityTest extends<br>
ActivityInstrumentationTestCase2&lt;MainActivity&gt;{<br>
	<br>
	private MainActivity mActivity;<br>
<br>
	public EntityTest() {<br>
		super("br.com.jro.gpa", MainActivity.class);<br>
		// TODO Auto-generated constructor stub<br>
	}<br>
	@Override<br>
	protected void setUp() throws Exception {<br>
		super.setUp();<br>
		mActivity = this.getActivity();	<br>
		mActivity.runOnUiThread(new Runnable() {<br>
			@Override<br>
			public void run() {<br>
			}<br>
		});<br>
<br>
		getInstrumentation().waitForIdleSync();<br>
	}<br>
	public void testCreateScriptAccept1(){<br>
		try {<br>
			Entity e = new Usuario(3,"test","test","test", 1);<br>
			Repositorio repository = new Repositorio(mActivity, Usuario.class,"teste",1);<br>
			String result = repository.createScript();<br>
			assertNotNull(result);<br>
			assertEquals("create table tb_usuario" +<br>
					"(senha text,nome_usuario text," +<br>
					"nome_completo text," +<br>
					"id_device integer," +<br>
					"id integer primary key autoincrement );", result);			<br>
		} catch (Exception e) {<br>
			fail("not pass here " + e.getLocalizedMessage());<br>
			e.printStackTrace();<br>
		}<br>
	}<br>
	public void testDeleteScriptAccept1(){<br>
		try {<br>
			Entity e = new Usuario(3,"test","test","test", 1);<br>
			Repositorio repository = new Repositorio(mActivity, Usuario.class,"teste",1);<br>
			String result = repository.deleteScript();<br>
			assertNotNull(result);<br>
			assertEquals("drop table if exists tb_usuario;", result);			<br>
		} catch (Exception e) {<br>
			fail("not pass here " + e.getLocalizedMessage());<br>
			e.printStackTrace();<br>
		}<br>
	}<br>
	public void testInsertAccept1(){<br>
		try {<br>
			Entity entity = new Usuario(3,"test","test","test", 1);<br>
			Repositorio repository = new Repositorio(mActivity, Usuario.class,"teste",1);<br>
			long result = repository.insert(entity);<br>
			assertTrue(result&gt;0);<br>
		} catch (Exception e) {<br>
			fail("not pass here " + e.getLocalizedMessage());<br>
			e.printStackTrace();<br>
		}<br>
<br>
	}<br>
	<br>
	public void testUpdateAccept1(){<br>
		try {<br>
			Repositorio repository = new Repositorio(mActivity, Usuario.class,"teste",1);<br>
			TransferObject to = repository.selectMax("TB_USUARIO", "id");<br>
			Integer maxId = to.getInteger("max");<br>
			Entity e = new Usuario(maxId, "test", "test", "test", 1);<br>
			long result = repository.update(e);<br>
			assertNotNull(result);<br>
			assertEquals(1, result);<br>
		} catch (Exception e) {<br>
			fail("not pass here " + e.getLocalizedMessage());<br>
			e.printStackTrace();<br>
		}<br>
<br>
	}<br>
	<br>
	public void testDeleteAccept1(){<br>
		try {<br>
			Repositorio repository = new Repositorio(mActivity, Usuario.class,"teste",1);<br>
			TransferObject to = repository.selectMax("TB_USUARIO", "id");<br>
			Integer maxId = to.getInteger("max");<br>
			Entity e = new Usuario( maxId, "test", "test", "test", 1);<br>
			long result = repository.delete(e);<br>
			assertNotNull(result);<br>
			assertEquals(1, result);<br>
		} catch (Exception e) {<br>
			fail("not pass here " + e.getLocalizedMessage());<br>
			e.printStackTrace();<br>
		}<br>
<br>
	}<br>
	<br>
	public void testSelectAccept1(){<br>
		try {<br>
			Repositorio repository = new Repositorio(mActivity, Usuario.class,"teste",1);<br>
			List&lt;TransferObject&gt; list = repository.select("TB_USUARIO", "nome_completo = 'test'");<br>
			assertNotNull(list);<br>
			assertTrue(list.size()&gt;0);<br>
		} catch (Exception e) {<br>
			fail("not pass here " + e.getLocalizedMessage());<br>
			e.printStackTrace();<br>
		}<br>
<br>
	}	<br>
}<br>
</code></pre>
By doing these tests, you will be creating an Entity instance to transact information from/to the configured database, <br>
using a regular DBMS operations (insert update and delete).<br>
This is an easy, simple and elegant form to manipulate/transact statements. No more hard DAO implementations. <br>

<b>Authors:</b> <br>
<b>Jairo de Almeida Since: v2 - 02/08/2011</b> <br>
<b>Willian da Costa Chimura : v3 - 06/09/2011</b> <br>
<b>Jairo de Almeida Since: v4 - 17/11/2011</b> <br>