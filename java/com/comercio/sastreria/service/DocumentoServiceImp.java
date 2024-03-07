package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comercio.sastreria.model.Documento;
import com.comercio.sastreria.repository.DocumentoRepository;

@Service
public class DocumentoServiceImp implements DocumentoService{

	@Autowired
	private DocumentoRepository documentoRepository; // Inyectando un OBJETO

	@Override
	public Optional<Documento> get(String idDocumento) {
		return documentoRepository.findById(idDocumento);
	}

	@Override
	public List<Documento> getAllCategorias() {
		return documentoRepository.findAll();
	}
}