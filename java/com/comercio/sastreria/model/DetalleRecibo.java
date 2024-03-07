package com.comercio.sastreria.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalles_recibos")
public class DetalleRecibo {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name ="id_recibo", nullable = false, referencedColumnName = "id_recibo")
    private Recibo recibo;

    @ManyToOne
    @JoinColumn(name ="id_transac", referencedColumnName = "id_transac")
    private Transaccion transaccion;
    
    @Column(name = "monto", nullable = false, precision = 18, scale = 2)
	private BigDecimal monto;
    
    public BigDecimal getImpDR() {
	    if (transaccion != null) {
	        return monto;
	    } else {
	        return BigDecimal.ZERO; // o manejo de error adecuado
	    }
	}

	public DetalleRecibo() {
	}
    
	public DetalleRecibo(Long id, Recibo recibo, Transaccion transaccion, BigDecimal monto) {
		super();
		this.id = id;
		this.recibo = recibo;
		this.transaccion = transaccion;
		this.monto = monto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Recibo getRecibo() {
		return recibo;
	}

	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
	}

	public Transaccion getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
}