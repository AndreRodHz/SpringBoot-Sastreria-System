package com.comercio.sastreria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {
	
	@Id
	@Column(name = "id_pedido")
	private String idPedido;
	
	@OneToOne
	@JoinColumn(name = "id_transac", nullable = false, unique = true, referencedColumnName = "id_transac")
	private Transaccion transaccion;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false, referencedColumnName = "id_cliente")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "id_empleado", nullable = false, referencedColumnName = "id_empleado")
	private Empleado empleado;
	
	@Column(name = "fecha_p", nullable = false)
	private Timestamp fechaPedido;
	
	@Column(name = "descrip")
	private String descripcionPedido;
	
	@Column(name = "total_p", nullable = false, precision = 18, scale = 2)
	private BigDecimal totalPedido;
	
	//Extra
	@OneToMany(mappedBy = "pedido")
	private List<DetallePedido> detallesPedido;

	// Constructores
	
	public Pedido() {
		super();
	}

	public Pedido(String idPedido, Transaccion transaccion, Cliente cliente, Empleado empleado,
			Timestamp fechaPedido, String descripcionPedido, BigDecimal totalPedido) {
		super();
		this.idPedido = idPedido;
		this.transaccion = transaccion;
		this.cliente = cliente;
		this.empleado = empleado;
		this.fechaPedido = fechaPedido;
		this.descripcionPedido = descripcionPedido;
		this.totalPedido = totalPedido;
	}
	
	public Pedido(String idPedido, Transaccion transaccion, Cliente cliente, Empleado empleado, Timestamp fechaPedido,
			String descripcionPedido, BigDecimal totalPedido, List<DetallePedido> detallesPedido) {
		super();
		this.idPedido = idPedido;
		this.transaccion = transaccion;
		this.cliente = cliente;
		this.empleado = empleado;
		this.fechaPedido = fechaPedido;
		this.descripcionPedido = descripcionPedido;
		this.totalPedido = totalPedido;
		this.detallesPedido = detallesPedido;
	}

	// Getters y Setters
	
	public String getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
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

	public Timestamp getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Timestamp fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public String getDescripcionPedido() {
		return descripcionPedido;
	}

	public void setDescripcionPedido(String descripcionPedido) {
		this.descripcionPedido = descripcionPedido;
	}

	public BigDecimal getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(BigDecimal totalPedido) {
		this.totalPedido = totalPedido;
	}

	public List<DetallePedido> getDetallesPedido() {
		return detallesPedido;
	}

	public void setDetallesPedido(List<DetallePedido> detallesPedido) {
		this.detallesPedido = detallesPedido;
	}
}