package com.comercio.sastreria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comercio.sastreria.model.Comprobante;
import com.comercio.sastreria.repository.ComprobanteRepository;

@Service
public class ComprobanteServiceImp implements ComprobanteService{

	@Autowired
	ComprobanteRepository comprobanteRepository;
	
	@Override
	public Optional<Comprobante> get(String idComprobante) {
		return comprobanteRepository.findById(idComprobante);
	}

	@Override
	public List<Comprobante> getAllCategorias() {
		return comprobanteRepository.findAll();
	}
}