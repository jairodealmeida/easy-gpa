/**
 * 
 */
package br.com.jro.termos_manager.dao.bean;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jairo de Almeida - jairo.almeida@proxima.agr.br
 * @size 13/04/2012
 * termos_manager 	
 * INSERT INTO INTERFACE (ID_INTERFACE,CD_INTERFACE,DE_INTERFACE, ROWVERSION, LAST_UPDATE, CHANGED_BY) 
 * values (437,'CONSULTAEVOLUCAOFITOSSANITARIAACTION','Consulta de Evolução Fitossanitária', 1, GETDATE(), 'PRX');
 */
public class Interface  extends AbstractModelObject {
	private Long id;
	private String cdInterface;
	private String deInterface;
	private Integer rowVersion;
	private Date lastUpdate;
	private String changedBy;
	private List<Termo> termos = new ArrayList<Termo>();
	
	public Interface(String cdInterface){
		this.cdInterface = cdInterface;
	}
	
	public void addTermo(Termo termo){
		termos.add(termo);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCdInterface() {
		return cdInterface;
	}
	public void setCdInterface(String cdInterface) {
		this.cdInterface = cdInterface;
	}
	public String getDeInterface() {
		return deInterface;
	}
	public void setDeInterface(String deInterface) {
		this.deInterface = deInterface;
	}
	public Integer getRowVersion() {
		return rowVersion;
	}
	public void setRowVersion(Integer rowVersion) {
		this.rowVersion = rowVersion;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
	public List<Termo> getTermos() {
		return termos;
	}
	public void setTermos(List<Termo> termos) {
		this.termos = termos;
	}
	
}
