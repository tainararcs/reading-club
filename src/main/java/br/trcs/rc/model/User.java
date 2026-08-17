package br.trcs.rc.model;

import java.time.LocalDate;

import br.trcs.rc.utils.Consts;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade que representa um usuário do sistema. Armazena informações pessoais, credenciais de acesso e dados
 * relacionados à autenticação e comunicação (e-mail). O identificador primário da entidade é o CPF do usuário.
 */
@Entity
@Table(name = Consts.USER_TABLE)
public class User {
	/**
     * CPF do usuário.
     * Atua como chave primária da entidade.
     */
	@Id
	@Column(length = 15, nullable = false, unique = true)
	private String cpf;
	
	/**
     * Nome do usuário.
     */
	@Column(nullable = false)
	private String name;
	
	/**
     * Número de telefone do usuário.
     */
	private String phoneNumber;
	
	/**
     * Data de nascimento do usuário.
     */
	private LocalDate birthDate;
	
	/**
     * Login do usuário no sistema.
     */
	private String login;
	
	/**
     * Senha do usuário.
     * Armazenada em hash simples.
     */
	@Column(nullable = false)
	private String password;
	
	/**
     * Perfil do usuário no sistema.
     * Define permissões e níveis de acesso.
     */
	@Column(nullable = false)
	private String profile;
	
	/**
     * Endereço de e-mail do usuário.
     * Utilizado para autenticação e envio de notificações.
     */
	@Column(nullable = false, unique = true)
	private String email;
	
	/**
     * Indica se o e-mail do usuário foi confirmado.
     * Utilizado para controle de envio de notificações e validação de comunicação.
     */
	@Column(nullable = false)
	private Boolean confirmedEmail;
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getConfirmedEmail() {
		return confirmedEmail;
	}
	
	public void setConfirmedEmail(Boolean confirmedEmail) {
		this.confirmedEmail = confirmedEmail;
	}
}