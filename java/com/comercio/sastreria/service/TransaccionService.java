package com.comercio.sastreria.service;

import java.util.Optional;
import com.comercio.sastreria.model.Transaccion;

public interface TransaccionService {
	
	public Transaccion registrar(Transaccion transaccion);
	public Optional<Transaccion> get(String idTransaccion);
	public void delete(String idTransaccion);
	String obtenerNuevoIdTransaccion(); //para poder obtener el ID a partir de un PROCEDIMIENTO ALMACENADO
}
