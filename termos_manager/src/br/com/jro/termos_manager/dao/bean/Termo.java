/**
 * 
 */
package br.com.jro.termos_manager.dao.bean;

import java.sql.Date;

/**
 * @author Jairo de Almeida - jairo.almeida@proxima.agr.br
 * @size 13/04/2012
 * termos_manager 	
 * INSERT INTO TERMO (ID_TERMO, CD_TERMO, DE_PADRAO, ROWVERSION, LAST_UPDATE, CHANGED_BY) 
 * VALUES (7968, '0001.0008.0014.0000', 'Falha geral no cadastro de Inspeção Fitossanitária por Pontos. Contate o Administrador.', 1, GETDATE(), 'PRX');
 */
public class Termo  extends AbstractModelObject{
	private Long idTermo;
	private String cdTermo;
	private String deTermo;
	
	
	
	/**
	 * @param idTermo
	 * @param cdTermo
	 * @param deTermo
	 * @param rowVersion
	 * @param lastUpdate
	 * @param changedBy
	 */
	public Termo(Long idTermo, String cdTermo, String deTermo,
			Integer rowVersion, Date lastUpdate, String changedBy) {
		super();
		this.idTermo = idTermo;
		this.cdTermo = cdTermo;
		this.deTermo = deTermo;
		this.rowVersion = rowVersion;
		this.lastUpdate = lastUpdate;
		this.changedBy = changedBy;
	}
	public Long getIdTermo() {
		return idTermo;
	}
	public void setIdTermo(Long idTermo) {
		this.idTermo = idTermo;
	}
	public String getCdTermo() {
		return cdTermo;
	}
	public void setCdTermo(String cdTermo) {
		this.cdTermo = cdTermo;
	}
	public String getDeTermo() {
		return deTermo;
	}
	public void setDeTermo(String deTermo) {
		this.deTermo = deTermo;
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
	private Integer rowVersion;
	private Date lastUpdate;
	private String changedBy;
}
