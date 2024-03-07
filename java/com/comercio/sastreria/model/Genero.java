package com.comercio.sastreria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "generos")
public class Genero {
	
	@Id
	@Column(name = "id_genero")
	private String idGenero;
	
	@Column(name = "nombre_gen", nullable = false)
	private String nombreGenero;

	// Constructores
	
	public Genero() {
	}

	public Genero(String idGenero, String nombreGenero) {
		super();
		this.idGenero = idGenero;
		this.nombreGenero = nombreGenero;
	}
	
	// Getters y Setters
	
	public String getIdGenero() {
		return idGenero;
	}

	public void setIdGenero(String idGenero) {
		this.idGenero = idGenero;
	}

	public String getNombreGenero() {
		return nombreGenero;
	}

	public void setNombreGenero(String nombreGenero) {
		this.nombreGenero = nombreGenero;
	}
}