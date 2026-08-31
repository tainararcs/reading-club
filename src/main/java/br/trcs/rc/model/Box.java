package br.trcs.rc.model;

import br.trcs.rc.utils.Consts;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade que representa uma caixa física de armazenamento.
 * Utilizada para organizar e identificar a localização das revistas.
 */
@Entity
@Table(name = Consts.BOX_TABLE)
public class Box {
	/**
     * Identificador único da caixa.
     * Gerado automaticamente pelo banco de dados.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/**
     * Número identificador da caixa.
     */
	@Column(nullable = false, unique = true)
	private Integer number;
	
	/**
     * Cor da caixa.
     */
	@Column(nullable = false)
	private String color;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getNumber() {
		return number;
	}
	
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
}
