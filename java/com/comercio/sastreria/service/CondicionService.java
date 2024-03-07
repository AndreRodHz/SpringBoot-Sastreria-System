package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.Condicion;

public interface CondicionService {
	
	public Optional<Condicion> get(String idCondicion);
	List<Condicion> getAllCategorias();
}