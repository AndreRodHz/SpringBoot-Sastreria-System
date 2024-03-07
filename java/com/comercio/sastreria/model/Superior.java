package com.comercio.sastreria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "superiores")
public class Superior {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	
	@OneToOne
	@JoinColumn(name = "id_pedido", nullable = false, unique = true, referencedColumnName = "id_pedido")
	private Pedido pedido;
	
	@Column(name = "cuello", precision = 18, scale = 2)
	private BigDecimal cuello;
	
	@Column(name = "longitud", precision = 18, scale = 2)
	private BigDecimal longitud;
	
	@Column(name = "hombros", precision = 18, scale = 2)
	private BigDecimal hombros;

	@Column(name = "sisa", precision = 18, scale = 2)
	private BigDecimal sisa;
	
	@Column(name = "biceps", precision = 18, scale = 2)
	private BigDecimal biceps;
	
	@Column(name = "pecho", precision = 18, scale = 2)
	private BigDecimal pecho;
	
	@Column(name = "brazos", precision = 18, scale = 2)
	private BigDecimal brazos;
	
	@Column(name = "largo_cha", precision = 18, scale = 2)
	private BigDecimal largoChaleco;
	
	@Column(name = "largo_abri", precision = 18, scale = 2)
	private BigDecimal largoAbrigo;

	// Constructores
	
	public Superior() {
	}

	public Superior(Integer id, Pedido pedido, BigDecimal cuello, BigDecimal longitud, BigDecimal hombros, BigDecimal sisa,
			BigDecimal biceps, BigDecimal pecho, BigDecimal brazos, BigDecimal largoChaleco, BigDecimal largoAbrigo) {
		super();
		this.id = id;
		this.pedido = pedido;
		this.cuello = cuello;
		this.longitud = longitud;
		this.hombros = hombros;
		this.sisa = sisa;
		this.biceps = biceps;
		this.pecho = pecho;
		this.brazos = brazos;
		this.largoChaleco = largoChaleco;
		this.largoAbrigo = largoAbrigo;
	}

	// Getters y Setters
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public BigDecimal getCuello() {
		return cuello;
	}

	public void setCuello(BigDecimal cuello) {
		this.cuello = cuello;
	}

	public BigDecimal getLongitud() {
		return longitud;
	}

	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}

	public BigDecimal getHombros() {
		return hombros;
	}

	public void setHombros(BigDecimal hombros) {
		this.hombros = hombros;
	}

	public BigDecimal getSisa() {
		return sisa;
	}

	public void setSisa(BigDecimal sisa) {
		this.sisa = sisa;
	}

	public BigDecimal getBiceps() {
		return biceps;
	}

	public void setBiceps(BigDecimal biceps) {
		this.biceps = biceps;
	}

	public BigDecimal getPecho() {
		return pecho;
	}

	public void setPecho(BigDecimal pecho) {
		this.pecho = pecho;
	}

	public BigDecimal getBrazos() {
		return brazos;
	}

	public void setBrazos(BigDecimal brazos) {
		this.brazos = brazos;
	}

	public BigDecimal getLargoChaleco() {
		return largoChaleco;
	}

	public void setLargoChaleco(BigDecimal largoChaleco) {
		this.largoChaleco = largoChaleco;
	}

	public BigDecimal getLargoAbrigo() {
		return largoAbrigo;
	}

	public void setLargoAbrigo(BigDecimal largoAbrigo) {
		this.largoAbrigo = largoAbrigo;
	}
}