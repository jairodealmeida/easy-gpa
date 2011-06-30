package br.com.slv.database.dao.entity;
import static org.junit.Assert.*;

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
		Entity e = new Usuario(3, "test", "test", "test", 1);
		String result = delegate.update(e);
		assertNotNull(result);
		assertEquals("success", result);
	}
	
	@Test
	public void delete(){
		Entity e = new Usuario(3, "test", "test", "test", 1);
		String result = delegate.delete(e);
		assertNotNull(result);
		assertEquals("success", result);
	}
	@Test
	public void select(){
		//TODO delegate.
	}
	

	
}
