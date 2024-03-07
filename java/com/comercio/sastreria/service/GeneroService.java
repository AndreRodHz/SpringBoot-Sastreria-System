package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.Genero;

public interface GeneroService {
	
	public Optional<Genero> get(String idGenero);
	List<Genero> getAllCategorias();
}