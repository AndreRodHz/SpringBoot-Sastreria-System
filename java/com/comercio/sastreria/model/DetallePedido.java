package com.comercio.sastreria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_pedidos")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_pedido", nullable = false, referencedColumnName = "id_pedido")
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name = "id_prod_pedi", nullable = false, referencedColumnName = "id_prod_pedi")
	private ProductoPedido productoPedido;
	
	@Column(name ="descrip_d_pp")
	private String descripDetalleP;
	
	@Column(name = "precio_d_pp", nullable = false, precision = 18, scale = 2)
	private BigDecimal precioDetalleProductoPedido;
	
	@Column(name = "cantidad_d_pp", nullable = false)
	private Integer cantidadDetalleProductoPedido;
	
	public BigDecimal getImpDP() {
		BigDecimal cantidadBigDecimal = new BigDecimal(cantidadDetalleProductoPedido);
	    if (productoPedido != null) {
	        return precioDetalleProductoPedido.multiply(cantidadBigDecimal);
	    } else {
	        return BigDecimal.ZERO; // o manejo de error adecuado
	    }
	}

	// Constructores
	
	public DetallePedido() {
	}

	public DetallePedido(Long id, Pedido pedido, ProductoPedido productoPedido, String descripDetalleP,
			BigDecimal precioDetalleProductoPedido, Integer cantidadDetalleProductoPedido) {
		super();
		this.id = id;
		this.pedido = pedido;
		this.productoPedido = productoPedido;
		this.descripDetalleP = descripDetalleP;
		this.precioDetalleProductoPedido = precioDetalleProductoPedido;
		this.cantidadDetalleProductoPedido = cantidadDetalleProductoPedido;
	}

	// Getters y Setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public ProductoPedido getProductoPedido() {
		return productoPedido;
	}

	public void setProductoPedido(ProductoPedido productoPedido) {
		this.productoPedido = productoPedido;
	}

	public String getDescripDetalleP() {
		return descripDetalleP;
	}

	public void setDescripDetalleP(String descripDetalleP) {
		this.descripDetalleP = descripDetalleP;
	}

	public BigDecimal getPrecioDetalleProductoPedido() {
		return precioDetalleProductoPedido;
	}

	public void setPrecioDetalleProductoPedido(BigDecimal precioDetalleProductoPedido) {
		this.precioDetalleProductoPedido = precioDetalleProductoPedido;
	}

	public Integer getCantidadDetalleProductoPedido() {
		return cantidadDetalleProductoPedido;
	}

	public void setCantidadDetalleProductoPedido(Integer cantidadDetalleProductoPedido) {
		this.cantidadDetalleProductoPedido = cantidadDetalleProductoPedido;
	}
}