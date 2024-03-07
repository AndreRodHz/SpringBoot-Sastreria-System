package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comercio.sastreria.model.Rol;
import com.comercio.sastreria.repository.RolRepository;

@Service
public class RolServiceImp implements RolService{
	
	@Autowired
	private RolRepository rolRepository; // Inyectando un OBJETO

	@Override
	public Optional<Rol> get(String idRol) {
		return rolRepository.findById(idRol);
	}

	@Override
	public List<Rol> getAllCategorias() {
		return rolRepository.findAll();
	}
}
