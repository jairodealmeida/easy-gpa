package br.com.jro.gpa.test;

import android.test.ActivityInstrumentationTestCase2;
import br.com.jro.gpa.MainActivity;
import br.com.slv.database.Repositorio;
import br.com.slv.database.dao.entity.Entity;

public class EntityTest extends
ActivityInstrumentationTestCase2<MainActivity>{
	
	private MainActivity mActivity;
	//DataTransferDelegate delegate = new DataTransferDelegate();
	
/*
	public void testInsert(){
		Entity e = new Usuario(3,"test","test","test", 1);
		String result = delegate.insert(e);
		assertNotNull(result);
		assertEquals("success", result);
	}
	

	public void testupdate(){
		TransferObject to = delegate.selectMax("TB_USUARIO", "id");
		Integer maxId = to.getInteger("max");
		Entity e = new Usuario(maxId, "test", "test", "test", 1);
		String result = delegate.update(e);
		assertNotNull(result);
		assertEquals("success", result);
	}
	

	public void testdelete(){
		TransferObject to = delegate.selectMax("TB_USUARIO", "id");
		Integer maxId = to.getInteger("max");
		Entity e = new Usuario( maxId, "test", "test", "test", 1);
		String result = delegate.delete(e);
		assertNotNull(result);
		assertEquals("success", result);
	}

	public void testselect(){
		ArrayList<TransferObject> list = delegate.select("TB_USUARIO", "nome_completo = 'test'");
		assertNotNull(list);
	}*/
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
			Repositorio repository = new Repositorio(mActivity, Usuario.class,"teste",1);
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
			Repositorio repository = new Repositorio(mActivity, Usuario.class,"teste",1);
			String result = repository.deleteScript();
			assertNotNull(result);
			assertEquals("drop table if exists tb_usuario;", result);			
		} catch (Exception e) {
			fail("not pass here " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	public void testInsert(){
		try {
			Entity e = new Usuario(3,"test","test","test", 1);
			Repositorio repository = new Repositorio(mActivity, Usuario.class,"teste",1);
			//String result = delegate.insert(e);
			//assertNotNull(result);
			//assertEquals("success", result);
		} catch (Exception e) {
			fail("not pass here " + e.getLocalizedMessage());
			e.printStackTrace();
		}

	}
	
}
