package com.comercio.sastreria.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.DetalleVenta;
import com.comercio.sastreria.model.Transaccion;
import com.comercio.sastreria.model.Venta;

public interface VentaService {

	public Venta registrar(Venta venta);
	public DetalleVenta registrarDetalle(DetalleVenta detalleVenta);
	
	public Optional<Venta> get(String idVenta);
	public void update(Venta venta);
	public void delete(String idVenta);
	String obtenerNuevoIdVenta(); //para poder obtener el ID a partir de un PROCEDIMIENTO ALMACENADO
	
	public List<Venta> findAll();
	
	public BigDecimal calcularTotalVenta(List<DetalleVenta> carrito);

	public Transaccion obtenerTransaccionPorIdVenta(String idVenta);
	public List<DetalleVenta> obtenerDetallesPorIdVenta(String idVenta);
	
	public List<Venta> findByFecha();
	public List<Venta> findByWeek();
}
