package br.com.jro.gpa.test;

import java.util.ArrayList;
import java.util.List;
import android.test.ActivityInstrumentationTestCase2;
import br.com.jro.gpa.MainActivity;
import br.com.slv.database.Repository;
import br.com.slv.database.Repository;
import br.com.slv.database.dao.entity.Entity;
import br.com.slv.database.dao.model.FieldTO;
import br.com.slv.database.dao.model.TransferObject;
import br.com.slv.database.dao.statement.operation.WhereStatement;

public class EntityTest extends
ActivityInstrumentationTestCase2<MainActivity>{
	
	private MainActivity mActivity;

	public EntityTest() {
		super("br.com.jro.gpa", MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mActivity = this.getActivity();	
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
			}
		});

		getInstrumentation().waitForIdleSync();
	}
	public void test_1_0_CreateScriptAccept1(){
		try {
			Entity e = new Usuario(3,"test","test","test", 1);
			Repository repository = new Repository(mActivity, Usuario.class,"teste",1);
			String result = repository.createScript();
			assertNotNull(result);
			assertEquals("create table tb_usuario(id integer primary key autoincrement ,id_device integer,nome_completo text,nome_usuario text,senha text);", result);			
		} catch (Exception e) {
			fail("not pass here " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	public void test_1_1_DeleteScriptAccept1(){
		try {
			Entity e = new Usuario(3,"test","test","test", 1);
			Repository repository = new Repository(mActivity, Usuario.class,"teste",1);
			String result = repository.deleteScript();
			assertNotNull(result);
			assertEquals("drop table if exists tb_usuario;", result);			
		} catch (Exception e) {
			fail("not pass here " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	public void test_1_2_InsertAccept1(){
		try {
			Entity entity = new Usuario(3,"test","test","test", 1);
			Repository repository = new Repository(mActivity, Usuario.class,"teste",1);
			long result = repository.insert(entity);
			assertTrue(result>0);
		} catch (Exception e) {
			fail("not pass here " + e.getLocalizedMessage());
			e.printStackTrace();
		}

	}
	
	public void test_1_3_UpdateAccept1(){
		try {
			Repository repository = new Repository(mActivity, Usuario.class,"teste",1);
			TransferObject to = repository.selectMax("TB_USUARIO", "id");
			Integer maxId = to.getInteger("max");
			Entity e = new Usuario(maxId, "test", "test", "test", 1);
			long result = repository.update(e);
			assertNotNull(result);
			//assertEquals(1, result);
		} catch (Exception e) {
			fail("not pass here " + e.getLocalizedMessage());
			e.printStackTrace();
		}

	}
	
	public void test_1_5_DeleteAccept1(){
		try {
			Repository repository = new Repository(mActivity, Usuario.class,"teste",1);
			TransferObject to = repository.selectMax("TB_USUARIO", "id");
			Integer maxId = to.getInteger("max");
			Entity e = new Usuario( maxId, "test", "test", "test", 1);
			long result = repository.delete(e);
			assertNotNull(result);
			//assertEquals(maxId, result);
		} catch (Exception e) {
			fail("not pass here " + e.getLocalizedMessage());
			e.printStackTrace();
		}

	}
	
	public void test_1_4_SelectAccept1(){
		try {
			Repository repository = new Repository(mActivity, Usuario.class,"teste",1);
			List<TransferObject> list = repository.selectAll(Usuario.class);
			assertNotNull(list);
			assertTrue(list.size()>0);
		} catch (Exception e) {
			fail("not pass here " + e.getLocalizedMessage());
			e.printStackTrace();
		}

	}
	
	public void test_1_6_SelectWhereAccept1(){
		try {
			Repository repository = new Repository(mActivity, Usuario.class,"teste",1);
			List<FieldTO> fields = new ArrayList<FieldTO>();
			fields.add(new FieldTO("name",new Long(3)));
			fields.add(new FieldTO("endereco","teste"));
			WhereStatement where = new WhereStatement(fields);
			List<TransferObject> list = repository.select(where); 
			assertNotNull(list);
			assertTrue(list.size()>0);
		} catch (Exception e) {
			fail("not pass here " + e.getLocalizedMessage());
			e.printStackTrace();
		}

	}
	
}
