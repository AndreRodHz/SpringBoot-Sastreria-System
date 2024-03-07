package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.Proveedor;

public interface ProveedorService {
	
	public Proveedor registrar(Proveedor proveedor);
	public Optional<Proveedor> get(String idProveedor);
	public void update(Proveedor proveedor);
	public void delete(String idProveedor);
	String obtenerNuevoIdProveedor(); //para poder obtener el ID a partir de un PROCEDIMIENTO ALMACENADO
	public List<Proveedor> findAll();
	public List<Proveedor> findByFechaDesc();
}