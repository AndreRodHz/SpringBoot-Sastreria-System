package com.comercio.sastreria.service;

import java.util.Optional;
import com.comercio.sastreria.model.Inferior;

public interface InferiorService {
	
	public Inferior registrar(Inferior inferior);
	public Optional<Inferior> get(Integer id);
	public void update(Inferior inferior);
	public void delete(Integer id);
}