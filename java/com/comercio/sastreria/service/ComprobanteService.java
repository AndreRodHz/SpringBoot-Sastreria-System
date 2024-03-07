package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.Comprobante;

public interface ComprobanteService {

	public Optional<Comprobante> get(String idComprobante);
	List<Comprobante> getAllCategorias();
}