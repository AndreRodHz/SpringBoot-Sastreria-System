package com.comercio.sastreria.model;


import jakarta.persistence.*;

@Entity
@Table(name = "documentos")
public class Documento {
	
	@Id
	@Column(name = "id_documento")
	private String idDocumento;
	
	@Column(name = "nombre_doc", nullable = false)
	private String nombreDocumento;

	// Constructores
	
	public Documento() {
	}

	public Documento(String idDocumento, String nombreDocumento) {
		super();
		this.idDocumento = idDocumento;
		this.nombreDocumento = nombreDocumento;
	}
	
	// Getters y Setters
	
	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
	
	// toString
	
	@Override
	public String toString() {
		return "Documento [idDocumento=" + idDocumento + ", nombreDocumento=" + nombreDocumento + "]";
	}
}