package com.comercio.sastreria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "inferiores")
public class Inferior {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	
	@OneToOne
	@JoinColumn(name = "id_pedido", nullable = false, unique = true, referencedColumnName = "id_pedido")
	private Pedido pedido;
	
	@Column(name = "caderas", precision = 18, scale = 2)
	private BigDecimal caderas;
	
	@Column(name = "largo", precision = 18, scale = 2)
	private BigDecimal largo;
	
	@Column(name = "tiro", precision = 18, scale = 2)
	private BigDecimal tiro;
	
	@Column(name = "posicion", precision = 18, scale = 2)
	private BigDecimal posicion;
	
	@Column(name = "muslos", precision = 18, scale = 2)
	private BigDecimal muslos;
	
	// Constructores
	
	public Inferior() {
	}
	
	public Inferior(Integer id, Pedido pedido, BigDecimal caderas, BigDecimal largo, BigDecimal tiro, BigDecimal posicion,
			BigDecimal muslos) {
		super();
		this.id = id;
		this.pedido = pedido;
		this.caderas = caderas;
		this.largo = largo;
		this.tiro = tiro;
		this.posicion = posicion;
		this.muslos = muslos;
	}

	// Getters y Setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public BigDecimal getCaderas() {
		return caderas;
	}

	public void setCaderas(BigDecimal caderas) {
		this.caderas = caderas;
	}

	public BigDecimal getLargo() {
		return largo;
	}

	public void setLargo(BigDecimal largo) {
		this.largo = largo;
	}

	public BigDecimal getTiro() {
		return tiro;
	}

	public void setTiro(BigDecimal tiro) {
		this.tiro = tiro;
	}

	public BigDecimal getPosicion() {
		return posicion;
	}

	public void setPosicion(BigDecimal posicion) {
		this.posicion = posicion;
	}

	public BigDecimal getMuslos() {
		return muslos;
	}

	public void setMuslos(BigDecimal muslos) {
		this.muslos = muslos;
	}
}