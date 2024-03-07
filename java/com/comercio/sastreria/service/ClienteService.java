package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.Cliente;

public interface ClienteService {
	
	public Cliente registrar(Cliente cliente);
	public Optional<Cliente> get(String idCliente);
	public void update(Cliente cliente);
	public void delete(String idCliente);
	String obtenerNuevoIdCliente(); //para poder obtener el ID a partir de un PROCEDIMIENTO ALMACENADO
	public List<Cliente> findAll();
	
	public Optional<Cliente> findByDocumento(String numeroDocCliente);
	public List<Cliente> findByFechaDesc();
}