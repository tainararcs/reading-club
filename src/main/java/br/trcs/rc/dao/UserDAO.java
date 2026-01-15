package br.trcs.rc.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.trcs.rc.model.Comic;
import br.trcs.rc.model.User;

/**
 * DAO responsável pelas operações de acesso a dados da entidade {@link User}.
 * <br>
 * Centraliza as consultas e operações relacionadas aos usuários do sistema,
 * utilizando a infraestrutura genérica fornecida pela superclasse {@link DAO}.
 */
public class UserDAO extends DAO<User> {

	/**
     * Construtor padrão.
     * Informa à superclasse qual entidade este DAO gerencia.
     * 
     *  @param currentClass classe da entidade {@link Comic}.
     */
	public UserDAO(Class<User> currentClass) {
		super(currentClass);
	}
	
	/**
     * Busca um usuário pelo e-mail e senha.
     *
     * @param email e-mail do usuário.
     * @param password senha do usuário.
     * @return usuário autenticado ou {@code null} se não encontrado.
     */
    public User findByEmailAndPassword(String email, String password) {
    	String jpql = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password";
        return findSingleByQuery(jpql, Map.of("email", email, "password", password)
        );
    }
    
    /**
     * Retorna a lista de usuários disponíveis para realizar novos empréstimos.
     * Um usuário é considerado disponível quando não possui nenhum empréstimo em aberto.
     *
     * @return lista de usuários disponíveis; lista vazia caso não existam resultados.
     */
    public List<User> listAvailableUsers() {
    	String jpql =  "SELECT u FROM User u WHERE NOT EXISTS (SELECT b FROM Borrowing b WHERE b.user = u AND b.returnDate IS NULL)";
        return findByQuery(jpql, Collections.emptyMap());
    }
}
