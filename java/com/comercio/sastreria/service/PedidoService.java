package com.comercio.sastreria.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.DetallePedido;
import com.comercio.sastreria.model.Inferior;
import com.comercio.sastreria.model.Pedido;
import com.comercio.sastreria.model.Superior;
import com.comercio.sastreria.model.Transaccion;

public interface PedidoService {
	
	public Pedido registrar(Pedido pedido);
	public DetallePedido registrarDetalle(DetallePedido detallePedido);
	
	public Optional<Pedido> get(String idPedido);
	public void update(Pedido pedido);
	public void delete(String idPedido);
	String obtenerNuevoIdPedido(); //para poder obtener el ID a partir de un PROCEDIMIENTO ALMACENADO
	
	public List<Pedido> findAll();
	
	public BigDecimal calcularTotalPedido(List<DetallePedido> carrito);
	
	public Transaccion obtenerTransaccionPorIdPedido(String idPedido);
	public List<DetallePedido> obtenerDetallesPorIdPedido(String idPedido);
	
	public Superior obtenerMedidasSuperiores(String idPedido);
	public Inferior obtenerMedidasInferiores(String idPedido);
	
	public List<Pedido> findByFecha();
	
	public List<Pedido> findByWeek();
}