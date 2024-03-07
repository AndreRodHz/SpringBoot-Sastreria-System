package com.comercio.sastreria.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.Alquiler;
import com.comercio.sastreria.model.DetalleRecibo;
import com.comercio.sastreria.model.Pedido;
import com.comercio.sastreria.model.Recibo;
import com.comercio.sastreria.model.Transaccion;
import com.comercio.sastreria.model.Venta;

public interface ReciboService {

	public Recibo registrar(Recibo recibo);
	public DetalleRecibo registrarDetalle(DetalleRecibo detalleRecibo);
	
	public Optional<Recibo> get(String idRecibo);
	public void update(Recibo recibo);
	public void delete(String idRecibo);
	String obtenerNuevoIdRecibo();
	
	public List<Recibo> findAll();
	
	public BigDecimal calcularTotalRecibo(List<DetalleRecibo> carrito);
	public BigDecimal calcularIgv(BigDecimal calcularTotalRecibo);
	public BigDecimal calcularTotalFinal(BigDecimal calcularTotalRecibo, BigDecimal calcularIgv);
	
	public List<Venta> obtenerVentasSinRecibo();
	public List<Alquiler> obtenerAlquileresSinRecibo();
	public List<Pedido> obtenerPedidosSinRecibo();
	
	public List<DetalleRecibo> obtenerDetallesPorIdRecibo(String idRecibo);
	
	public List<Recibo> findByFecha();
	
	boolean existsByNumeroComprobante(String numeroComprobante);
	
	public List<Recibo> obtenerRecibosSemanaEmpleado(String idEmpleado);
	
	public String generarSerieComprobante(String inputIdCom);
	
	public Object obtenerDetalleEspecifico(Transaccion transaccion);
}