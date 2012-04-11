/**
 * 
 */
package br.com.jro.termos_manager.dao.bean;

/**
 * @author Jairo de Almeida - jairo.almeida@proxima.agr.br
 * @size 11/04/2012
 * termos_manager 	
 */
public class Termos {
	private Long id;
	private String dePadrao;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDePadrao() {
		return dePadrao;
	}
	public void setDePadrao(String dePadrao) {
		this.dePadrao = dePadrao;
	}
}
