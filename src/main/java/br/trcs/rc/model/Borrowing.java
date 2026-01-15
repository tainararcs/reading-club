package br.trcs.rc.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidade que representa um empréstimo de revista no sistema.
 * <br>
 * Cada empréstimo associa um {@link User} a uma {@link Comic},
 * registrando as datas de retirada, devolução prevista e devolução efetiva.
 */
@Entity
@Table(name = "borrowings")
public class Borrowing {
	
	/**
     * Identificador único do empréstimo.
     * Gerado automaticamente pelo banco de dados.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
     * Usuário responsável pelo empréstimo.
     */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userCpf", nullable = false)
	private User user;
	
	/**
     * Revista emprestada.
     */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "comicId", nullable = false)
	private Comic comic;
	
	/**
     * Data em que a revista foi retirada.
     */
	@Column(nullable = false)
	private LocalDate checkoutDate;
	
	/**
     * Data prevista para devolução da revista.
     */
	@Column(nullable = false)
	private LocalDate expectedReturnDate;
	
	/**
     * Data em que a revista foi efetivamente devolvida.
     * Quando nula, indica que o empréstimo ainda está ativo.
     */
	private LocalDate returnDate;
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Comic getComic() {
		return comic;
	}

	public void setComic(Comic comic) {
		this.comic = comic;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}
	
	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	
	public LocalDate getExpectedReturnDate() {
		return expectedReturnDate;
	}
	
	public void setExpectedReturnDate(LocalDate expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}
	
	public LocalDate getReturnDate() {
		return returnDate;
	}
	
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
}
