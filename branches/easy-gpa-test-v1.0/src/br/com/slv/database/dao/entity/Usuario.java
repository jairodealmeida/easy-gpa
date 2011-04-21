package br.com.slv.database.dao.entity;
@EntityTable(name="tb_usuario")
public class Usuario extends Entity{
	@FieldTable(name="id")
	private int id;
	@FieldTable(name="nome_completo")
	private String nome_completo;
	@FieldTable(name="nome_usuario")
	private String nome_usuario;
	@FieldTable(name="senha")
	private String senha;
	@FieldTable(name="id_device")
	private int id_device;

	public Usuario(String nome_completo, String nome_usuario,
			String senha_character, int id_device) {
		super();
		this.nome_completo = nome_completo;
		this.nome_usuario = nome_usuario;
		this.senha = senha_character;
		this.id_device = id_device;
	}
	
	public Usuario(int id, String nome_completo, String nome_usuario,
			String senha_character, int id_device) {
		super();
		this.id = id;
		this.nome_completo = nome_completo;
		this.nome_usuario = nome_usuario;
		this.senha = senha_character;
		this.id_device = id_device;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome_completo() {
		return nome_completo;
	}
	public void setNome_completo(String nome_completo) {
		this.nome_completo = nome_completo;
	}
	public String getNome_usuario() {
		return nome_usuario;
	}
	public void setNome_usuario(String nome_usuario) {
		this.nome_usuario = nome_usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}

}
