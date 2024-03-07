package com.comercio.sastreria.model;

import jakarta.persistence.*;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "recibos")
public class Recibo {
	
	@Id
	@Column(name = "id_recibo")
	private String idRecibo;
	
	@ManyToOne
	@JoinColumn(name = "id_comprobante", nullable = false, referencedColumnName = "id_comprobante")
	private Comprobante comprobante;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false, referencedColumnName = "id_cliente")
	private Cliente cliente;
	
	@Column(name = "num_comprobante", nullable = false, unique = true)
	private String numeroComprobanteRecibo;
	
	@Column(name = "fecha_recibo", nullable = false)
	private Timestamp fechaRecibo;
	
	@Column(name = "base_rec", nullable = false, precision = 18, scale = 2)
	private BigDecimal baseRecibo;
	
	@Column(name = "impuesto_rec", nullable = false, precision = 18, scale = 2)
	private BigDecimal impuestoRecibo;
	
	@Column(name = "total_rec", nullable = false, precision = 18, scale = 2)
	private BigDecimal totalRecibo;
	
	@Column(name = "estado", nullable = false)
	private String estadoRecibo;
	
	@ManyToOne
	@JoinColumn(name = "id_empleado", nullable = false, referencedColumnName = "id_empleado")
	private Empleado empleado;
	
	//Extra
	@OneToMany(mappedBy = "recibo")
	private List<DetalleRecibo> detalleRecibo;

	// Constructores
	
	public Recibo() {
	}

	public Recibo(String idRecibo, Cliente cliente, Comprobante comprobante, String numeroComprobanteRecibo, Timestamp fechaRecibo,
				  BigDecimal baseRecibo, BigDecimal impuestoRecibo, BigDecimal totalRecibo, String estadoRecibo, Empleado empleado) {
		super();
		this.idRecibo = idRecibo;
		this.cliente = cliente;
		this.comprobante = comprobante;
		this.numeroComprobanteRecibo = numeroComprobanteRecibo;
		this.fechaRecibo = fechaRecibo;
		this.baseRecibo = baseRecibo;
		this.impuestoRecibo = impuestoRecibo;
		this.totalRecibo = totalRecibo;
		this.estadoRecibo = estadoRecibo;
		this.empleado = empleado;
	}
	
	public Recibo(String idRecibo, Comprobante comprobante, String numeroComprobanteRecibo, Timestamp fechaRecibo,
			BigDecimal baseRecibo, BigDecimal impuestoRecibo, BigDecimal totalRecibo, String estadoRecibo,
			List<DetalleRecibo> detalleRecibo) {
		super();
		this.idRecibo = idRecibo;
		this.comprobante = comprobante;
		this.numeroComprobanteRecibo = numeroComprobanteRecibo;
		this.fechaRecibo = fechaRecibo;
		this.baseRecibo = baseRecibo;
		this.impuestoRecibo = impuestoRecibo;
		this.totalRecibo = totalRecibo;
		this.estadoRecibo = estadoRecibo;
		this.detalleRecibo = detalleRecibo;
	}

	// Getters y Setters

	public String getIdRecibo() {
		return idRecibo;
	}

	public void setIdRecibo(String idRecibo) {
		this.idRecibo = idRecibo;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public String getNumeroComprobanteRecibo() {
		return numeroComprobanteRecibo;
	}

	public void setNumeroComprobanteRecibo(String numeroComprobanteRecibo) {
		this.numeroComprobanteRecibo = numeroComprobanteRecibo;
	}

	public Timestamp getFechaRecibo() {
		return fechaRecibo;
	}

	public void setFechaRecibo(Timestamp fechaActual_1) {
		this.fechaRecibo = fechaActual_1;
	}

	public BigDecimal getBaseRecibo() {
		return baseRecibo;
	}

	public void setBaseRecibo(BigDecimal baseRecibo) {
		this.baseRecibo = baseRecibo;
	}

	public BigDecimal getImpuestoRecibo() {
		return impuestoRecibo;
	}

	public void setImpuestoRecibo(BigDecimal impuestoRecibo) {
		this.impuestoRecibo = impuestoRecibo;
	}

	public BigDecimal getTotalRecibo() {
		return totalRecibo;
	}

	public void setTotalRecibo(BigDecimal totalRecibo) {
		this.totalRecibo = totalRecibo;
	}

	public String getEstadoRecibo() {
		return estadoRecibo;
	}

	public void setEstadoRecibo(String estadoRecibo) {
		this.estadoRecibo = estadoRecibo;
	}

	public List<DetalleRecibo> getDetalleRecibo() {
		return detalleRecibo;
	}

	public void setDetalleRecibo(List<DetalleRecibo> detalleRecibo) {
		this.detalleRecibo = detalleRecibo;
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
}