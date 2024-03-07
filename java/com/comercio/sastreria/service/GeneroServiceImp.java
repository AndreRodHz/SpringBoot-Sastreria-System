package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comercio.sastreria.model.Genero;
import com.comercio.sastreria.repository.GeneroRepository;

@Service
public class GeneroServiceImp implements GeneroService{

	@Autowired
	private GeneroRepository generoRepository; // Inyectando un OBJETO

	@Override
	public Optional<Genero> get(String idGenero) {
		return generoRepository.findById(idGenero);
	}

	@Override
	public List<Genero> getAllCategorias() {
		return generoRepository.findAll();
	}
}