package br.com.slv.database.dao.entity;



import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import br.com.slv.database.ConnectionFactory;
import br.com.slv.database.dao.DataTransferObject;
import br.com.slv.database.dao.model.TransferObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataTransferObjectTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void insertVeiculo() throws SQLException, ClassNotFoundException{
		Connection conn = ConnectionFactory.getConnection();
		DataTransferObject dto = new DataTransferObject();
		ArrayList<TransferObject> tos = MockTransferObjects.getVeiculoTOs();
		String result = dto.transact(tos);
		assertEquals(result, "success");
	}
	@Test
	public void insertMotorista() throws SQLException, ClassNotFoundException{
		Connection conn = ConnectionFactory.getConnection();
		DataTransferObject dto = new DataTransferObject();
		ArrayList<TransferObject> tos = MockTransferObjects.getMotoristaTOs();
		String result = dto.transact(tos);
		assertEquals(result, "success");
	}
	
	@Test
	public void insertFake2() throws SQLException, ClassNotFoundException{
		Connection conn = ConnectionFactory.getConnection();
		DataTransferObject dto = new DataTransferObject();
		TransferObject to = new TransferObject("", null, TransferObject.INSERT_TYPE);
		ArrayList<TransferObject> tos = new ArrayList<TransferObject>();
		tos.add(to);
		String result = dto.transact(tos);
		assertEquals(result, "fail");
	}
}
