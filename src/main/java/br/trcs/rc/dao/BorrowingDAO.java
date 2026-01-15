package br.trcs.rc.dao;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import br.trcs.rc.model.Borrowing;
import br.trcs.rc.model.Comic;

/**
 * DAO responsável pelas operações de acesso a dados da entidade {@link Borrowing}.
 * <br>
 * Centraliza as consultas relacionadas aos empréstimos, incluindo carregamento
 * das entidades associadas ({@code User} e {@code Comic}) quando necessário,
 * utilizando a infraestrutura genérica da superclasse {@link DAO}.
 */
public class BorrowingDAO extends DAO<Borrowing> {

	/**
     * Construtor padrão.
     * Informa à superclasse qual entidade este DAO gerencia.
     * 
     * @param currentClass classe da entidade {@link Comic}.
     */
	public BorrowingDAO(Class<Borrowing> currentClass) {
		super(currentClass);
	}

	/**
     * Lista todos os empréstimos cadastrados, carregando explicitamente
     * as entidades relacionadas {@code User} e {@code Comic}.
     *
     * @return lista de todos os empréstimos com usuário e revista associados.
     */
    public List<Borrowing> listAllInfo() {
        String jpql = "SELECT b FROM Borrowing b JOIN FETCH b.user JOIN FETCH b.comic";
        return findByQuery(jpql, Collections.emptyMap());
    }

    /**
     * Lista todos os empréstimos associados a um usuário específico.
     * 
     * @param userCpf CPF do usuário.
     * @return lista de empréstimos do usuário informado.
     */
    public List<Borrowing> listAllInfoByUser(String userCpf) {
        String jpql = "SELECT b FROM Borrowing b JOIN FETCH b.user JOIN FETCH b.comic WHERE b.user.cpf = :cpf";
        return findByQuery(jpql, Collections.singletonMap("cpf", userCpf));
    }

    /**
     * Lista todos os empréstimos em atraso.
     * <br>
     * Um empréstimo é considerado em atraso quando:
     * <ul>
     *   <li>{@code returnDate} é {@code null} (empréstimo em aberto)</li>
     *   <li>{@code expectedReturnDate} é anterior à data atual</li>
     * </ul>
     *
     * @return lista de empréstimos em atraso.
     */
    public List<Borrowing> listOverdue() {
        LocalDate today = LocalDate.now();
        String jpql = "SELECT b FROM Borrowing b JOIN FETCH b.user JOIN FETCH b.comic WHERE b.returnDate IS NULL AND b.expectedReturnDate < :today";
        return findByQuery(jpql, Collections.singletonMap("today", today));
    }

    /**
     * Lista todos os empréstimos atualmente em aberto.
     *
     * @return lista de empréstimos em andamento.
     */
    public List<Borrowing> listBorrowed() {
    	String jpql = "SELECT b FROM Borrowing b JOIN FETCH b.user JOIN FETCH b.comic WHERE b.returnDate IS NULL";
        return findByQuery(jpql, Collections.emptyMap());
    }
    
    /**
     * Verifica se um usuário possui algum empréstimo em aberto.
     *
     * @param cpf CPF do usuário.
     * @return {@code true} se existir empréstimo em aberto; {@code false} caso contrário.
     */
    public Boolean hasOpenBorrowing(String cpf) {
    	String jpql = "SELECT b FROM Borrowing b WHERE b.user.cpf = :cpf AND b.returnDate IS NULL";
        return findSingleByQuery(jpql, Collections.singletonMap("cpf", cpf)) != null;
    }
}
