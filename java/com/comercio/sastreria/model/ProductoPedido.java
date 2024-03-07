package com.comercio.sastreria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "productos_pedidos")
public class ProductoPedido {
	
	@Id
	@Column(name = "id_prod_pedi")
	private String idProductoPedido;
	
	@Column(name = "nombre_pp", nullable = false)
	private String nombreProductoPedido;
	
	@Column(name = "descripcion_pp")
	private String descripcionProductoPedido;

	// Constructores
	
	public ProductoPedido() {
	}

	public ProductoPedido(String idProductoPedido, String nombreProductoPedido, String descripcionProductoPedido) {
		super();
		this.idProductoPedido = idProductoPedido;
		this.nombreProductoPedido = nombreProductoPedido;
		this.descripcionProductoPedido = descripcionProductoPedido;
	}

	// Getters y Setters
	
	public String getIdProductoPedido() {
		return idProductoPedido;
	}

	public void setIdProductoPedido(String idProductoPedido) {
		this.idProductoPedido = idProductoPedido;
	}

	public String getNombreProductoPedido() {
		return nombreProductoPedido;
	}

	public void setNombreProductoPedido(String nombreProductoPedido) {
		this.nombreProductoPedido = nombreProductoPedido;
	}

	public String getDescripcionProductoPedido() {
		return descripcionProductoPedido;
	}

	public void setDescripcionProductoPedido(String descripcionProductoPedido) {
		this.descripcionProductoPedido = descripcionProductoPedido;
	}
}