package br.com.slv.database.dao.entity;
import static org.junit.Assert.*;

import org.junit.Test;

import br.com.slv.database.dao.entity.Entity;
import br.com.slv.database.dao.model.TransferObject;



public class EntityTest {
	
	@Test
	public void insert(){
		Entity e = new Usuario("test","test","test", 1);
		String result = e.insert();
		assertNotNull(result);
		assertEquals("success", result);
	}
	@Test
	public void update(){
		Entity e = new Usuario("test","test","test", 1);
		String result = e.update();
		assertNotNull(result);
		assertEquals("success", result);
	}
	@Test
	public void delete(){
		Entity e = new Usuario("test","test","test", 1);
		String result = e.delete();
		assertNotNull(result);
		assertEquals("success", result);
	}
}
