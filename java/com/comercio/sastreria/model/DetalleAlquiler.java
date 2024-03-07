package com.comercio.sastreria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_alquileres")
public class DetalleAlquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
	
	@ManyToOne
	@JoinColumn(name ="id_alquiler", nullable = false, referencedColumnName = "id_alquiler")
	private Alquiler alquiler;
	
	@ManyToOne
	@JoinColumn(name ="id_producto", nullable = false, referencedColumnName = "id_producto")
	private Producto producto;
	
	@Column(name = "precio_u", nullable = false, precision = 18, scale = 2)
	private BigDecimal precioUnidadAlquiler;
	
	@Column(name = "cantidad_alquiler",  nullable = false)
	private Integer cantidadAlquiler;
	
	public BigDecimal getImpDA() {
	    BigDecimal cantidadBigDecimal = new BigDecimal(cantidadAlquiler);
	    return precioUnidadAlquiler.multiply(cantidadBigDecimal);
	}

	// Constructores
	
	public DetalleAlquiler() {
	}


	public DetalleAlquiler(Long id, Alquiler alquiler, Producto producto, BigDecimal precioUnidadAlquiler,
			Integer cantidadAlquiler) {
		super();
		this.id = id;
		this.alquiler = alquiler;
		this.producto = producto;
		this.precioUnidadAlquiler = precioUnidadAlquiler;
		this.cantidadAlquiler = cantidadAlquiler;
	}

	// Getters y Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Alquiler getAlquiler() {
		return alquiler;
	}

	public void setAlquiler(Alquiler alquiler) {
		this.alquiler = alquiler;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public BigDecimal getPrecioUnidadAlquiler() {
		return precioUnidadAlquiler;
	}

	public void setPrecioUnidadAlquiler(BigDecimal precioUnidadAlquiler) {
		this.precioUnidadAlquiler = precioUnidadAlquiler;
	}

	public Integer getCantidadAlquiler() {
		return cantidadAlquiler;
	}

	public void setCantidadAlquiler(Integer cantidadAlquiler) {
		this.cantidadAlquiler = cantidadAlquiler;
	}
}