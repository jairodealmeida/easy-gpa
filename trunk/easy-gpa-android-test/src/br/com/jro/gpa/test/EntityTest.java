package br.com.jro.gpa.test;

import java.util.List;
import android.test.ActivityInstrumentationTestCase2;
import br.com.jro.gpa.MainActivity;
import br.com.slv.database.Repository;
import br.com.slv.database.Repository;
import br.com.slv.database.dao.entity.Entity;
import br.com.slv.database.dao.model.TransferObject;

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
	public void testCreateScriptAccept1(){
		try {
			Entity e = new Usuario(3,"test","test","test", 1);
			Repository repository = new Repository(mActivity, Usuario.class,"teste",1);
			String result = repository.createScript();
			assertNotNull(result);
			assertEquals("create table tb_usuario" +
					"(senha text,nome_usuario text," +
					"nome_completo text," +
					"id_device integer," +
					"id integer primary key autoincrement );", result);			
		} catch (Exception e) {
			fail("not pass here " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	public void testDeleteScriptAccept1(){
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
	public void testInsertAccept1(){
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
	
	public void testUpdateAccept1(){
		try {
			Repository repository = new Repository(mActivity, Usuario.class,"teste",1);
			TransferObject to = repository.selectMax("TB_USUARIO", "id");
			Integer maxId = to.getInteger("max");
			Entity e = new Usuario(maxId, "test", "test", "test", 1);
			long result = repository.update(e);
			assertNotNull(result);
			assertEquals(1, result);
		} catch (Exception e) {
			fail("not pass here " + e.getLocalizedMessage());
			e.printStackTrace();
		}

	}
	
	public void testDeleteAccept1(){
		try {
			Repository repository = new Repository(mActivity, Usuario.class,"teste",1);
			TransferObject to = repository.selectMax("TB_USUARIO", "id");
			Integer maxId = to.getInteger("max");
			Entity e = new Usuario( maxId, "test", "test", "test", 1);
			long result = repository.delete(e);
			assertNotNull(result);
			assertEquals(1, result);
		} catch (Exception e) {
			fail("not pass here " + e.getLocalizedMessage());
			e.printStackTrace();
		}

	}
	
	public void testSelectAccept1(){
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
	
	
	
}
