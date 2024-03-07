package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import com.comercio.sastreria.model.Documento;

public interface DocumentoService {
	
	public Optional<Documento> get(String idDocumento);
	List<Documento> getAllCategorias();
}