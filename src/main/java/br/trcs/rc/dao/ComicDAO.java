package br.trcs.rc.dao;

import java.util.Collections;
import java.util.List;

import br.trcs.rc.model.Comic;

/**
 * DAO responsável pelas operações de acesso a dados da entidade {@link Comic}.
 * Esta classe encapsula as consultas relacionadas às revistas cadastradas,
 * delegando à superclasse {@link DAO} as operações genéricas de persistência.
 */
public class ComicDAO extends DAO<Comic> {
	
	/**
     * Informa à superclasse qual entidade este DAO gerencia.
     * 
     * @param currentClass classe da entidade {@link Comic}.
     */
	public ComicDAO(Class<Comic> currentClass) {
		super(currentClass);
	}
	
	/**
     * Retorna a lista de revistas atualmente disponíveis para empréstimo.
     * Uma revista é considerada disponível quando o atributo
     * {@code availability} está definido como {@code true}.
     *
     * @return lista de revistas disponíveis; lista vazia caso não existam resultados.
     */
	public List<Comic> listAvailableComics() {
        return findByQuery("SELECT c FROM Comic c WHERE c.availability = true", Collections.emptyMap());
    }
}
