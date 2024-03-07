package com.comercio.sastreria.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comercio.sastreria.model.Superior;
import com.comercio.sastreria.repository.SuperiorRepository;

@Service
public class SuperiorServiceImp implements SuperiorService{

	@Autowired
	private SuperiorRepository superiorRepository;
	
	@Override
	public Superior registrar(Superior superior) {
		return superiorRepository.save(superior);
	}

	@Override
	public Optional<Superior> get(Integer id) {
		return superiorRepository.findById(id);
	}

	@Override
	public void update(Superior superior) {
		superiorRepository.save(superior);
	}

	@Override
	public void delete(Integer id) {
		superiorRepository.deleteById(id);
	}
}