package org.generation.hotel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name="tb_usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cd_usuario")
	private long codigo;
	
	@Column(name="nm_usuario", nullable=false, length=80)
	@Size(min = 2, max = 80)
	@NotNull
	private String nome;

	@Column(name="nm_email", nullable=false, length=80)
	@Size(min = 7, max = 80)
	@NotNull
	@Email(message = "Email inválido!")
	private String email;

	@Column(name="nm_password", nullable=false, length=256)
	@Size(min = 5, max = 256)
	@NotNull
	private String password;
	
	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
