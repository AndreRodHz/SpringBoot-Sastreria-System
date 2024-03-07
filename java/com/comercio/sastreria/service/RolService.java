package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.Rol;

public interface RolService {
	
	public Optional<Rol> get(String idRol);
	List<Rol> getAllCategorias();
}
