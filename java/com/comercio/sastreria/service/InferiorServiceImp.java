package com.comercio.sastreria.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comercio.sastreria.model.Inferior;
import com.comercio.sastreria.repository.InferiorRepository;

@Service
public class InferiorServiceImp implements InferiorService{

	@Autowired
	private InferiorRepository inferiorRepository;
	
	@Override
	public Inferior registrar(Inferior inferior) {
		return inferiorRepository.save(inferior);
	}

	@Override
	public Optional<Inferior> get(Integer id) {
		return inferiorRepository.findById(id);
	}

	@Override
	public void update(Inferior inferior) {
		inferiorRepository.save(inferior);
	}

	@Override
	public void delete(Integer id) {
		inferiorRepository.deleteById(id);
	}
}