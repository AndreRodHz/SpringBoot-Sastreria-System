package com.comercio.sastreria.model;


import jakarta.persistence.*;

@Entity
@Table(name = "comprobantes")
public class Comprobante {

	@Id
	@Column(name = "id_comprobante")
	private String idComprobante;
	
	@Column(name = "nombre_com")
	private String nombreComprobante;

	// Constructores
	
	public Comprobante() {
	}

	public Comprobante(String idComprobante, String nombreComprobante) {
		super();
		this.idComprobante = idComprobante;
		this.nombreComprobante = nombreComprobante;
	}
	
	// Getters y Setters

	public String getIdComprobante() {
		return idComprobante;
	}

	public void setIdComprobante(String idComprobante) {
		this.idComprobante = idComprobante;
	}

	public String getNombreComprobante() {
		return nombreComprobante;
	}

	public void setNombreComprobante(String nombreComprobante) {
		this.nombreComprobante = nombreComprobante;
	}
}