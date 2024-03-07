package com.comercio.sastreria.model;


import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "empleados")
public class Empleado {
	
	@Id
	@Column(name = "id_empleado")
	private String idEmpleado;
	
	@ManyToOne
	@JoinColumn(name = "id_rol", nullable = false, referencedColumnName = "id_rol")
	private Rol rol;
	
	@Column(name = "nombre_e", nullable = false)
	private String nombreEmpleado;
	
	@ManyToOne
	@JoinColumn(name = "id_genero", nullable = false, referencedColumnName = "id_genero")
	private Genero genero;
	
	@ManyToOne
	@JoinColumn(name = "id_documento", nullable = false, referencedColumnName = "id_documento")
	private Documento documento;
	
	@Column(name = "num_doc_e", nullable = false, unique = true)
	private String numeroDocEmpleado;
	
	@Column(name = "telefono_e", nullable = false)
	private String telefonoEmpleado;
	
	@Column(name = "direccion_e")
	private String direccionEmpleado;
	
	@Column(name = "email_e", nullable = false)
	private String emailEmpleado;
	
	@Column(name = "password_e", nullable = false)
	private String passwordEmpleado;
	
	@Column(name = "fecha", nullable = false)
	private Timestamp fecha;
	
	// Constructores
	
	public Empleado() {
	}

	public Empleado(String idEmpleado, Rol rol, String nombreEmpleado, Genero genero, Documento documento,
			String numeroDocEmpleado, String telefonoEmpleado, String direccionEmpleado, String emailEmpleado,
			String passwordEmpleado, Timestamp fecha) {
		super();
		this.idEmpleado = idEmpleado;
		this.rol = rol;
		this.nombreEmpleado = nombreEmpleado;
		this.genero = genero;
		this.documento = documento;
		this.numeroDocEmpleado = numeroDocEmpleado;
		this.telefonoEmpleado = telefonoEmpleado;
		this.direccionEmpleado = direccionEmpleado;
		this.emailEmpleado = emailEmpleado;
		this.passwordEmpleado = passwordEmpleado;
		this.fecha = fecha;
	}

	// Getters y Setters
	
	public String getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(String idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
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

	public String getNumeroDocEmpleado() {
		return numeroDocEmpleado;
	}

	public void setNumeroDocEmpleado(String numeroDocEmpleado) {
		this.numeroDocEmpleado = numeroDocEmpleado;
	}

	public String getTelefonoEmpleado() {
		return telefonoEmpleado;
	}

	public void setTelefonoEmpleado(String telefonoEmpleado) {
		this.telefonoEmpleado = telefonoEmpleado;
	}

	public String getDireccionEmpleado() {
		return direccionEmpleado;
	}

	public void setDireccionEmpleado(String direccionEmpleado) {
		this.direccionEmpleado = direccionEmpleado;
	}

	public String getEmailEmpleado() {
		return emailEmpleado;
	}

	public void setEmailEmpleado(String emailEmpleado) {
		this.emailEmpleado = emailEmpleado;
	}

	public String getPasswordEmpleado() {
		return passwordEmpleado;
	}

	public void setPasswordEmpleado(String passwordEmpleado) {
		this.passwordEmpleado = passwordEmpleado;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
}