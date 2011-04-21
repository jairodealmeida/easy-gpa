package br.com.slv.database.dao.entity;
import static org.junit.Assert.*;

import org.junit.Test;

import br.com.slv.database.dao.entity.Entity;
import br.com.slv.database.dao.model.TransferObject;



public class EntityTest {
	
	private Entity e = new Usuario("test","test","test", 1);
	
	@Test
	public void ToTransferObject(){
		String result = e.insert();
		assertNotNull(result);
		assertEquals("success", result);
	}
}
