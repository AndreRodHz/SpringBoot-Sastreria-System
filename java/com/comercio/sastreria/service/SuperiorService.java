package com.comercio.sastreria.service;

import java.util.Optional;
import com.comercio.sastreria.model.Superior;

public interface SuperiorService {

	public Superior registrar(Superior superior);
	public Optional<Superior> get(Integer id);
	public void update(Superior superior);
	public void delete(Integer id);
}