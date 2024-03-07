package com.comercio.sastreria.model;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {
	
	@Id
	@Column(name = "id_cliente")
	private String idCliente;
	
	@Column(name = "nombre_c", nullable = false)
	private String nombreCliente;
	
	@ManyToOne
	@JoinColumn(name = "id_genero", nullable = false, referencedColumnName = "id_genero")
	private Genero genero;
	
	@ManyToOne
	@JoinColumn(name = "id_documento", nullable = false, referencedColumnName = "id_documento")
	private Documento documento;
	
	@Column(name = "num_doc_c", nullable = false, unique = true)
	private String numeroDocCliente;
	
	@Column(name = "telefono_c")
	private String telefonoCliente;
	
	@Column(name = "email_c")
	private String emailCliente;
	
	@Column(name = "fecha", nullable = false)
	private Timestamp fecha;
	
	// Constructores
	
	public Cliente() {
	}

	public Cliente(String idCliente, String nombreCliente, Genero genero, Documento documento, String numeroDocCliente,
			String telefonoCliente, String emailCliente, Timestamp fecha) {
		super();
		this.idCliente = idCliente;
		this.nombreCliente = nombreCliente;
		this.genero = genero;
		this.documento = documento;
		this.numeroDocCliente = numeroDocCliente;
		this.telefonoCliente = telefonoCliente;
		this.emailCliente = emailCliente;
		this.fecha = fecha;
	}
	
	// Getters y Setters
	
	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public String getNumeroDocCliente() {
		return numeroDocCliente;
	}

	public void setNumeroDocCliente(String numeroDocCliente) {
		this.numeroDocCliente = numeroDocCliente;
	}

	public String getTelefonoCliente() {
		return telefonoCliente;
	}

	public void setTelefonoCliente(String telefonoCliente) {
		this.telefonoCliente = telefonoCliente;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
}