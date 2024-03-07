package com.comercio.sastreria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "condiciones")
public class Condicion {
	
	@Id
	@Column(name = "id_condicion")
	private String idCondicion;
	
//	@Transient
//	private String generatedCondicionId; // Propiedad para el ID personalizado generado
	
	@Column(name = "nombre_condi", nullable = false)
	private String nombreCondicion;
	
	@Column(name = "descrip_condi")
	private String descripcionCondicion;

	// Constructores
	
	public Condicion() {
	}

	public Condicion(String idCondicion, String nombreCondicion, String descripcionCondicion) {
		super();
		this.idCondicion = idCondicion;
		this.nombreCondicion = nombreCondicion;
		this.descripcionCondicion = descripcionCondicion;
	}

	// Getters y Setters
	
	public String getIdCondicion() {
		return idCondicion;
	}

	public void setIdCondicion(String idCondicion) {
		this.idCondicion = idCondicion;
	}

	public String getNombreCondicion() {
		return nombreCondicion;
	}

	public void setNombreCondicion(String nombreCondicion) {
		this.nombreCondicion = nombreCondicion;
	}

	public String getDescripcionCondicion() {
		return descripcionCondicion;
	}

	public void setDescripcionCondicion(String descripcionCondicion) {
		this.descripcionCondicion = descripcionCondicion;
	}
}