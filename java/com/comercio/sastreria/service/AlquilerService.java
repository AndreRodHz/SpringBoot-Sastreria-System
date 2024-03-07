package com.comercio.sastreria.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.Alquiler;
import com.comercio.sastreria.model.DetalleAlquiler;
import com.comercio.sastreria.model.Transaccion;

public interface AlquilerService {

	public Alquiler registrar(Alquiler alquiler);
	public DetalleAlquiler registrarDetalle(DetalleAlquiler detalleAlquiler);
	
	public Optional<Alquiler> get(String idAlquiler);
	public void update(Alquiler alquiler);
	public void delete(String idAlquiler);
	String obtenerNuevoIdAlquiler(); //para poder obtener el ID a partir de un PROCEDIMIENTO ALMACENADO

	public List<Alquiler> findAll();

	public BigDecimal calcularTotalAlquiler(List<DetalleAlquiler> carrito);
	
	public Transaccion obtenerTransaccionPorIdAlquiler(String idAlquiler);
	public List<DetalleAlquiler> obtenerDetallesPorIdAlquiler(String idAlquiler);
	
	public List<Alquiler> findByFecha();
	
	public Alquiler reIngresoAlquiler(String idAlquiler);
	
	public List<Alquiler> findByWeek();
}