package com.comercio.sastreria.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "transacciones")
public class Transaccion {
	
	@Id
	@Column(name = "id_transac")
	private String idTransaccion;
	
	@Column(name = "tipo_transac", nullable = false)
	private String tipoTransaccion;
	
	@Column(name = "fecha_t", nullable = false)
	private Timestamp fechaTransaccion;
	
	@Column(name = "monto", nullable = false)
	private BigDecimal monto;
	
	@Column(name = "id_operacion", nullable = false, unique = true)
	private String idOperacion;

	// Constructores
	
	public Transaccion() {
	}
	
	public Transaccion(String idTransaccion, String tipoTransaccion, Timestamp fechaTransaccion, BigDecimal monto,
			String idOperacion) {
		super();
		this.idTransaccion = idTransaccion;
		this.tipoTransaccion = tipoTransaccion;
		this.fechaTransaccion = fechaTransaccion;
		this.monto = monto;
		this.idOperacion = idOperacion;
	}

	// Getters y Setters
	
	public String getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(String idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	public Timestamp getFechaTransaccion() {
		return fechaTransaccion;
	}

	public void setFechaTransaccion(Timestamp fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(String idOperacion) {
		this.idOperacion = idOperacion;
	}
}