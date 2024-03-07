package com.comercio.sastreria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {
	
	@Id
	@Column(name = "id_venta")
	private String idVenta;
	
	@OneToOne
	@JoinColumn(name = "id_transac", nullable = false, unique = true, referencedColumnName = "id_transac")
	private Transaccion transaccion;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false, referencedColumnName = "id_cliente")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "id_empleado", nullable = false, referencedColumnName = "id_empleado")
	private Empleado empleado;
	
	@Column(name = "fecha_v", nullable = false)
	private Timestamp fechaVenta;
	
	@Column(name = "total_v", nullable = false, precision = 18, scale = 2)
	private BigDecimal totalVenta;
	
	//Extra
	@OneToMany(mappedBy = "venta")
	private List<DetalleVenta> detallesVenta;

	
	// Constructores

	public Venta() {
	}

	public Venta(String idVenta, Transaccion transaccion, Cliente cliente,
				 Empleado empleado, Timestamp fechaVenta, BigDecimal totalVenta) {
		super();
		this.idVenta = idVenta;
		this.transaccion = transaccion;
		this.cliente = cliente;
		this.empleado = empleado;
		this.fechaVenta = fechaVenta;
		this.totalVenta = totalVenta;
	}
	
	public Venta(String idVenta, Transaccion transaccion, Cliente cliente,
				 Empleado empleado, Timestamp fechaVenta, BigDecimal totalVenta,
			List<DetalleVenta> detallesVenta) {
		super();
		this.idVenta = idVenta;
		this.transaccion = transaccion;
		this.cliente = cliente;
		this.empleado = empleado;
		this.fechaVenta = fechaVenta;
		this.totalVenta = totalVenta;
		this.detallesVenta = detallesVenta;
	}
	
	// Getters y Setters

	public String getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(String idVenta) {
		this.idVenta = idVenta;
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

	public Timestamp getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Timestamp fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public BigDecimal getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(BigDecimal totalVenta) {
		this.totalVenta = totalVenta;
	}
	
	public List<DetalleVenta> getDetallesVenta() {
	    return detallesVenta;
	}

	public void setDetallesVenta(List<DetalleVenta> detallesVenta) {
	    this.detallesVenta = detallesVenta;
	}
}