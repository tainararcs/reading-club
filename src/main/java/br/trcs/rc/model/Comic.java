package br.trcs.rc.model;

import br.trcs.rc.utils.Consts;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade que representa uma revista (quadrinho) do acervo. Armazena informações de identificação da revista, 
 * como coleção, número da edição, ano de publicação e disponibilidade para empréstimo.
 * Cada revista pertence a uma caixa física identificada pelo {@code boxId}.
 */
@Entity
@Table(name = Consts.COMIC_TABLE)
public class Comic {
	/**
     * Identificador único da revista.
     * Gerado automaticamente pelo banco de dados.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
     * Nome da coleção à qual a revista pertence.
     */
	private String collection;
	
	/**
     * Número da edição da revista dentro da coleção.
     */
	private Integer editionNumber;
	
	/**
     * Ano de publicação da revista.
     */
	private String year;
	
	/**
     * Indica se a revista está disponível para empréstimo.
     */
	private boolean availability;
	
	/**
     * Identificador da caixa onde a revista está armazenada.
     */
	@Column(nullable = false)
	private int boxId;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCollection() {
		return collection;
	}
	
	public void setCollection(String collection) {
		this.collection = collection;
	}
	
	public Integer getEditionNumber() {
		return editionNumber;
	}
	
	public void setEditionNumber(Integer editionNumber) {
		this.editionNumber = editionNumber;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public boolean getAvailability() {
		return availability;
	}
	
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	
	public int getBoxId() {
		return boxId;
	}
	
	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}
}
