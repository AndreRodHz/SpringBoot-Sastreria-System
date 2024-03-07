package com.comercio.sastreria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {
	
	@Id
	@Column(name = "id_rol")
	private String idRol;
	
	@Column(name = "nombre_rol", nullable = false)
	private String nombreRol;

	public Rol() {
	}

	public Rol(String idRol, String nombreRol) {
		super();
		this.idRol = idRol;
		this.nombreRol = nombreRol;
	}

	public String getIdRol() {
		return idRol;
	}

	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	
	// toString
	@Override
	public String toString() {
		return "Rol [idRol=" + idRol + ", nombreRol=" + nombreRol + "]";
	}
}