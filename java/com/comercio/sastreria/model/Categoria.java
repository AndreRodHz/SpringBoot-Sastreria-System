package com.comercio.sastreria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {
	
	@Id
	@Column(name = "id_categoria")
	private String idCategoria;
	
	@Column(name = "nombre_cate", nullable = false)
	private String nombreCate;
	
	@Column(name = "descrip_cate")
	private String descripcionCategoria;
	
	// Constructores

	public Categoria() {
	}

	public Categoria(String idCategoria, String nombreCate, String descripcionCategoria) {
		super();
		this.idCategoria = idCategoria;
		this.nombreCate = nombreCate;
		this.descripcionCategoria = descripcionCategoria;
	}
	
	// Getter y Setters

	public String getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(String idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreCate() {
		return nombreCate;
	}

	public void setNombreCate(String nombreCate) {
		this.nombreCate = nombreCate;
	}

	public String getDescripcionCategoria() {
		return descripcionCategoria;
	}

	public void setDescripcionCategoria(String descripcionCategoria) {
		this.descripcionCategoria = descripcionCategoria;
	}
}