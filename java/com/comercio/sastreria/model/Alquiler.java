package com.comercio.sastreria.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.math.BigDecimal;

@Entity
@Table(name = "alquileres")
public class Alquiler {
	
	@Id
	@Column(name = "id_alquiler")
	private String idAlquiler;
	
	@OneToOne
	@JoinColumn(name = "id_transac", nullable = false, unique = true, referencedColumnName = "id_transac")
	private Transaccion transaccion;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false, referencedColumnName = "id_cliente")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "id_empleado", nullable = false, referencedColumnName = "id_empleado")
	private Empleado empleado;
	
	@Column(name = "estado", nullable = false)
	private String estado;
	
	@Column(name = "fecha_a", nullable = false)
	private Timestamp fechaAlquiler;
	
	@Column(name = "total_a", nullable = false, precision = 18, scale = 2)
	private BigDecimal totalAlquiler;
	
	//Extra
	@OneToMany(mappedBy = "alquiler")
	private List<DetalleAlquiler> detallesAlquiler;

	// Constructores
	
	public Alquiler() {
	}
	
	public Alquiler(String idAlquiler, Transaccion transaccion, Cliente cliente, Empleado empleado, String estado,
			Timestamp fechaAlquiler, BigDecimal totalAlquiler) {
		super();
		this.idAlquiler = idAlquiler;
		this.transaccion = transaccion;
		this.cliente = cliente;
		this.empleado = empleado;
		this.estado = estado;
		this.fechaAlquiler = fechaAlquiler;
		this.totalAlquiler = totalAlquiler;
	}

	public Alquiler(String idAlquiler, Transaccion transaccion, Cliente cliente, Empleado empleado, String estado,
			Timestamp fechaAlquiler, BigDecimal totalAlquiler, List<DetalleAlquiler> detallesAlquiler) {
		super();
		this.idAlquiler = idAlquiler;
		this.transaccion = transaccion;
		this.cliente = cliente;
		this.empleado = empleado;
		this.estado = estado;
		this.fechaAlquiler = fechaAlquiler;
		this.totalAlquiler = totalAlquiler;
		this.detallesAlquiler = detallesAlquiler;
	}
	
	// Getters y Setters
	
	public String getIdAlquiler() {
		return idAlquiler;
	}

	public void setIdAlquiler(String idAlquiler) {
		this.idAlquiler = idAlquiler;
	}

	public Transaccion getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Timestamp getFechaAlquiler() {
		return fechaAlquiler;
	}

	public void setFechaAlquiler(Timestamp fechaAlquiler) {
		this.fechaAlquiler = fechaAlquiler;
	}

	public BigDecimal getTotalAlquiler() {
		return totalAlquiler;
	}

	public void setTotalAlquiler(BigDecimal totalAlquiler) {
		this.totalAlquiler = totalAlquiler;
	}

	public List<DetalleAlquiler> getDetallesAlquiler() {
		return detallesAlquiler;
	}

	public void setDetallesAlquiler(List<DetalleAlquiler> detallesAlquiler) {
		this.detallesAlquiler = detallesAlquiler;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}