package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comercio.sastreria.model.Condicion;
import com.comercio.sastreria.repository.CondicionRepository;

@Service
public class CondicionServiceImp implements CondicionService{

	@Autowired
	private CondicionRepository condicionRepository; // Inyectando un OBJETO
	
	@Override
	public Optional<Condicion> get(String idCondicion) {
		return condicionRepository.findById(idCondicion);
	}

	@Override
	public List<Condicion> getAllCategorias() {
		return condicionRepository.findAll();
	}
}