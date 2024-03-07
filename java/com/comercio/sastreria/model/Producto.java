package com.comercio.sastreria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "productos")
public class Producto {
	
	@Id
	@Column(name = "id_producto")
	private String idProducto;
	
	@ManyToOne
	@JoinColumn(name = "id_proveedor", nullable = false, referencedColumnName = "id_proveedor")
	private Proveedor proveedor;
	
	@Column(name = "nombre_p", nullable = false)
	private String nombreProducto;
	
	@Column(name = "descrip_p")
	private String descripcionProducto;
	
	@Column(name = "talla", nullable = false)
	private String tallaProducto;
	
	@ManyToOne
	@JoinColumn(name = "id_categoria", nullable = false, referencedColumnName = "id_categoria")
	private Categoria categoria;
	
	@ManyToOne
	@JoinColumn(name = "id_condicion", nullable = false, referencedColumnName = "id_condicion")
	private Condicion condicion;
	
	@Column(name = "precio_p", nullable = false, precision = 18, scale = 2)
	private BigDecimal precioProducto;
	
	@Column(name = "stock_p", nullable = false)
	private Integer stockProducto;
	
	@Column(name = "fecha", nullable = false)
	private Timestamp fecha;

	// Constructores
	
	public Producto() {
	}
	
	public Producto(String idProducto, Proveedor proveedor, String nombreProducto, String descripcionProducto,
			String tallaProducto, Categoria categoria, Condicion condicion, BigDecimal precioProducto,
			Integer stockProducto, Timestamp fecha) {
		super();
		this.idProducto = idProducto;
		this.proveedor = proveedor;
		this.nombreProducto = nombreProducto;
		this.descripcionProducto = descripcionProducto;
		this.tallaProducto = tallaProducto;
		this.categoria = categoria;
		this.condicion = condicion;
		this.precioProducto = precioProducto;
		this.stockProducto = stockProducto;
		this.fecha = fecha;
	}

	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	public String getTallaProducto() {
		return tallaProducto;
	}

	public void setTallaProducto(String tallaProducto) {
		this.tallaProducto = tallaProducto;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Condicion getCondicion() {
		return condicion;
	}

	public void setCondicion(Condicion condicion) {
		this.condicion = condicion;
	}

	public BigDecimal getPrecioProducto() {
		return precioProducto;
	}

	public void setPrecioProducto(BigDecimal precioProducto) {
		this.precioProducto = precioProducto;
	}

	public Integer getStockProducto() {
		return stockProducto;
	}

	public void setStockProducto(Integer stockProducto) {
		this.stockProducto = stockProducto;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
}