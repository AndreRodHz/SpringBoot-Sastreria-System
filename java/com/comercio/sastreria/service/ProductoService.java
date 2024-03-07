package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.Producto;

public interface ProductoService {
	
	public Producto registrar(Producto producto);
	public Optional<Producto> get(String idProducto);
	public void update(Producto producto);
	public void delete(String idProducto);
	String obtenerNuevoIdProducto(); //para poder obtener el ID a partir de un PROCEDIMIENTO ALMACENADO
	public List<Producto> findAll();
	public List<Producto> findByFechaDesc();
	
	public void actualizarStock(String idProducto, Integer nuevoStock);
}