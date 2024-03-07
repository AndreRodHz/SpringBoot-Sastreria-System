package com.comercio.sastreria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_ventas")
public class DetalleVenta {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
	
    @ManyToOne
    @JoinColumn(name ="id_venta", nullable = false, referencedColumnName = "id_venta")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name ="id_producto", referencedColumnName = "id_producto")
    private Producto producto;
	
	@Column(name = "precio_u", nullable = false, precision = 18, scale = 2)
	private BigDecimal precioUnidadVenta;
	
	@Column(name = "cantidad_venta", nullable = false)
	private Integer cantidadVenta;
	
	public BigDecimal getImpDV() {
		BigDecimal cantidadBigDecimal = new BigDecimal(cantidadVenta);
	    if (producto != null && producto.getPrecioProducto() != null) {
	        return producto.getPrecioProducto().multiply(cantidadBigDecimal);
	    } else {
	        return BigDecimal.ZERO; // o manejo de error adecuado
	    }
	}

	// Constructores
	
	public DetalleVenta() {
	}

	public DetalleVenta(Long id, Venta venta, Producto producto, BigDecimal precioUnidadVenta, Integer cantidadVenta) {
		super();
		this.id = id;
		this.venta = venta;
		this.producto = producto;
		this.precioUnidadVenta = precioUnidadVenta;
		this.cantidadVenta = cantidadVenta;
	}

	// Getters y Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public BigDecimal getPrecioUnidadVenta() {
		return precioUnidadVenta;
	}

	public void setPrecioUnidadVenta(BigDecimal precioUnidadVenta) {
		this.precioUnidadVenta = precioUnidadVenta;
	}

	public Integer getCantidadVenta() {
		return cantidadVenta;
	}

	public void setCantidadVenta(Integer cantidadVenta) {
		this.cantidadVenta = cantidadVenta;
	}
}