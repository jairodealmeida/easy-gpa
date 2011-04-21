package br.com.slv.database.dao.entity;


import java.util.ArrayList;

import br.com.slv.database.dao.model.FieldTO;
import br.com.slv.database.dao.model.TransferObject;

public class MockTransferObjects {
	public static ArrayList<TransferObject> getUsuarioTOs(){
		ArrayList<TransferObject> result = new ArrayList<TransferObject>();
		ArrayList<FieldTO> fields = new ArrayList<FieldTO>();
		fields.add(new FieldTO( "nome", "caminhãozinho1" ));
		fields.add(new FieldTO( "marca", "mercedes" ));/*
		"Jairo de Almeida";"jairodealmeida";"senha";4
		"Nezinha Amor";"nezinha";"amor";4
		"Thiago";"thiago";"thiago";4
		"Carolina";"carolina";"carolina";4
		"Maria Maura de Almeida";"maura";"maura";4
		"José de Ameida Filho";"jose";"jose";4
		"Marcos";"marcos";"senha";4
		"jjjj";"jjjj";"123";4
		"jairo";"jairo";"jairo";4*/

		TransferObject to = new TransferObject("tb_veiculo", fields, TransferObject.INSERT_TYPE);
		
		result.add(to);
		return result;
	}
	/**
	 * CREATE TABLE tb_veiculo(
			id serial NOT NULL,
			nome character varying,
			marca character varying,
			CONSTRAINT tb_veiculo_pkey PRIMARY KEY (id)
		)
	 */
	public static ArrayList<TransferObject> getVeiculoTOs(){
		ArrayList<FieldTO> fields = new ArrayList<FieldTO>();
		fields.add(new FieldTO( "nome", "caminhãozinho1" ));
		fields.add(new FieldTO( "marca", "mercedes" ));
		TransferObject to = new TransferObject("tb_veiculo", fields, TransferObject.INSERT_TYPE);
		ArrayList<TransferObject> result = new ArrayList<TransferObject>();
		result.add(to);
		return result;
	}
	/**
	 * CREATE TABLE tb_motorista
		(
		  id serial NOT NULL,
		  nome character varying,
		  idade integer,
		  telefone character varying,
		  CONSTRAINT tb_motorista_pkey PRIMARY KEY (id)
		)
	 */
	public static ArrayList<TransferObject> getMotoristaTOs(){
		ArrayList<FieldTO> fields = new ArrayList<FieldTO>();
		fields.add(new FieldTO( "nome", "Tião Macalé" ));
		fields.add(new FieldTO( "idade", 30 ));
		fields.add(new FieldTO( "telefone", "(011)33223232" ));
		TransferObject to = new TransferObject("tb_motorista",fields, TransferObject.INSERT_TYPE);
		ArrayList<TransferObject> result = new ArrayList<TransferObject>();
		result.add(to);
		return result;
	}
}
